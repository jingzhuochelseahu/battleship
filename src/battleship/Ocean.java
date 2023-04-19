/**
 * @author: Jingzhuo Hu
 * @author: Yuxin Meng
 */

package battleship;

import java.util.Random;

/**
 * This contains a 10x10 array of Ships, representing an “ocean”, and some methods to manipulate it
 */
public class Ocean {

    //Array used to quickly determine which ship is in any given location
    private Ship[][] ships = new Ship[10][10];
    //The total number of shots fired by the user
    private int shotsFired;
    //The number of times a shot hit a ship.
    private int hitCount;
    //The number of ships sunk (10 ships in all)
    private int shipsSunk;
    //A helper variable to store sunk message
    private String sunkMessage;

    /**
     * Constructor creates an ”empty” ocean (and fills the ships array with EmptySea objects)
     * Also initializes any game variables, such as how many shots have been fired.
     */
    public Ocean() {
        // call create sea
        createEmptySea();

        // initializes any game variables
        this.shotsFired = 0;
        this.hitCount = 0;
        this.shipsSunk = 0;
    }

    /**
     * A private helper method to fill this ocean with EmptySea Objects for initialization.
     */
    private void createEmptySea() {
        //Ship[][] emptySea = new Ship[10][10];

        //iterate over the rows
        for (int i = 0; i < 10; i++) {
            //iterate over the columns in each row
            for (int j = 0; j < 10; j++) {
                //fills the ships array with EmptySea objects
                Ship ship = new EmptySea();
                ship.placeShipAt(i, j, true, this); // this means create a new class ocean
            }
        }
    }

    /**
     * Place all ten ships randomly on the (initially empty) ocean. Place larger ships before
     * smaller ones, or you may end up with no legal place to put a large ship.
     */
    void placeAllShipsRandomly() {
        Random rd = new Random();

        // row position and column position
        int rowShip;
        int columnShip;

        // boolean for whether horizontal
        boolean horizontal;

        // create ships
        Ship[] computerShip = new Ship[10];
        computerShip[0] = new Battleship();
        computerShip[1] = new Cruiser();
        computerShip[2] = new Cruiser();
        computerShip[3] = new Destroyer();
        computerShip[4] = new Destroyer();
        computerShip[5] = new Destroyer();
        computerShip[6] = new Submarine();
        computerShip[7] = new Submarine();
        computerShip[8] = new Submarine();
        computerShip[9] = new Submarine();

        // for every ship of computerShip
        for (Ship s : computerShip) {
            do {
                //generate random number from 0 to 9
                rowShip = rd.nextInt(10);
                //generate random number from 0 to 9
                columnShip = rd.nextInt(10);
                //generate random number from 0 to 1
                horizontal = rd.nextBoolean();
            } while (!s.okToPlaceShipAt(rowShip, columnShip, horizontal, this));
            // call placeShipAt to place ship
            s.placeShipAt(rowShip, columnShip, horizontal, this);
        }
    }

    /**
     * Returns true if the given location contains a ship, false if it does not
     *
     * @param row
     * @param column
     * @return whether given location is occupied
     */
    boolean isOccupied(int row, int column) {
        return (this.ships[row][column].getShipType() != "empty");
    }

    /**
     * Returns true if the given location contains a ”real” ship, still afloat, (not an EmptySea),
     * false if it does not. In addition, this method updates the number of shots that have been
     * fired, and the number of hits.
     *
     * @param row
     * @param column
     * @return if this shot succeeds
     */
    boolean shootAt(int row, int column) {
        // if inputs are invalid, return false, and do not count in shotsFired
        if (row < 0 || column < 0 || row > 9 || column > 9) {
            return false;
        }

        // shotsFired increments
        this.shotsFired++;

        Ship ship = this.ships[row][column];
        //if shot succeeds, meaning it's not empty and the ship was not sunk before this shot
        if (ship.shootAt(row, column)) {
            //hitCount increments (hit count should not increase for hitting an already sunk ship)
            this.hitCount++;
            //if ship sinks after this shot, shipsSunk increments
            if (ship.isSunk()) {
                this.shipsSunk++;
                //update sunk message
                sunkMessage = "You just sank a ship - " + ship.getShipType() + ".";
            }
            return true;
        }
        // if no ship at given location, or ship at this position is already sunk
        return false;
    }

    /**
     * A helper method to print message when sinking a ship.
     */
    void printSunkMessage() {
        System.out.println(this.sunkMessage);
    }

    /**
     * Returns the number of shots fired (in the game)
     *
     * @return number of shots fired
     */
    int getShotsFired() {
        return this.shotsFired;
    }

    /**
     * Returns the number of hits recorded (in the game). All hits are counted, not just
     * the first time a given square is hit.
     *
     * @return the number of hits recorded
     */
    int getHitCount() {
        return this.hitCount;
    }

    /**
     * Returns the number of ships sunk (in the game)
     *
     * @return the number of ships sunk
     */
    int getShipsSunk() {
        return this.shipsSunk;
    }

    /**
     * Returns true if all ships have been sunk, otherwise false
     *
     * @return if all ships have been sunk
     */
    boolean isGameOver() {
        return this.shipsSunk == 10;
    }

    /**
     * Returns the 10x10 array of Ships. The methods in the Ship class that take an
     * Ocean parameter need to be able to look at the contents of this array;
     * the placeShipAt() method even needs to modify it. While it is undesirable to
     * allow methods in one class to directly access instance variables in another class,
     * sometimes there is just no good alternative.
     *
     * @return Ship array
     */
    Ship[][] getShipArray() {
        return this.ships;
    }

    /**
     * Prints the Ocean. To aid the user, row numbers should be displayed along the left edge
     * of the array, and column numbers should be displayed along the top.
     * Numbers' range: 0 to 9.
     * Use ‘x’ to indicate a location that you have fired upon and hit a (real) ship.
     * Use ‘-’ to indicate a location that you have fired upon and found nothing there.
     * Use ‘s’ to indicate a location containing a sunken ship.
     * Use ‘.’ (a period) to indicate a location that you have never fired upon.
     */
    void print() {
        //TODO
        //print column numbers on the top
        for (int i = 0; i < 10; i++) {
            System.out.print("\t" + i);
        }
        System.out.println();
        //print each line
        for (int r = 0; r < 10; r++) {
            //print row column on the right
            System.out.print(r);
            //print element on each column
            for (int c = 0; c < 10; c++) {
                //offset
                System.out.print("\t");
                //get the ship at the current location
                Ship cur = this.ships[r][c];
                //get the relative position of this part within this ship
                int pos = cur.relativePos(r, c);
                //if this location has been hit, print it;
                // otherwise print "." to represent an unknown field
                if (cur.getHit()[pos]) System.out.print(cur);
                else System.out.print(".");
            }
            System.out.println();
        }
    }

    /**
     * For debugging only.
     * Prints the Ocean with row numbers displayed along the left edge of the array,
     * and column numbers displayed along the top.
     * Use ‘b’ to indicate a Battleship.
     * Use ‘c’ to indicate a Cruiser.
     * Use ‘d’ to indicate a Destroyer.
     * Use ‘s’ to indicate a Submarine.
     * Use ‘ ’ (single space) to indicate an EmptySea.
     */
    void printWithShips() {
        //TODO
        //print column numbers on the top
        System.out.print("  ");
        for (int i = 0; i < ships[0].length; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        //print each line
        for (int r = 0; r < ships.length; r++) {
            System.out.print(r + " ");
            for (int c = 0; c < ships[0].length; c++) {
                //update the ships
                Ship[][] shipType = this.getShipArray();
                if (shipType[r][c] instanceof Battleship) {
                    // if it is a battleship, return b
                    System.out.print("b ");
                } else if (shipType[r][c] instanceof Cruiser) {
                    // if it is a cruiser, return c
                    System.out.print("c ");
                } else if (shipType[r][c] instanceof Destroyer) {
                    // if it is a destroyer, return d
                    System.out.print("d ");
                } else if (shipType[r][c] instanceof Submarine) {
                    // if it is a submarine, return b
                    System.out.print("s ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
    }
}

