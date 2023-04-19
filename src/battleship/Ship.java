/**
 * @author: Yuxin Meng
 * @author: Jingzhuo Hu
 */

package battleship;

/**
 * This abstract class describes the characteristics common to all ships.
 */
public abstract class Ship {

    private int bowRow;         //The row that contains the bow (front part of the ship)
    private int bowColumn;      //The column that contains the bow (front part of the ship)
    private int length;         //The length of the ship
    private boolean horizontal; //whether the ship is going to be placed horizontally or vertically
    private boolean[] hit;      //whether that part of the ship has been hit or not

    /**
     * This constructor sets the length property of the particular ship and initializes
     * the hit array based on that length
     *
     * @param length
     */
    public Ship(int length) {
        //sets the length property
        this.length = length;
        //initialize boolean array hit
        this.hit = new boolean[length];
        //initially all parts of a ship have not been not hit
        for (int i = 0; i < length; i++) {
            hit[i] = false;
        }
    }

    /**
     * Returns the bow column location
     *
     * @return bow column location as int
     */
    public int getBowColumn() {
        return this.bowColumn;
    }

    /**
     * Sets the value of bowColumn
     *
     * @param column
     */
    public void setBowColumn(int column) {
        this.bowColumn = column;
    }

    /**
     * Returns the ship length
     *
     * @return length as int
     */
    public int getLength() {
        return this.length;
    }

    /**
     * Returns the row corresponding to the position of the bow
     *
     * @return bow row location as int
     */
    public int getBowRow() {
        return this.bowRow;
    }

    /**
     * Sets the value of bowRow
     *
     * @param row
     */
    public void setBowRow(int row) {
        this.bowRow = row;
    }

    /**
     * Returns the hit array
     *
     * @return hit array as boolean[]
     */
    public boolean[] getHit() {
        return this.hit;
    }

    /**
     * Returns whether the ship is horizontal or not
     *
     * @return is horizontal or not as boolean
     */
    public boolean isHorizontal() {
        return this.horizontal;
    }

    /**
     * Sets the value of the instance variable horizontal
     *
     * @param horizontal
     */
    public void setHorizontal(boolean horizontal) {
        this.horizontal = horizontal;
    }

    /**
     * Abstract method, which will be override in subclasses.
     * Returns the type of ship as a String. Every specific type of Ship (e.g. BattleShip, Cruiser, etc.)
     * has to override and implement this method and return the corresponding ship type.
     *
     * @return
     */
    public abstract String getShipType();

    /**
     * Based on the given row, column, and orientation, returns true if it is okay to put a ship of
     * this length with its bow in this location; false otherwise.
     *
     * @param row        target row location (expected left top of ship)
     * @param column     target column location (expected left top of ship)
     * @param horizontal
     * @param ocean
     * @return if it is legal to place ship here, as boolean
     */
    boolean okToPlaceShipAt(int row, int column, boolean horizontal, Ocean ocean) {
        //deal with illegal inputs
        if (row < 0 || column < 0 || row > 9 || column > 9) return false;
        //check availability for each part & offset
        if (horizontal) {   //put the ship horizontally, parts on the same row, column range = ship length
            //check for body parts
            for (int i = 0; i < this.length; i++) {
                //As required: horizontal ships face East
                //  ex. given ship of length 3, it occupies col, col-1, and col-2 three columns
                //if there is no enough room for this ship
                if (column - i < 0) return false;
                //if any of these locations is occupied, we cannot put the ship here, return false
                if (ocean.isOccupied(row, column - i)) {
                    return false;
                }
            }
            //check for offsets - no adjacency
            for (int i = -1; i < 2; i++) {
                int r = row - i;    //offset above and below
                if (r < 0 || r > 9) continue;   //if ship sit aside boundary, no need to check offset
                for (int j = -1; j <= this.length; j++) {
                    if (column - j < 0 || column - j > 9)
                        continue;   //if ship sit aside boundary, no need to check offset
                    //if any of these locations is occupied, we cannot put the ship here, return false
                    if (ocean.isOccupied(r, column - j)) {
                        return false;
                    }
                }
            }
        } else {    //put the ship vertically, parts on the same column, row range = ship length
            //check for body parts
            for (int i = 0; i < this.length; i++) {
                //As required: horizontal ships face East
                //  ex. given ship of length 3, it occupies col, col-1, and col-2 three columns
                //if there is no enough room for this ship
                if (row - i < 0) return false;
                //if any of these locations is occupied, we cannot put the ship here, return false
                if (ocean.isOccupied(row - i, column)) {
                    return false;
                }
            }
            //check for offsets - no adjacency
            for (int i = -1; i < 2; i++) {
                int c = column - i;    //offset above and below
                if (c < 0 || c > 9) continue;   //if ship sit aside boundary, no need to check offset
                for (int j = -1; j <= this.length; j++) {
                    if (row - j < 0 || row - j > 9) continue;   //if ship sit aside boundary, no need to check offset
                    //if any of these locations is occupied, we cannot put the ship here, return false
                    if (ocean.isOccupied(row - j, c)) {
                        return false;
                    }
                }
            }
        }
        //if all parts' locations are available (not occupied), return true
        return true;
    }

    /**
     * Place the ship in the ocean. This involves giving values to the bowRow, bowColumn,
     * and horizontal instance variables in the ship, and it also involves putting a reference
     * to the ship in each of 1 or more locations (up to 4) in the ships array in the Ocean object.
     *
     * @param row        row location of the head of ship
     * @param column     column location of the head of ship
     * @param horizontal whether the ship is put horizontally
     * @param ocean      current ocean
     */
    void placeShipAt(int row, int column, boolean horizontal, Ocean ocean) {
        //if the ship cannot be placed at target location, do nothing and return
        ////    if (!this.okToPlaceShipAt(row, column, horizontal, ocean)) return;
        //if it is ok to place ship at target location, continue:
        //set values of instance variables of ship
        this.bowRow = row;
        this.bowColumn = column;
        this.horizontal = horizontal;
        //putting a reference to the ship in each location in the ships array in the Ocean object
        Ship[][] ships = ocean.getShipArray();
        //for each part of the ship, set ship[row][col] to
        if (horizontal) {
            //if ship is put horizontally
            for (int i = 0; i < this.length; i++) {
                ships[row][column - i] = this;
            }
        } else {
            //if ship is put vertically
            for (int i = 0; i < this.length; i++) {
                ships[row - i][column] = this;
            }
        }
    }

    /**
     * If a part of the ship occupies the given row and column, and the ship hasn’t been sunk,
     * mark that part of the ship as “hit” (in the hit array, index 0 indicates the bow) and return true;
     * otherwise return false.
     *
     * @param row
     * @param column
     * @return
     */
    boolean shootAt(int row, int column) {
        //check if the ship is already sunk
        if (this.isSunk()) return false;
        //check if target is part of ship and mark as hit if it is
        int r = this.bowRow;
        int c = this.bowColumn;
        //if target = bow
        if (r == row && c == column) {
            hit[0] = true;
            return true;
        }
        //check if target is body of ship
        if (this.horizontal) {  //ship is horizontal
            //if target is not in the same row with bow, it's not part of ship
            if (r != row) return false;
            for (int i = 1; i < this.length; i++) {
                if (c - i == column) {
                    hit[i] = true;
                    return true;
                }
            }
            //if target is not part of ship, return false
            return false;
        } else {    //ship is vertical
            //if target is not in the same column with bow, it's not part of ship
            if (c != column) return false;
            for (int i = 1; i < this.length; i++) {
                if (r - i == row) {
                    hit[i] = true;
                    return true;
                }
            }
            //if target is not part of ship, return false
            return false;
        }
    }

    /**
     * Return true if every part of the ship has been hit, false otherwise
     *
     * @return if the ship is sunk, as boolean
     */
    boolean isSunk() {
        boolean re = true;
        //check all parts, if any part has not been hit, the ship is not sunk
        for (boolean partHit : hit) {
            //if any part has not been hit
            if (!partHit) re = false;
        }
        return re;
    }

    /**
     * Returns a single-character String to use in the Ocean’s print method.
     * Returns ”s” if the ship has been sunk and ”x” if it has not been sunk.
     *
     * @return
     */
    @Override
    public String toString() {
        if (this.isSunk()) {
            return "s";
        } else {
            return "x";
        }
    }

    /**
     * Returns the relative position of the given location in this ship.
     * For print() method in Ocean class. Valid arguments(location) is guaranteed in print() method.
     *
     * @return relative position in ship
     */
    public int relativePos(int row, int column) {
        if (this.isHorizontal()) {   //if horizontal
            //ex. for a ship of len 3, bow at (4,4), bow is the 0th, (4,2) is the 2nd
            return (this.getBowColumn() - column);
        } else {    //if vertical
            //ex. for a ship of len 3, bow at (4,4), bow is the 0th, (2,4) is the 2nd
            return (this.getBowRow() - row);
        }
    }

}

/**
 * This class describes a ship of length 4
 */
class Battleship extends Ship {
    /**
     * This constructor creates a ship of length 4, sets length and initialize hit array.
     */
    public Battleship() {
        super(4);
    }

    /**
     * Returns the type of ship as a String.
     *
     * @return
     */
    @Override
    public String getShipType() {
        return "battleship";
    }
}

/**
 * This class describes a ship of length 3
 */
class Cruiser extends Ship {
    /**
     * This constructor creates a ship of length 3, sets length and initialize hit array.
     */
    public Cruiser() {
        super(3);
    }

    /**
     * Returns the type of ship as a String.
     *
     * @return
     */
    @Override
    public String getShipType() {
        return "cruiser";
    }
}

/**
 * This class describes a ship of length 2
 */
class Destroyer extends Ship {
    /**
     * This constructor creates a ship of length 2, sets length and initialize hit array.
     */
    public Destroyer() {
        super(2);
    }  //ship of length 2

    /**
     * Returns the type of ship as a String.
     *
     * @return
     */
    @Override
    public String getShipType() {
        return "destroyer";
    }
}

/**
 * This class describes a ship of length 1
 */
class Submarine extends Ship {
    /**
     * This constructor creates a ship of length 1, sets length and initialize hit array.
     */
    public Submarine() {
        super(1);
    }

    /**
     * Returns the type of ship as a String.
     *
     * @return
     */
    @Override
    public String getShipType() {
        return "submarine";
    }
}

/**
 * This class describes a part of the ocean that doesn’t have a ship in it.
 */
class EmptySea extends Ship {

    /**
     * Constructor sets the length variable to 1 by calling the constructor in the super class
     */
    public EmptySea() {
        super(1);
    }

    /**
     * This method overrides shootAt(int row, int column) that is inherited
     * from Ship, and always returns false to indicate that nothing was hit.
     *
     * @param row
     * @param column
     * @return false
     */
    @Override
    boolean shootAt(int row, int column) {
        this.getHit()[0] = true;
        return false;
    }

    /**
     * This method overrides isSunk() that is inherited from Ship, and always
     * returns false to indicate that you didn’t sink anything.
     *
     * @return false
     */
    @Override
    boolean isSunk() {
        return false;
    }

    /**
     * Returns the single-character “-“ String to use in the Ocean’s print method.
     *
     * @return "-"
     */
    @Override
    public String toString() {
        return "-";
    }

    /**
     * Returns the type of 'ship' as a String: "empty"
     *
     * @return "empty"
     */
    @Override
    public String getShipType() {
        return "empty";
    }
}
