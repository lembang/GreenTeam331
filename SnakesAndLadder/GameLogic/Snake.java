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
 * @author Dylan
 */
public class Snake {
    public enum Dir{
        LEFT, RIGHT
    }
    public enum Color{
        RED, BLUE, YELLOW, GREEN
    }
    private int affectsSquare;
    private int movesToSquare;
    private Dir orientation;
    private Color snakeColor;
    private int xPos;
    private int yPos;
    private Image snakeIMG;
    
    public int getHeadPos(){
        return this.affectsSquare;
    }
    public int getTailPos(){
        return this.movesToSquare;
    }

    public void draw(Graphics g, SnakesAndLadders parent) {
        g.drawImage(snakeIMG,xPos,yPos, parent);   
    }
    
    Snake(){
        //set orientation randomly
        int rand = 1 + (int)(Math.random()*2);
        if (rand == 1){
            this.orientation = Dir.LEFT;
        } else {
            this.orientation = Dir.RIGHT;
        }
        //get random color
        this.snakeColor = Color.values()[(int)(Math.random()*Color.values().length)];
        //get random position for head calculated in terms of board positions
        this.affectsSquare = getRandomHeadPosition();
        this.movesToSquare = calculateTailPosition();
        calcDrawXY();
        try {
          if (this.orientation == Dir.LEFT)
            this.snakeIMG = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/imgs/snakes/snake1Blue.png"));
          else 
            this.snakeIMG = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/imgs/snakes/snake1BlueFlipped.png"));  
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
    
    public int getAffectsSquare() {
        return this.affectsSquare;
    }

    public int getMoveToSquare() {
        return this.movesToSquare;
    }
    
    private int getRandomHeadPosition(){
        //an exceptionally recursive function
        try {
            int boardPosition = 21 + (int)(Math.random() * ((99 - 21) + 1));
            if (this.orientation == Dir.LEFT){
                if (boardPosition % 20 < 11 && boardPosition % 20 != 0){
                   //board number increment left to right eg 1 2 3 .. 10
                    //check out of bounds
                   if (boardPosition % 10 >= 1 && boardPosition % 10 <= 3)
                       throw new Exception();
                } else {
                    //board number increment right to left 20 19 18 .. 11
                    //check out of bounds
                    if (boardPosition % 10 == 0 || boardPosition % 10 >= 8)
                       throw new Exception();
                }
            } else { //orientation == Dir.RIGHT
                if (boardPosition % 20 < 11 && boardPosition % 20 != 0){
                    //board number increment left to right eg 1 2 3 .. 10
                    //check out of bounds
                    if (boardPosition % 10 == 0 || boardPosition % 10 >= 8)
                       throw new Exception();  
                } else {
                   //board number increment right to left 20 19 18 .. 11
                   //check out of bounds
                   if (boardPosition % 10 >= 1 && boardPosition % 10 <= 3)
                       throw new Exception();
                }
            }
            return boardPosition;
        } catch(Exception e) {
            return getRandomHeadPosition();
        }
    }


    private int calculateTailPosition(){
        //Assumes correctness of this.affectsSquare
        //Assumes snake rise of 3
        //Assumes snake run of 4
        int boardPosition = this.affectsSquare;
        if (this.orientation == Dir.LEFT){
                if (boardPosition % 20 < 11 && boardPosition % 20 != 0){
                   //board number increment left to right eg 1 2 3 .. 10
                   return boardPosition - 23;
                } else {
                   //board number increment right to left 20 19 18 .. 11
                   return boardPosition - 17;
                }
        } else { //orientation == Dir.RIGHT
                if (boardPosition % 20 < 11 && boardPosition % 20 != 0){
                   //board number increment left to right eg 1 2 3 .. 10
                   return boardPosition - 17;
                } else {
                   //board number increment right to left 20 19 18 .. 11
                   return boardPosition - 23;
                }
        }
    }
    
    private void calcDrawXY(){
        
        int square = this.affectsSquare;
        int x, y;
        int xOffset = 200;
        int yOffset = 160;
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
        if (this.orientation == Dir.RIGHT)
            x += 220;
        this.xPos = (int)((x - xOffset)*.75);
        this.yPos = (int)((y + 5)*.75);
    }  
}
