/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameLogic;

import java.awt.Graphics;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import SnakesAndLaddersApplet.SnakesAndLadders;

/**
 *
 * @author Dylan
 */

/* CSCI331 DS JUNIT
 * Here is a JUNIT test I wrote for the Star class
 */


public class StarTest {
    
    public StarTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of draw method, of class Star.
     * 
     * Test that a {@link NullPointerException} is thrown from
     * {@link Star#draw()}
     * 
     * @throws NullPointerException
     */
    @Test(expected = NullPointerException.class)
    public void testDraw() throws NullPointerException{
        System.out.println("Star.draw() Exception Test");
        Graphics g = null;
        SnakesAndLadders parent = null;
        Star instance = new Star();
          instance.draw(g, parent);
    }

    /**
     * Test of {@link Star#getBoardPosition()} method, of class Star.
     */
    @Test
    public void testGetBoardPosition() {
        System.out.println("Star.getBoardPosition() Test");
        Star instance = new Star(0);
        int expResult = 0;
        int result = instance.getBoardPosition();
        assertEquals("getBoardPosition failed!! expected 0!!",expResult, result);
        
        instance = new Star(5);
        expResult = 5;
        result = instance.getBoardPosition();
        assertEquals("getBoardPosition failed!! expected 5!!",expResult, result);
    }
    
    /**
     * Test of default constructor {@link Star#Star()}
     * 
     * All results should be within range of 1-100
     */
    @Test
    public void testDefaultConstructor() {
        System.out.println("Star.Star() Test");
        for (int i = 0; i < 10000; i++){
            Star instance = new Star();
            int result = instance.getBoardPosition();
            assertTrue("def. constructor fail!! expected 0 <= num <= 100", 
                    result >= 1 && result <= 100); 
        }
    }
    /**
     * Test of {@link Star#Star(int square)}
     * 
     * All results should be the passed parameter.
     */
    @Test
    public void testOneParameterConstructor() {
        System.out.println("Star.Star(int square) Test");
        Star instance;
        int result;
        for (int i = 0; i <= 100; i++){
            instance = new Star(i);
            result = instance.getBoardPosition();
            assertEquals("oneparam constructor fail!! expected " + i + "!!", 
                    result, i); 
        }
        instance = new Star(-1);
        result = instance.getBoardPosition();
        assertEquals("negative number constructor fail!! expected 0!!", 
                    result, 0);
        instance = new Star(105);
        result = instance.getBoardPosition();
        assertEquals(">100 number constructor fail!! expected 100!!", 
                    result, 100);

    }
    
    
}
