/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameLogic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import SnakesAndLaddersApplet.SnakesAndLadders;

/** CSCI331 RH ENCAPSULATION
 *  This class has getters and setters for 
 *  the currPlayers variable.
 *
 */
public class ScoreBoard {
    private int currPlayers;
    private String playerNames[];
    private int playerStars[];
    private int currTurn;
    private int startX;
    private int startY;
    private int width;
    private int height;
    int plNum;
    private final static int MAX_PLAYERS = 4;
    
    public void addStar(int playerNum){
        if (this.playerStars[playerNum] < 2)
            this.playerStars[playerNum]++;
    }
    
    public void delStar(int playerNum){
        if (this.playerStars[playerNum] > 0)
            this.playerStars[playerNum]--;
    }
    
    public void updateCurrPlayers(int num){
        this.currPlayers = num;
    }
    public void updatePlayerName(int playerNum, String name){
        this.playerNames[playerNum] = name;
    }
    public void updateCurrTurn(int turnNum){
        this.currTurn = turnNum;
    }
    
    public ScoreBoard(){
        playerNames = new String[MAX_PLAYERS];
        playerStars = new int[MAX_PLAYERS];
        for (int i = 0; i < MAX_PLAYERS; i++) playerStars[i] = 0;
        startX = 610;
        startY = 10;
        width = 265;
        height = 350;
    }
    public void draw(Graphics g, SnakesAndLadders parent) {
         g.setColor(Color.BLACK);
         g.fillRect(  startX-3,   startY-3,  width+6,    height+6);
        Color BG = new Color(180,180,180);
        g.setColor(BG);
        g.fillRect(startX,startY,width,height);
        g.setColor(Color.BLACK);
        int lastY = 0;
       Font f = new Font("Serif",Font.BOLD,25);
        
        g.setFont(f);
       
        
        for (int i=0 ; i < this.getCurrPlayers(); i++){
           
            g.drawString(playerNames[i], startX + 20 , startY + 20 + (i*50));
            lastY = startY + 20 + (i*50);
        }
        
        lastY += 60;
        g.drawString("Current Turn:", startX + 20 , lastY);
        lastY += 60;
                
        g.drawString("Player: " + playerNames[currTurn], startX + 20 , lastY);
    }
    
    /**
     * @return the currPlayers
     */
    public int getCurrPlayers() {
        return this.currPlayers;
    }

    /**
     * @param currPlayers the currPlayers to set
     */
    public void setCurrPlayers(int currPlayers) {
        if (currPlayers>=0 && currPlayers<=MAX_PLAYERS)   
               this.currPlayers = currPlayers;
    }
}

