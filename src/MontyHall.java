import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class MontyHall {

    public static Set<String> runMontyHall(Integer choice1, boolean stay) {
        //use a set to represent each door
        Set<String> door1 = new HashSet<>();
        Set<String> door2 = new HashSet<>();
        Set<String> door3 = new HashSet<>();

        //no need to vary what is behind each door as choice will always be random
        door1.add("goat");
        door2.add("goat");
        door3.add("car");

        ArrayList<Set<String>> doors = new ArrayList<>();  //store doors for accessibility (see if-then below)
        doors.add(door1);
        doors.add(door2);
        doors.add(door3);

        if (stay) {
            return doors.get(choice1);  //if staying, just return the player's initial choice
        } else {
            if (choice1.equals(0)) {  //if initial choice is door 0 (goat), show them the goat behind door 1
                doors.remove(1);
            } else {
                doors.remove(0);  //otherwise, show them the goat behind door 0
            }
            if (choice1.equals(2)) {
                /*if initial choice was 2 and player not staying,
                 *then they already lost the car, so just return any door with a goat*/
                return doors.get(0);
            } else if (choice1.equals(0) || choice1.equals(1)) {
                /*if initial choice was door 0 or door 1, player initially chose the goat,
                 *so since they're changing their initial choice and the door with another goat behind it was revealed,
                 *just return the door with the car behind it (was at array index 2, now at index 1 after door removal)*/
                return doors.get(1);
            }
        }
        return null;  //this won't ever actually get executed
    }

    public static void main(String[] args) {
        //add some extra randomness
        Random randomSeed = new Random();
        boolean stay = true;
        Integer runsNum = new Integer(10000);  //set this to however many runs you want to perform
        if (args != null) {
            try {
                runsNum = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println(args[0] + ": invalid integer choice, using default 10,000");
            }
        }
        System.out.println(runsNum + " runs being performed: " );

        /* Simulate wins if initial choice of door DOESN'T change */
        int stayWins = 0;
        for (int i = 0; i < runsNum; i++) {
            Random randomChoice = new Random(randomSeed.nextInt());
            if (runMontyHall(randomChoice.nextInt(3), stay).contains("car")) {  //get random integer in [0,2]
                stayWins++;
            }
        }
        System.out.println("Stay: " + stayWins);  //number of wins when choosing to stay

        /* Simulate wins if initial choice of door DOES change */
        int switchWins = 0;
        for (int i = 0; i < runsNum; i++) {
            Random randomChoice = new Random(randomSeed.nextInt());
            if (runMontyHall(randomChoice.nextInt(3), !stay).contains("car")) {
                switchWins++;
            }
        }
        System.out.println("Switch: " + switchWins);  //number of wins when choosing to switch

        /* Notice how the number of wins when switching is approx. double the wins when staying,
         * and stayWins is approx. 1/3 of runsNum, while switchWins is approx. 2/3 of runsNum, as expected.*/
    }
}
