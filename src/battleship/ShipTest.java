/**
 * @author: Yuxin Meng
 * @author: Jingzhuo Hu
 */

package battleship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShipTest {

    Ocean ocean;
    Ship ship;

    @BeforeEach
    void setUp() throws Exception {
        ocean = new Ocean();
    }

    @Test
    void testGetLength() {
        ship = new Battleship();
        assertEquals(4, ship.getLength());

        //More tests
        //There are 3 for cruiser
        ship = new Cruiser();
        assertEquals(3, ship.getLength());
        
        //There are 2 for destroyer
        ship = new Destroyer();
        assertEquals(2, ship.getLength());
        
        //There is 1 for submarine
        ship = new Submarine();
        assertEquals(1, ship.getLength());
        
        //There is 1 for submarine
        ship = new EmptySea();
        assertEquals(1, ship.getLength());
    }

    @Test
    void testGetBowRow() {
        Ship battleship = new Battleship();
        int row = 0;
        int column = 4;
        boolean horizontal = true;
        battleship.placeShipAt(row, column, horizontal, ocean);
        assertEquals(row, battleship.getBowRow());

        //More tests
        battleship.setBowRow(2);
        assertEquals(2, battleship.getBowRow());

        //bowRow should be 0 before initialization
        Ship newship = new Submarine();
        assertEquals(0, newship.getBowRow());
        
        //bowRow should be 1 after place at(1,1)
        newship.placeShipAt(1, 1, false, ocean);
        assertEquals(1, newship.getBowRow());
    }

    @Test
    void testGetBowColumn() {
        Ship battleship = new Battleship();
        int row = 0;
        int column = 4;
        boolean horizontal = true;
        battleship.placeShipAt(row, column, horizontal, ocean);
        battleship.setBowColumn(column);
        assertEquals(column, battleship.getBowColumn());

        //More tests
        battleship.setBowColumn(2);
        assertEquals(2, battleship.getBowColumn());

        //bowRow should be 0 before initialization
        Ship newship = new Submarine();
        assertEquals(0, newship.getBowColumn());
        
        //bowRow should be 3 after place at(1,3)
        newship.placeShipAt(1, 3, false, ocean);
        assertEquals(3, newship.getBowColumn());
    }

    @Test
    void testGetHit() {
        ship = new Battleship();
        boolean[] hits = new boolean[4];
        assertArrayEquals(hits, ship.getHit());
        assertFalse(ship.getHit()[0]);
        assertFalse(ship.getHit()[1]);


        //More tests
        ship.placeShipAt(3, 0, false, ocean);
        //[3,0] is bow, ship face south
        assertFalse(ship.shootAt(1, 1));
        assertArrayEquals(hits, ship.getHit());

        assertTrue(ship.shootAt(1, 0));
        //[1,0] is the 2ed position of ship
        hits[2] = true;
        assertTrue(ship.getHit()[2]);
        assertArrayEquals(hits, ship.getHit());

        assertTrue(ship.shootAt(2, 0));
        //[2,0] is the 1st position of ship
        hits[1] = true;
        assertTrue(ship.getHit()[1]);
        assertArrayEquals(hits, ship.getHit());
    }

    @Test
    void testGetShipType() {
        ship = new Battleship();
        assertEquals("battleship", ship.getShipType());

        //More tests
        //get cruiser for cruiser
        ship = new Cruiser();
        assertEquals("cruiser", ship.getShipType());
        
        //get destroyer for destroyer
        ship = new Destroyer();
        assertEquals("destroyer", ship.getShipType());
        
        //get submarine for submarine
        ship = new Submarine();
        assertEquals("submarine", ship.getShipType());

        //get empty for empty
        ship = new EmptySea();
        assertEquals("empty", ship.getShipType());
    }

    @Test
    void testIsHorizontal() {
        Ship battleship = new Battleship();
        int row = 0;
        int column = 4;
        boolean horizontal = true;
        battleship.placeShipAt(row, column, horizontal, ocean);
        assertTrue(battleship.isHorizontal());


        //More tests
        //when set false, we get false for isHorizontal
        battleship.setHorizontal(false);
        assertFalse(battleship.isHorizontal());

        //when set false, we get false for isHorizontal
        Ship newship = new Cruiser();
        newship.placeShipAt(3, 3, false, ocean);
        assertFalse(newship.isHorizontal());
        
        //when set true, we get true for isHorizontal
        newship.setHorizontal(true);
        assertTrue(newship.isHorizontal());
    }

    @Test
    void testSetBowRow() {
        Ship battleship = new Battleship();
        int row = 0;
        int column = 4;
        boolean horizontal = true;
        battleship.setBowRow(row);
        assertEquals(row, battleship.getBowRow());


        //More tests
        //when set bowrow for 6, we get 6 for getbowrow
        battleship.setBowRow(6);
        assertEquals(6, battleship.getBowRow());
        
        //when start of a new ship, we get 0 for getborow
        Ship newship = new Cruiser();
        assertEquals(0, newship.getBowRow());
        
        //when set bowrow for 2, we get 2 for getbowrow
        newship.setBowRow(2);
        assertEquals(2, newship.getBowRow());
        
        //when set bowrow for 2, we get 2 for getbowrow
        newship.placeShipAt(3, 3, false, ocean);
        assertEquals(3, newship.getBowRow());
    }

    @Test
    void testSetBowColumn() {
        Ship battleship = new Battleship();
        int row = 0;
        int column = 4;
        boolean horizontal = true;
        battleship.setBowColumn(column);
        assertEquals(column, battleship.getBowColumn());


        //More tests
        //when set bowcolumn to 6, we get 6 for getbowcolumn
        battleship.setBowColumn(6);
        assertEquals(6, battleship.getBowColumn());
        
        //when start a new ship, we get 0 for getbowcolumn
        Ship newship = new Cruiser();
        assertEquals(0, newship.getBowColumn());
        //when set bowcolumn to 2, we get 2 for getbowcolumn
        newship.setBowColumn(2);
        assertEquals(2, newship.getBowColumn());
        
        //when set bowcolumn to 3, we get 3 for getbowcolumn
        newship.placeShipAt(3, 3, false, ocean);
        assertEquals(3, newship.getBowColumn());
    }

    @Test
    void testSetHorizontal() {
        Ship battleship = new Battleship();
        int row = 0;
        int column = 4;
        boolean horizontal = true;
        battleship.setHorizontal(horizontal);
        assertTrue(battleship.isHorizontal());


        //More tests
        //when sethorizontal is false, we get false for isbattleship
        battleship.setHorizontal(false);
        assertFalse(battleship.isHorizontal());
        
        //when start a new ship, we get false for isbattleship
        Ship newship = new Cruiser();
        assertFalse(newship.isHorizontal());
        
        //when sethorizontal is true, we get true for isbattleship
        newship.setHorizontal(true);
        assertTrue(newship.isHorizontal());
    }

    @Test
    void testOkToPlaceShipAt() {

        //test when other ships are not in the ocean
        Ship battleship = new Battleship();
        int row = 0;
        int column = 4;
        boolean horizontal = true;
        boolean ok = battleship.okToPlaceShipAt(row, column, horizontal, ocean);
        assertTrue(ok, "OK to place ship here.");

        //More tests
        //when at 4,1, it is a place ok to place battleship
        ok = battleship.okToPlaceShipAt(4, 1, false, ocean);
        assertTrue(ok, "OK to place ship here.");
        
        //when at 2,1, it is not a place ok to place battleship, out of bound
        boolean illegal = battleship.okToPlaceShipAt(2, 1, true, ocean);
        assertFalse(illegal, "No enough space to place ship here.");
        
        //when at 2,10, it is not a place ok to place battleship, 10 is out of bound
        illegal = battleship.okToPlaceShipAt(2, 10, true, ocean);
        assertFalse(illegal, "Index out of range.");
        
        //2,1 is a place ok to place destroyer
        Ship destroyer = new Destroyer();
        ok = destroyer.okToPlaceShipAt(2, 1, true, ocean);
        assertTrue(ok, "OK to place ship here.");   //destroyer is of length 2, there's enough space
    }

    @Test
    void testOkToPlaceShipAtAgainstOtherShipsOneBattleship() {

        //test when other ships are in the ocean

        //place first ship
        Battleship battleship1 = new Battleship();
        int row = 0;
        int column = 4;
        boolean horizontal = true;
        boolean ok1 = battleship1.okToPlaceShipAt(row, column, horizontal, ocean);
        assertTrue(ok1, "OK to place ship here.");
        battleship1.placeShipAt(row, column, horizontal, ocean);

        //test second ship
        Battleship battleship2 = new Battleship();
        row = 1;
        column = 4;
        horizontal = true;
        boolean ok2 = battleship2.okToPlaceShipAt(row, column, horizontal, ocean);
        assertFalse(ok2, "Not OK to place ship vertically adjacent below.");


        //More tests
        boolean ok3 = battleship2.okToPlaceShipAt(2, 2, horizontal, ocean);
        assertFalse(ok3, "No enough place on the left.");

        boolean ok4 = battleship2.okToPlaceShipAt(2, 3, horizontal, ocean);
        assertTrue(ok4, "OK to place ship here.");

        boolean ok5 = battleship2.okToPlaceShipAt(2, 9, horizontal, ocean);
        assertTrue(ok5, "OK to place ship here.");

        Destroyer destroyer = new Destroyer();

        boolean d_ok = destroyer.okToPlaceShipAt(4, 2, false, ocean);
        assertTrue(d_ok, "OK to place ship here.");
        destroyer.placeShipAt(4, 2, false, ocean);

        //test submarine
        Submarine submarine = new Submarine();
        boolean s;
        for (int i = 2; i <= 5; i++) {
            for (int j = 1; j <= 3; j++) {
                s = submarine.okToPlaceShipAt(i, j, true, ocean);
                assertFalse(s, "No OK to place here.");
            }
        }
        for (int i = 0; i <= 4; i++) {
            s = submarine.okToPlaceShipAt(6, i, true, ocean);
            assertTrue(s, "OK to place ship here.");
        }
        for (int i = 2; i <= 6; i++) {
            s = submarine.okToPlaceShipAt(i, 0, true, ocean);
            assertTrue(s, "OK to place ship here.");
        }
    }

    @Test
    void testPlaceShipAt() {

        Ship battleship = new Battleship();
        int row = 0;
        int column = 4;
        boolean horizontal = true;
        battleship.placeShipAt(row, column, horizontal, ocean);
        assertEquals(row, battleship.getBowRow());
        assertEquals(column, battleship.getBowColumn());
        assertTrue(battleship.isHorizontal());

        assertEquals("empty", ocean.getShipArray()[0][0].getShipType());
        assertEquals(battleship, ocean.getShipArray()[0][1]);


        //More tests
        assertEquals(battleship, ocean.getShipArray()[0][2]);
        assertEquals(battleship, ocean.getShipArray()[0][3]);
        assertEquals(battleship, ocean.getShipArray()[0][4]);
        
        //test to place a destroyer at 4,2
        Ship destroyer = new Destroyer();
        row = 4;
        column = 2;
        horizontal = false;
        destroyer.placeShipAt(row, column, horizontal, ocean);
        assertEquals(row, destroyer.getBowRow());
        assertEquals(column, destroyer.getBowColumn());
        assertFalse(destroyer.isHorizontal());

        assertEquals("empty", ocean.getShipArray()[2][4].getShipType());
        assertEquals(destroyer, ocean.getShipArray()[3][2]);
        assertEquals(destroyer, ocean.getShipArray()[4][2]);
        
        //test to place a cruiser at 2,6
        Ship cruiser = new Cruiser();
        row = 2;
        column = 6;
        horizontal = true;
        cruiser.placeShipAt(row, column, horizontal, ocean);
        assertEquals(row, cruiser.getBowRow());
        assertEquals(column, cruiser.getBowColumn());
        assertTrue(cruiser.isHorizontal());

        assertEquals("empty", ocean.getShipArray()[2][3].getShipType());
        assertEquals(cruiser, ocean.getShipArray()[2][4]);
        assertEquals(cruiser, ocean.getShipArray()[2][5]);
        assertEquals(cruiser, ocean.getShipArray()[2][6]);

    }

    @Test
    void testShootAt() {

        Ship battleship = new Battleship();
        int row = 0;
        int column = 9;
        boolean horizontal = true;
        battleship.placeShipAt(row, column, horizontal, ocean);

        assertFalse(battleship.shootAt(1, 9));
        boolean[] hitArray0 = {false, false, false, false};
        assertArrayEquals(hitArray0, battleship.getHit());


        //More tests
        assertTrue(battleship.shootAt(0, 8));
        boolean[] hitArray1 = {false, true, false, false};
        assertArrayEquals(hitArray1, battleship.getHit());
        
        // when place a cruiser at 2,6
        Ship cruiser = new Cruiser();
        row = 2;
        column = 6;
        horizontal = true;
        cruiser.placeShipAt(row, column, horizontal, ocean);

        assertFalse(cruiser.shootAt(2, 3));
        boolean[] hitArray2 = {false, false, false};
        assertArrayEquals(hitArray2, cruiser.getHit());

        assertTrue(cruiser.shootAt(2, 6));
        boolean[] hitArray3 = {true, false, false};
        assertArrayEquals(hitArray3, cruiser.getHit());
        
        // when place a submarine at 4,4
        Ship submarine = new Submarine();
        row = 2;
        column = 6;
        horizontal = true;
        submarine.placeShipAt(row, column, horizontal, ocean);

        assertFalse(submarine.shootAt(2, 3));
    }

    @Test
    void testIsSunk() {

        Ship submarine = new Submarine();
        int row = 3;
        int column = 3;
        boolean horizontal = true;
        submarine.placeShipAt(row, column, horizontal, ocean);

        assertFalse(submarine.isSunk());
        assertFalse(submarine.shootAt(5, 2));
        assertFalse(submarine.isSunk());


        //More tests
        //when place cruiser at 2,7
        Ship cruiser = new Cruiser();
        row = 2;
        column = 7;
        horizontal = true;
        cruiser.placeShipAt(row, column, horizontal, ocean);

        assertFalse(cruiser.isSunk());

        assertFalse(cruiser.shootAt(2, 4));
        assertFalse(cruiser.isSunk());

        assertTrue(cruiser.shootAt(2, 7));
        assertFalse(cruiser.isSunk());

        assertTrue(cruiser.shootAt(2, 5));
        assertFalse(cruiser.isSunk());

        assertTrue(cruiser.shootAt(2, 6));
        assertTrue(cruiser.isSunk());

        //when place a submarine at 0,0
        submarine.placeShipAt(0, 0, false, ocean);

        assertFalse(submarine.shootAt(3, 3));
        assertFalse(submarine.isSunk());

        assertTrue(submarine.shootAt(0, 0));
        assertTrue(submarine.isSunk());

    }

    @Test
    void testToString() {

        Ship battleship = new Battleship();
        assertEquals("x", battleship.toString());

        int row = 9;
        int column = 1;
        boolean horizontal = false;
        battleship.placeShipAt(row, column, horizontal, ocean);
        battleship.shootAt(9, 1);
        assertEquals("x", battleship.toString());


        //More tests

        battleship.shootAt(8, 1);
        assertEquals("x", battleship.toString());
        battleship.shootAt(7, 1);
        assertEquals("x", battleship.toString());
        battleship.shootAt(6, 1);
        assertEquals("s", battleship.toString());
        
        //when place cruiser at 2,7
        Ship cruiser = new Cruiser();
        row = 2;
        column = 7;
        horizontal = true;
        cruiser.placeShipAt(row, column, horizontal, ocean);
        assertFalse(cruiser.isSunk());
        assertEquals("x", cruiser.toString());
        
        assertTrue(cruiser.shootAt(2, 7));
        assertTrue(cruiser.shootAt(2, 5));
        assertTrue(cruiser.shootAt(2, 6));
        assertTrue(cruiser.isSunk());
        assertEquals("s", cruiser.toString());
        
        //when place submarine at 4,4
        Ship submarine = new Submarine();
        row = 4;
        column = 4;
        horizontal = true;
        submarine.placeShipAt(row, column, horizontal, ocean);
        assertFalse(submarine.isSunk());
        assertEquals("x", submarine.toString());
        
        assertTrue(submarine.shootAt(4,4));
        assertTrue(submarine.isSunk());
        assertEquals("s", submarine.toString());

    }

}

