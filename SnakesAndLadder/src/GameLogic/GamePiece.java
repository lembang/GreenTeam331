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
    private Boolean direction;
    private int square;
    private int baseXPos;
    private boolean animating;
    private static final int ANIM_FRAMES = 20;
    
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
        this.direction = true;
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
    
    public void move(int square){
        this.xPos = calcXPos(this.square);
        this.yPos = calcYPos(this.square);
        this.goXPos = calcXPos(square);
        this.goYPos = calcYPos(square);
        this.square = square;
        
        this.dx = (this.goXPos - this.xPos) / ANIM_FRAMES;
        this.dy = (this.goYPos - this.yPos) / ANIM_FRAMES;
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
