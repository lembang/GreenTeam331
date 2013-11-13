/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameLogic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import SnakesAndLaddersApplet.SnakesAndLadders;

/**
 *
 *
 */
public class ScoreBoard {
    private int currPlayers;
    private String playerNames[];
    private int currTurn;
    private int startX;
    private int startY;
    private int width;
    private int height;
    private final static int MAX_PLAYERS = 4;
    
    
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
        startX = 610;
        startY = 10;
        width = 265;
        height = 410;
    }
    public void draw(Graphics g, SnakesAndLadders parent) {
        //g.drawImage(thisIMG,xPos,yPos, parent);
        //draw stuff here, fix it all
        Color BG = new Color(200,200,200);
        g.setColor(BG);
        g.fillRect(startX,startY,width,height);
        g.setColor(Color.BLACK);
        int lastY = 0;
        Font font = new Font("Arial", Font.PLAIN, 20);
        g.setFont(font);
        for (int i = 0; i < this.currPlayers; i++){
            g.drawString(playerNames[i], startX + 20 , startY + 20 + (i*40));
            lastY = startY + 20 + (i*40);
        }
        lastY += 40;
        g.drawString("Current Turn:", startX + 20 , lastY);
        lastY += 40;
        g.drawString("Player: " + playerNames[currTurn], startX + 20 , lastY);
    }
}

