/**
 * @author: Jingzhuo Hu
 * @author: Yuxin Meng
 */

package battleship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class OceanTest {

    static int NUM_BATTLESHIPS = 1;
    static int NUM_CRUISERS = 2;
    static int NUM_DESTROYERS = 3;
    static int NUM_SUBMARINES = 4;
    static int OCEAN_SIZE = 10;
    Ocean ocean;

    @BeforeEach
    void setUp() throws Exception {
        ocean = new Ocean();
    }

    @Test
    void testEmptyOcean() {

        //tests that all locations in the ocean are "empty"

        Ship[][] ships = ocean.getShipArray();

        for (int i = 0; i < ships.length; i++) {
            for (int j = 0; j < ships[i].length; j++) {
                Ship ship = ships[i][j];

                assertEquals("empty", ship.getShipType());
            }
        }

        assertEquals(0, ships[0][0].getBowRow());
        assertEquals(0, ships[0][0].getBowColumn());

        assertEquals(5, ships[5][5].getBowRow());
        assertEquals(5, ships[5][5].getBowColumn());

        assertEquals(9, ships[9][0].getBowRow());
        assertEquals(0, ships[9][0].getBowColumn());
    }

    @Test
    void testPlaceAllShipsRandomly() {

        //tests that the correct number of each ship type is placed in the ocean

        ocean.placeAllShipsRandomly();

        Ship[][] ships = ocean.getShipArray();
        ArrayList<Ship> shipsFound = new ArrayList<Ship>();

        int numBattlehips = 0;
        int numCruisers = 0;
        int numDestroyers = 0;
        int numSubmarines = 0;
        int numEmptySeas = 0;

        for (int i = 0; i < ships.length; i++) {
            for (int j = 0; j < ships[i].length; j++) {
                Ship ship = ships[i][j];
                if (!shipsFound.contains(ship)) {
                    shipsFound.add(ship);
                }
            }
        }

        for (Ship ship : shipsFound) {
            if ("battleship".equals(ship.getShipType())) {
                numBattlehips++;
            } else if ("cruiser".equals(ship.getShipType())) {
                numCruisers++;
            } else if ("destroyer".equals(ship.getShipType())) {
                numDestroyers++;
            } else if ("submarine".equals(ship.getShipType())) {
                numSubmarines++;
            } else if ("empty".equals(ship.getShipType())) {
                numEmptySeas++;
            }
        }

        assertEquals(NUM_BATTLESHIPS, numBattlehips);
        assertEquals(NUM_CRUISERS, numCruisers);
        assertEquals(NUM_DESTROYERS, numDestroyers);
        assertEquals(NUM_SUBMARINES, numSubmarines);

        //calculate total number of available spaces and occupied spaces
        int totalSpaces = OCEAN_SIZE * OCEAN_SIZE;
        int occupiedSpaces = (NUM_BATTLESHIPS * 4)
                + (NUM_CRUISERS * 3)
                + (NUM_DESTROYERS * 2)
                + (NUM_SUBMARINES * 1);

        //test number of empty seas, each with length of 1
        assertEquals(totalSpaces - occupiedSpaces, numEmptySeas);
    }

    @Test
    void testIsOccupied() {

        Destroyer destroyer = new Destroyer();
        int row = 1;
        int column = 5;
        boolean horizontal = false;
        destroyer.placeShipAt(row, column, horizontal, ocean);

        Ship submarine = new Submarine();
        row = 0;
        column = 0;
        horizontal = false;
        submarine.placeShipAt(row, column, horizontal, ocean);

        assertTrue(ocean.isOccupied(1, 5));

        //TODO
        //More tests
        // test Cruiser
        Cruiser cruiser = new Cruiser();
        row = 9;
        column = 9;
        horizontal = true;
        cruiser.placeShipAt(row, column, horizontal, ocean);
        // should have ship in (9,9) and (9,8)
        assertTrue(ocean.isOccupied(9, 9));
        assertTrue(ocean.isOccupied(9, 8));
        assertFalse(ocean.isOccupied(1, 8));

        // test Battleship
        Battleship battleship = new Battleship();
        row = 4;
        column = 4;
        horizontal = false;
        battleship.placeShipAt(row, column, horizontal, ocean);
        // should have ship in (4,4) and (3,4)
        assertTrue(ocean.isOccupied(4, 4));
        assertTrue(ocean.isOccupied(3, 4));
        assertFalse(ocean.isOccupied(2, 7));
    }

    @Test
    void testShootAt() {

        assertFalse(ocean.shootAt(0, 1));

        Destroyer destroyer = new Destroyer();
        int row = 1;
        int column = 5;
        boolean horizontal = false;
        destroyer.placeShipAt(row, column, horizontal, ocean);

        assertTrue(ocean.shootAt(1, 5));
        assertFalse(destroyer.isSunk());
        assertTrue(ocean.shootAt(0, 5));

        //TODO
        //More tests
        //test Cruiser
        Cruiser cruiser = new Cruiser();
        row = 6;
        column = 7;
        horizontal = true;
        cruiser.placeShipAt(row, column, horizontal, ocean);
        //hit the cruiser
        assertFalse(ocean.shootAt(4, 4));
        assertTrue(ocean.shootAt(6, 7));
        assertTrue(ocean.shootAt(6, 6));
        assertTrue(ocean.shootAt(6, 5));
        //after cruiser is sunk, shootAt should return false
        assertTrue(cruiser.isSunk());
        assertFalse(ocean.shootAt(0, 5));

        //test submarine
        Ship submarine = new Submarine();
        row = 0;
        column = 0;
        horizontal = false;
        submarine.placeShipAt(row, column, horizontal, ocean);
        //at (3,4), there is no submarine, so shoot and isSunk should be false
        assertFalse(ocean.shootAt(3, 4));
        assertFalse(submarine.isSunk());
        //shoot will be true at (0,0), but it will be false after the submarine sunk
        assertTrue(ocean.shootAt(0, 0));
        assertTrue(submarine.isSunk());
        assertFalse(ocean.shootAt(0, 0));

    }

    @Test
    void testGetShotsFired() {

        //should be all false - no ships added yet
        assertFalse(ocean.shootAt(0, 1));
        assertFalse(ocean.shootAt(1, 0));
        assertFalse(ocean.shootAt(3, 3));
        assertFalse(ocean.shootAt(9, 9));
        assertEquals(4, ocean.getShotsFired());

        Destroyer destroyer = new Destroyer();
        int row = 1;
        int column = 5;
        boolean horizontal = false;
        destroyer.placeShipAt(row, column, horizontal, ocean);

        Ship submarine = new Submarine();
        row = 0;
        column = 0;
        horizontal = false;
        submarine.placeShipAt(row, column, horizontal, ocean);

        assertTrue(ocean.shootAt(1, 5));
        assertFalse(destroyer.isSunk());
        assertTrue(ocean.shootAt(0, 5));
        assertTrue(destroyer.isSunk());
        assertEquals(6, ocean.getShotsFired());

        //TODO
        //More tests
        //test cruiser
        Cruiser cruiser = new Cruiser();
        row = 6;
        column = 7;
        horizontal = true;
        cruiser.placeShipAt(row, column, horizontal, ocean);
        //hit the cruiser
        assertFalse(ocean.shootAt(4, 4));
        assertTrue(ocean.shootAt(6, 7));
        assertTrue(ocean.shootAt(6, 6));
        assertTrue(ocean.shootAt(6, 5));
        assertEquals(10, ocean.getShotsFired());
        //after cruiser is sunk, shootAt should return false
        assertTrue(cruiser.isSunk());
        assertFalse(ocean.shootAt(0, 5));
        //the number should be added if the cruiser is sunk
        assertEquals(11, ocean.getShotsFired());

        //test battleship
        Battleship battleship = new Battleship();
        row = 4;
        column = 4;
        horizontal = false;
        battleship.placeShipAt(row, column, horizontal, ocean);
        // should have ship in (4,4) and (3,4)
        assertTrue(ocean.shootAt(4, 4));
        assertTrue(ocean.shootAt(3, 4));
        assertFalse(ocean.shootAt(1, 7));
        assertEquals(14, ocean.getShotsFired());
    }

    @Test
    void testGetHitCount() {

        Destroyer destroyer = new Destroyer();
        int row = 1;
        int column = 5;
        boolean horizontal = false;
        destroyer.placeShipAt(row, column, horizontal, ocean);

        assertTrue(ocean.shootAt(1, 5));
        assertFalse(destroyer.isSunk());
        assertEquals(1, ocean.getHitCount());

        //TODO
        //More tests
        //test cruiser
        Cruiser cruiser = new Cruiser();
        row = 6;
        column = 7;
        horizontal = true;
        cruiser.placeShipAt(row, column, horizontal, ocean);
        //hit the cruiser
        assertFalse(ocean.shootAt(4, 4));
        assertTrue(ocean.shootAt(6, 7));
        assertTrue(ocean.shootAt(6, 6));
        assertTrue(ocean.shootAt(6, 5));
        assertEquals(4, ocean.getHitCount());
        //after cruiser is sunk, shootAt should return false
        assertTrue(cruiser.isSunk());
        assertFalse(ocean.shootAt(9, 9));
        //but the getHitCount won't change
        assertEquals(4, ocean.getHitCount());

        //test battleship
        Battleship battleship = new Battleship();
        row = 4;
        column = 4;
        horizontal = false;
        battleship.placeShipAt(row, column, horizontal, ocean);
        // should have ship in (4,4) and (3,4)
        assertTrue(ocean.shootAt(4, 4));
        assertTrue(ocean.shootAt(3, 4));
        assertFalse(ocean.shootAt(1, 7));
        assertEquals(6, ocean.getHitCount());
    }

    @Test
    void testGetShipsSunk() {

        Destroyer destroyer = new Destroyer();
        int row = 1;
        int column = 5;
        boolean horizontal = false;
        destroyer.placeShipAt(row, column, horizontal, ocean);

        assertTrue(ocean.shootAt(1, 5));
        assertFalse(destroyer.isSunk());
        assertEquals(1, ocean.getHitCount());
        assertEquals(0, ocean.getShipsSunk());

        //TODO
        //More tests
        //test submarine
        Ship submarine = new Submarine();
        row = 0;
        column = 0;
        horizontal = false;
        submarine.placeShipAt(row, column, horizontal, ocean);
        //at (3,4), there is no submarine, so shoot and isSunk should be false, getShipsSunk is 0
        assertFalse(ocean.shootAt(3, 4));
        assertFalse(submarine.isSunk());
        assertEquals(0, ocean.getShipsSunk());
        //shoot will be true at (0,0), but it will be false after the submarine sunk, and getShipsSunk is 1
        assertTrue(ocean.shootAt(0, 0));
        assertTrue(submarine.isSunk());
        assertFalse(ocean.shootAt(0, 0));
        assertEquals(1, ocean.getShipsSunk());

        //test cruiser
        Cruiser cruiser = new Cruiser();
        row = 7;
        column = 7;
        horizontal = true;
        cruiser.placeShipAt(row, column, horizontal, ocean);
        //hit the cruiser
        assertFalse(ocean.shootAt(4, 4));
        assertTrue(ocean.shootAt(7, 7));
        assertTrue(ocean.shootAt(7, 6));
        assertTrue(ocean.shootAt(7, 5));
        //after cruiser is sunk, shootAt should return false, and getShipsSunk will add 1
        assertTrue(cruiser.isSunk());
        assertFalse(ocean.shootAt(7, 7));
        assertEquals(2, ocean.getShipsSunk());

    }

    @Test
    void testGetShipArray() {

        Ship[][] shipArray = ocean.getShipArray();
        assertEquals(OCEAN_SIZE, shipArray.length);
        assertEquals(OCEAN_SIZE, shipArray[0].length);

        assertEquals("empty", shipArray[0][0].getShipType());

        //TODO
        //More tests
        //add a submarine to see if it becomes not empty
        Ship submarine = new Submarine();
        int row = 0;
        int column = 0;
        boolean horizontal = false;
        submarine.placeShipAt(row, column, horizontal, ocean);
        //place at submarine(0,0) should be submarine, else should be empty
        assertEquals("submarine", shipArray[0][0].getShipType());
        assertEquals("empty", shipArray[1][1].getShipType());

        //add a cruiser to see if it becomes not empty
        Cruiser cruiser = new Cruiser();
        row = 7;
        column = 7;
        horizontal = true;
        cruiser.placeShipAt(row, column, horizontal, ocean);
        assertEquals("cruiser", shipArray[7][7].getShipType());
        assertEquals("cruiser", shipArray[7][6].getShipType());
        assertEquals("empty", shipArray[9][9].getShipType());

    }

}

