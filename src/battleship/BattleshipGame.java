/**
 * @author: Yuxin Meng
 * @author: Jingzhuo Hu
 */

package battleship;

import java.util.Scanner;

/**
 * The BattleshipGame class is the “main” class
 * It contains a main method, where program sets up the game; deals with inputs and outputs;
 * displays the results; and prints final scores.
 */
public class BattleshipGame {

    //maintain static variables available for every method in this class
    private static Scanner scan;
    private static Ocean ocean;

    /**
     * Set up the game; accept ”shots” from the user; display the results; and print final scores.
     * Complete all input/output including calling methods from other classes.
     *
     * @param args
     */
    public static void main(String[] args) {
        //initialization
        scan = new Scanner(System.in);
        ocean = new Ocean();
        //place ships of computer
        ocean.placeAllShipsRandomly();
        //print welcome messages
        printWelcome();
        //lunch game
        lunchGame();
        //print out final results
        printResults();
    }

    /**
     * Asks user to type in row and column locations to shoot at
     *
     * @return row and column locations as an int array
     */
    static int[] getUserInput() {
        //prompt message
        System.out.println("Enter row,column:");
        //store input row and column in an int array
        int[] rowAndCol = new int[2];
        //read user input
        String input = scan.nextLine();
        //split into 2 Strings, first indicating row, second indicating column
        String[] strs = input.split(",");
        //parse into ints
        for (int i = 0; i < 2; i++) {
            try {
                rowAndCol[i] = Integer.parseInt(strs[i].trim());    //trim spaces
            } catch (final NumberFormatException e) {
                //catch exception and print error message
                System.out.println("Invalid inputs!");
                //prompt again until get valid inputs
                return getUserInput();
            }
        }
        //check if row index within the proper range
        if (rowAndCol[0] < 0 || rowAndCol[0] > 9) {
            System.out.println("Row index out of bound!");
            //prompt again until get valid inputs
            return getUserInput();
        }
        //check if row index within the proper range
        if (rowAndCol[1] < 0 || rowAndCol[1] > 9) {
            System.out.println("Column index out of bound!");
            //prompt again until get valid inputs
            return getUserInput();
        }
        //return row and col in an array
        return rowAndCol;
    }

    /**
     * Lunches game until game over.
     * Displays interface, asks for and deals with inputs.
     */
    static void lunchGame() {
        while (!ocean.isGameOver()) {
            //print ocean
            ocean.print();
            //take in user inputs
            int[] rowAndCol = getUserInput();
            int row = rowAndCol[0];
            int col = rowAndCol[1];
            //record original sunk count
            int originalSunk = ocean.getShipsSunk();
            //shoot at the position user determined
            if (ocean.shootAt(row, col)) {
                //if shot succeeds, print success message
                System.out.println("Hit!");
                //if newly sunk a ship, print message
                if (ocean.getShipsSunk() > originalSunk) ocean.printSunkMessage();
            } else {
                //if shot fails, print failure message
                System.out.println("Miss!");
            }
        }
    }

    /**
     * Prints out final scores.
     */
    static void printResults() {
        System.out.println("Congratulations! You've sunk all the ships!");
        System.out.println("*****************");
        System.out.println("* Final Results *");
        System.out.println("*****************");
        System.out.println("Total shots: " + ocean.getShotsFired());
        System.out.println("Total hits:  " + ocean.getHitCount());
    }


    /**
     * Prints welcome message and instructions of the game.
     */
    static void printWelcome() {
        System.out.println("Welcome to Battleship!");
        System.out.println("You will play against the computer, who has hid 10 ships in the ocean:");
        System.out.println("\t1 Battleship\t\t(length 4)");
        System.out.println("\t2 Cruiser\t\t(length 3)");
        System.out.println("\t3 Destroyer\t\t(length 2)");
        System.out.println("\t4 Submarine\t\t(length 1)");
        System.out.println("Your goal is to sunk all of them. " +
                "Enter your target row,column locations to give a shot.");
        System.out.println("Let's begin!\n");
    }

}
