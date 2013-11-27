/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameLogic;

import java.awt.Graphics;
import SnakesAndLaddersApplet.SnakesAndLadders;

/**
 *
 * @author Dylan
 */
public class Player {
    private String playerName;
    private GamePiece _gamePiece; //0 1 2 or 3
    private int boardPosition; //1 to 100
    private int starCount; //0 1 or 2
    private static int numPlayers = 0;
    
    /* CSCI331 DS ENCAPSULATION
     * The variable boardPosition is an example of a private variable with a 
     * getter and a setter. The reason why it would be bad to allow direct
     * access to a variable is because someone could change that variable to
     * something that would break the contract of that variable which could
     * cause a part of code to break. Direct access to this particular 
     * variable isn't allowed is because it should always be within the range
     * of 0 to 100 since that is the range of the board and the setter makes 
     * sure that that contract is always upheld.
     */
    
    public Player(){
        this.numPlayers++;
        this._gamePiece = new GamePiece(numPlayers);
        this.playerName = "player" + Integer.toString(numPlayers);
        this.boardPosition = 1;
	this.starCount = 0;
    }
    public int getBoardPos(){
        return this.boardPosition;
    }
    
    public boolean getAnimating(){
        return _gamePiece.getAnimating();
    }
    
    public String getPlayerName(){
        return this.playerName;
    }
    
    public int getMovePathSize(){
        return _gamePiece.getMovePathSize();
    }
            
    public void setBoardPos(int pos) {
        if (pos <= 100 && pos >= 0){
            _gamePiece.addMove(pos);
            this.boardPosition = pos;
        }
    }

    public void draw(Graphics g, SnakesAndLadders parent) {
        this._gamePiece.paint(g, parent);
    }
    public void addStar(){
        if (starCount < 2)
	    starCount++;
    }
    public void delStar(){
	if (starCount > 0)
	    starCount--;
    }
    public boolean hasStar(){
	if (starCount > 0)
		return true;
	return false;
    }

    public int getStarCount() {
        return this.starCount;
    }
}



