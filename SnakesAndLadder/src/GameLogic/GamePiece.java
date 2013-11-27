/* Gamepiece.java
 * Implements the gamepieces and navigates them over the board
 * 
 */
package GameLogic;

/**
 *
 * @author kenyonheffner
 * edited by dylan
 */
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Vector;
import SnakesAndLaddersApplet.SnakesAndLadders;


public class GamePiece {
    
    private int xPos;
    private int yPos;
    private int goXPos;
    private int goYPos;
    private double dx;
    private double dy;
    Vector<Integer> goSquares;
    private int color; //red=1,blue=2,green=3,yellow=4,
    private Image pieceImage;
    private int square;
    private int baseXPos;
    private boolean animating;
    
    public int getMovePathSize(){
        return goSquares.size();
    }
    
    public Boolean getAnimating(){
        return this.animating;
    }
    
    public GamePiece(int plNum){
        this.yPos = 550;
        animating = false;
        this.dx = 0;
        this.dy = 0;
        this.color = plNum;
        this.getImage(color);
        this.square = 1;
        this.xPos += 4*plNum;
        this.baseXPos = 0 + 4*plNum;
        this.xPos = calcXPos(this.square);
        this.yPos = calcYPos(this.square);
        this.goXPos = calcXPos(square);
        this.goYPos = calcYPos(square);
        goSquares = new Vector<>();
    } 
    public void getImage(int pieceNum){
        this.pieceImage = Toolkit.getDefaultToolkit().getImage(getClass().getResource
                ("/resources/imgs/gamePieces/"+pieceNum +".png"));
    }
    
    public int getX(){
        return this.xPos;
    }
    public int getY(){
        return this.yPos;
    }
    
    private int calcXPos(int square){
        int ret;
        if (square % 20 < 11 && square % 20 != 0){
            square--;
            //board number increment left to right eg 1 2 3 .. 10
            ret = ((square % 10)*80) + this.baseXPos;
        } else {
            //board number increment right to left 20 19 18 .. 11
            square--;
            ret = ((9 - (square % 10)) * 80) + this.baseXPos;
        }
        ret = (int) (ret* 0.75);
        return ret;
    }
    private int calcYPos(int square){
        int ret;
        ret = 730 - ((square/10)*80);
        if (square % 10 == 0)
            ret += 80;
        ret = (int) (ret * 0.75)+5;
        return ret;
    }
    
    public void addMove(int square){
        goSquares.add(square);
    }
    //CSCI331 LAB5
    /* Here is the point in our code where we have implemented animation. The 
     * way it works is by utilizing a FIFO queue of board squares numbers that
     * the game piece should animate towards in succession. The reason we have
     * implemented it this way is because when you travel through the squares
     * that are divisable by 10 and the ones directly after it, you are going 
     * around a corner on the board so there is three places you travel to. After
     * that there is the possibility of landing on a snake or a ladder so there
     * is even more possible places a game piece might need to travel in one move
     * every time the game piece is drawn, an update function is called that 
     * either continues animating the game piece to where it should be going, 
     * pulls a square off the fifo queue if it isn't empty and begins animating
     * the game piece towards that square, or stops the animation of the game 
     * piece if there is no more squares to animate to. This provides a smooth
     * animation style that is scalable and makes sense within the context of 
     * this particular board game.
     */
    
    public void move(int square){
        this.xPos = calcXPos(this.square);
        this.yPos = calcYPos(this.square);
        this.goXPos = calcXPos(square);
        this.goYPos = calcYPos(square);
        int numSquares = square - this.square;
        this.square = square;
        int speedModifier;
        if (numSquares > 0 && numSquares <= 10){
            speedModifier = numSquares * 5;
        } else { 
            speedModifier = 20;     
        }
        this.dx = (this.goXPos - this.xPos) / speedModifier;
        this.dy = (this.goYPos - this.yPos) / speedModifier;
        animating = true;
    }   
    public void paint(Graphics g, SnakesAndLadders parent)
    {          
        update();
        g.drawImage(pieceImage,this.xPos,this.yPos, parent);   
    }
    
    public void update() { 
       if (animating == false){  
         if (goSquares.size() > 0){
           this.move( goSquares.firstElement() );
           goSquares.removeElementAt( 0 );
         }
       } else {
        if (this.dx >= 0){
          if (this.xPos < this.goXPos)
            this.xPos = this.xPos + (int)this.dx;
          else
            this.xPos = this.goXPos;
          if (this.xPos > this.goXPos)
            this.xPos = this.goXPos;
        } else {
          if (this.xPos > this.goXPos)
            this.xPos = this.xPos + (int)this.dx;
          else
            this.xPos = this.goXPos;
          if (this.xPos < this.goXPos)
            this.xPos = this.goXPos;
        }

        if (this.dy >= 0){
          if (this.yPos < this.goYPos)
            this.yPos = this.yPos + (int)this.dy;
          else
            this.yPos = this.goYPos;
          if (this.yPos > this.goYPos)
            this.yPos = this.goYPos;
        } else {
          if (this.yPos > this.goYPos)
            this.yPos = this.yPos + (int)this.dy;
          else
            this.yPos = this.goYPos;
          if (this.yPos < this.goYPos)
            this.xPos = this.goYPos;
        }

        if (this.goXPos == this.xPos && this.yPos == this.goYPos){
            animating = false;
        }
      }
       
    }
    public int getSquare(){
       return this.square;
    }
    
}
