/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameLogic;

import java.awt.Graphics;
import snakesandladders.GamePiece;
import snakesandladders.SnakesAndLadders;

/**
 *
 * @author Dylan
 */
public class Player {
    private String playerName;
    GamePiece _gamePiece; //0 1 2 or 3
    private int boardPosition; //0 to 99
    private int starCount; //0 1 or 2
    private static int numPlayers = 0;
    
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
    
    public String getPlayerName(){
        return this.playerName;
    }
            
    public void setBoardPos(int pos) {
        _gamePiece.move(pos);
        this.boardPosition = pos;
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



