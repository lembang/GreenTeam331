/* Gamepiece.java
 * Implements the gamepieces and navigates them over the board
 * 
 */
package snakesandladders;

/**
 *
 * @author kenyonheffner
 * edited by dylan
 */
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;


public class GamePiece {
    
    private int xPos;
    private int yPos;
    private int color; //red=1,blue=2,green=3,yellow=4,
    private Image pieceImage;
    private Boolean direction;
    private int square;
    private int baseXPos;
    
    //private Thread pieceThread;
    
    public GamePiece(int plNum){
        this.xPos = 0;
        this.yPos = 550;
        this.color = plNum;
        this.getImage(color);
        this.direction = true;
        this.square = 1;
        this.xPos += 4*plNum;
        this.baseXPos = 0 + 4*plNum;
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
    public void move(int square){
        if (square % 20 < 11 && square % 20 != 0){
            square--;
            //board number increment left to right eg 1 2 3 .. 10
            this.xPos = ((square % 10)*80) + this.baseXPos;
        } else {
            //board number increment right to left 20 19 18 .. 11
            square--;
            this.xPos = ((9 - (square % 10)) * 80) + this.baseXPos;
        }
        this.yPos = 730 - ((square/10)*80);
        this.xPos = (int) (this.xPos* 0.75);
        this.yPos = (int) (this.yPos * 0.75)+5;
    }   
    public void paint(Graphics g, SnakesAndLadders parent)
    {           
        g.drawImage(pieceImage,xPos,yPos, parent);   
    }
    public void update() {
       this.xPos = this.getX();
       this.yPos = this.getY();
    }
    public int getSquare(){
      
       int x = getX();
       int y = getY();
       
       
       return this.square;
       
    }
    
}
