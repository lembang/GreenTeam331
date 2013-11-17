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
 * edited by Dylan
 */

public class Ladder {

    public enum Dir{
        LEFT, RIGHT
    }
    
    private int affectsSquare;
    private int movesToSquare;
    private Dir orientation;
    private int xPos;
    private int yPos;
    private Image ladderIMG;
    
    public int getBottomPos(){
        return this.affectsSquare;
    }
    public int getTopPos(){
        return this.movesToSquare;
    }

    public void draw(Graphics g, SnakesAndLadders parent) {
        g.drawImage(this.ladderIMG,this.xPos,this.yPos, parent);   
    }
    
    public int getAffectsSquare(){
        return this.affectsSquare;
    }
    
    Ladder(){
        //set orientation randomly
        int rand = 1 + (int)(Math.random()*2);
        if (rand == 1){
            this.orientation = Dir.LEFT;
        } else {
            this.orientation = Dir.RIGHT;
        }
        
        //get random position for bottom calculated in terms of board positions
        this.affectsSquare = getRandomBottomPosition();
        this.movesToSquare = calculateTopPosition();
        calcDrawXY();
        try {
          if (this.orientation == Dir.RIGHT)
            this.ladderIMG = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/imgs/ladders/ladder1.png"));
          else 
            this.ladderIMG = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/imgs/ladders/ladder1Flipped.png"));  
        }
        catch (Exception e) {
     	  System.out.printf(e.toString() + "\n");
        }
    }
    
    public String getDir(){
        if (this.orientation == Dir.LEFT)
                return "LEFT";
        return "RIGHT";
    }
    
    private int getRandomBottomPosition(){
        //an exceptionally recursive function
        try {
        	//get number between 1 and 80
            int boardPosition = 1 + (int)(Math.random() * 80);
            if (this.orientation == Dir.LEFT){
                if (boardPosition % 20 < 3 || boardPosition % 20 == 19)
                   throw new Exception();
            } else { //orientation == Dir.RIGHT
                if (boardPosition % 20 <= 12 && boardPosition % 20 >= 9)
                	throw new Exception(); 
            }
            return boardPosition;
        } catch(Exception e) {
            return this.getRandomBottomPosition();
        }
    }
    private final  int calculateTopPosition(){
        //Assumes correctness of this.affectsSquare
        //Assumes ladder rise of 3
        //Assumes ladder run of 3
        int boardPosition = this.affectsSquare;
        if (this.orientation == Dir.LEFT){
                if (boardPosition % 20 < 11 && boardPosition % 20 != 0){
                   //board number increment left to right eg 1 2 3 .. 10
                   return boardPosition + 18;
                } else {
                   //board number increment right to left 20 19 18 .. 11
                   return boardPosition + 22;
                }
        } else { //orientation == Dir.RIGHT
                if (boardPosition % 20 < 11 && boardPosition % 20 != 0){
                   //board number increment left to right eg 1 2 3 .. 10
                   return boardPosition + 22;
                } else {
                   //board number increment right to left 20 19 18 .. 11
                   return boardPosition + 18;
                }
        }
    }
     private void calcDrawXY(){
        int square = this.affectsSquare;
        int x, y;
        int xOffset = 10;
        int yOffset = 145;
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
        if (this.orientation == Dir.LEFT)
            x -= 160;
        this.xPos = (int)((x + xOffset)*.75);
        this.yPos = (int)((y - yOffset)*.75);
    }  
}
