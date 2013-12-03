/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameLogic;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import SnakesAndLaddersApplet.SnakesAndLadders;

/**
 *
 * @author Divyata
 */

/** CSCI331 DD INTERFACE information of instances can 
 *  be accessed by my class using setter getter functions.
 *  constructor and setter handles setting of board number  
 *  such that the board number of an instance can not be 
 *  changed outside a range.
 */
public class Star {
    private int affectsSquare;
    private int xPos;
    private int yPos;
    private static Image starIMG = null;

    /**
     *
     * @param g
     * @param parent
     *
     * @throws NullPointerException
     */
    public void draw(Graphics g, SnakesAndLadders parent) throws NullPointerException {
        if (g == null || parent == null)
            throw new NullPointerException();
        g.drawImage(starIMG,xPos,yPos, parent);  
    }
    
    /**
     *
     * @return the square it should "reside" on
     */
    public int getBoardPosition(){
        return this.affectsSquare;
    }//CSCI331 INTERFACE getters an
    public void setBoardPosition(int position){
       
        if(position>=1 && position<=100)
            this.affectsSquare=position;
    }

    /* CSCI331 DD ENCAPSULATION
     * getters and setters variables is used by my class 
     * which allows certain variables to be set and can 
     * and can be used.  My class interfaces in the same 
     * way and also allows taht we can reuse or retrieve the data 
     * by calling get functions as well
     */

    Star(){
	this.affectsSquare = 1 + (int)(Math.random() * (99));
        this.calcDrawXY();
        if (starIMG == null){
            try {
              starIMG = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/imgs/star.png"));
            }
            catch (Exception e) {
              System.out.printf(e.toString() + "\n");
            }
        }
    }
    
    Star(int squareNum){
        if (squareNum >= 0 && squareNum <= 100){
          this.affectsSquare = squareNum;
        } else if (squareNum < 0) { 
          this.affectsSquare = 0;
        } else {
          this.affectsSquare = 100;
        }
        this.calcDrawXY();
        if (starIMG == null){
            try {
              starIMG = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/imgs/star.png"));
            }
            catch (Exception e) {
              System.out.printf(e.toString() + "\n");
            }
        }
        
    }
    
    private void calcDrawXY(){
        int square = this.affectsSquare;
        int x, y;
        int xOffset = 16;
        int yOffset = 5;
        if (square % 20 < 11 && square % 20 != 0){
            square--;
            //board number increment left to right eg 1 2 3 .. 10
            x = ((square % 10)*80);
        } else {
            //board number increment right to left 20 19 18 .. 11
            square--;
            x = ((9 - (square % 10)) * 80);
        }
        y = 730 - ((square/10)*80);
        this.xPos = (int)((x + xOffset)*0.75);
        this.yPos = (int)((y + yOffset)*0.75);
    } 
}

