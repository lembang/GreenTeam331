/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameLogic;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.*;
import snakesandladders.Dice;
import snakesandladders.SnakesAndLadders;

/**
 *
 * @author Dylan, Divyata
 */
public class Board {
    BoardNode[] boardArray;
    private ArrayList snakeList;
    private ArrayList ladderList;
    private ArrayList playerList;
    private ArrayList starList;
    private static Image bgImage;
    private int boardSize;
    private static final int MAX_PLAYERS = 4;
    private int currPlayers;
    private int playerTurnNum;
    private static Dice _dice;
    
    
    
    public Board(){
        //constructor
        boardSize = 100;
        currPlayers = 0;
        playerTurnNum = 0;
        boardArray = new BoardNode[boardSize];
        for (int i = 0; i < boardSize; i++){
            boardArray[i] = new BoardNode(i);
        }
        snakeList = new ArrayList();
        ladderList = new ArrayList();
        playerList = new ArrayList();
        starList = new ArrayList();
        _dice = new Dice();
        try {
    	  bgImage = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/imgs/background.png"));
        }
        catch (Exception e) {
     	  System.out.printf(e.toString() + "\n");
        }
    }
    
    public int getPlayerTurnNum(){
        return this.playerTurnNum;
    }
    
    public boolean addPlayer(Player _player){
        if (currPlayers < MAX_PLAYERS){
            playerList.add(_player);
            currPlayers++;
            return true;
        }
        return false;
    }
    
    public void tempDisplayStatus(){
        System.out.printf("\n");
        for (int i = 0; i < currPlayers; i++) {
            Player _currPlayer = (Player)this.playerList.get(i);
            System.out.printf("Player" + i + "ON:" + _currPlayer.getBoardPos());
            System.out.printf(" Stars:" + _currPlayer.getStarCount());
            System.out.printf("\n");
        }
        System.out.printf("\n");
    }
    
    public void drawBG(Graphics g, SnakesAndLadders parent){
        //implement meee
        g.drawImage(bgImage, 0, 0, parent);
    }

    public boolean addSnake(){
        Snake _snakeInstance = new Snake();
        //check for duplicate
        //if duplicate return false
        for(Iterator iterator = snakeList.iterator(); iterator.hasNext();) {
            Snake _currSnake = (Snake) iterator.next();
            if (_currSnake.getHeadPos() == _snakeInstance.getHeadPos())
                return false;
        }
        
        //add snake to board
        int head = _snakeInstance.getHeadPos();
        int tail = _snakeInstance.getTailPos();
        this.boardArray[head-1].containsSnakeHead = true;
        this.boardArray[tail-1].containsSnakeTail = true;
        this.snakeList.add(_snakeInstance);
        return true;
    }
    
    public boolean addLadder(){
        Ladder _ladderInstance = new Ladder();
        //check for duplicate
        //if duplicate return false
        int posCheck = _ladderInstance.getBottomPos();
        if (boardArray[posCheck-1].containsLadderBottom == true){
            return false;
        }
        
        //add ladder to board
        int bottom = _ladderInstance.getBottomPos();
        int top = _ladderInstance.getTopPos();
        this.boardArray[bottom-1].containsLadderBottom = true;
        this.boardArray[top-1].containsLadderTop = true;
        this.ladderList.add(_ladderInstance);
        return true;
    }

    public boolean addStar(){
        Star _starInstance = new Star();
        int pos = _starInstance.getBoardPosition();
        if (boardArray[pos-1].containsStar == true){
            return false;
        }
        //add star to board
        this.boardArray[pos-1].containsStar = true;
        this.starList.add(_starInstance);
        return true;
    }

    public void drawBoard(Graphics g, SnakesAndLadders parent){
        //draw the BG
        this.drawBG(g, parent);
        

        //draw stars
        for(Iterator iterator = starList.iterator(); iterator.hasNext();) {
            Star _currStar = (Star) iterator.next();
            _currStar.draw(g, parent);
        }
        
        //draw ladders
        for(Iterator iterator = ladderList.iterator(); iterator.hasNext();) {
            Ladder _currLadder = (Ladder) iterator.next();
            _currLadder.draw(g, parent);
        }
        //draw the snakes
        for(Iterator iterator = snakeList.iterator(); iterator.hasNext();) {
            Snake _currSnake = (Snake) iterator.next();
            _currSnake.draw(g, parent);
        }
        //draw players
        for(Iterator iterator = playerList.iterator(); iterator.hasNext();) {
            Player _currPlayer = (Player) iterator.next();
            _currPlayer.draw(g, parent);
        }
        
        //draw dice
        _dice.paintComponent(g, parent);
    }
    public boolean movePlayer(){
        int numMoves = getMovement();
        Player _currPlayer = (Player)this.playerList.get(playerTurnNum);
        int firstPos = _currPlayer.getBoardPos();
        int endPos = firstPos + numMoves;
             
        if (endPos >= 100){ 
            _currPlayer.setBoardPos(100);
            System.out.print("MOVE PLAYER[" + _currPlayer.getPlayerName() 
                         + "] FROM [" + firstPos + "] TO SQ [100]\n\n");
            return false; //end of game
        }

        System.out.print("MOVE PLAYER[" + _currPlayer.getPlayerName() 
                         + "] FROM [" + firstPos + "] TO SQ [" + endPos + "]\n\n");
        
        int arrayEndPos = endPos-1;
        if (this.boardArray[arrayEndPos].containsStar){
            _currPlayer.addStar();
        }
        
        if (this.boardArray[arrayEndPos].containsLadderBottom){
        	for(Iterator iterator = ladderList.iterator(); iterator.hasNext();) {
                Ladder _currLadder = (Ladder) iterator.next();
                 if (_currLadder.getBottomPos() == endPos){
                     endPos = _currLadder.getTopPos();
                     System.out.printf("LADDER FOR PLAYER" + playerTurnNum + " FROM:" 
                             + (_currLadder.getAffectsSquare()) + "-TO-" + (endPos) + "\n\n");
                     break;
                 }
             }
        }
        
        if (this.boardArray[arrayEndPos].containsSnakeHead){
            //check if user has a star
            boolean star = _currPlayer.hasStar();
            if (star) {
                System.out.print("STAR USED FOR PLAYER" + _currPlayer.getPlayerName() + "\n\n");
                _currPlayer.delStar();
            } else {
                //find snake and update endPos
                for(Iterator iterator = snakeList.iterator(); iterator.hasNext();) {
                   Snake _currSnake = (Snake) iterator.next();
                    if (_currSnake.getAffectsSquare() == endPos){
                        endPos = _currSnake.getMoveToSquare();
                        System.out.printf("SNAKE FOR PLAYER" + playerTurnNum + " FROM:" 
                                + _currSnake.getAffectsSquare() + "-TO-" + endPos + "\n\n");
                        break;
                    }
                }
            }
        }
        _currPlayer.setBoardPos(endPos);
        playerTurnNum = ++playerTurnNum % currPlayers;
        return true;
    }


    private static int rollDice(){
        return 1 + (int)(Math.random()*6); 
    }
    
    private static int getMovement(){
        _dice.twoDice();
        int diceRoll = _dice.getDiceTotal();
        //check for any global modifications to movement here
        int postMove = diceRoll; //none implemented atm
        // end of movement modifications
        return postMove;
    }
    
    public int calcXPos(int square){
        if (square % 20 < 11 && square % 20 != 0){
            square--;
            //board number increment left to right eg 1 2 3 .. 10
            return ((square % 10)*80);
        } else {
            //board number increment right to left 20 19 18 .. 11
            square--;
            return ((9 - (square % 10)) * 80);
        }
    }
    public int calcYPos(int square){
        return 730 - ((square/10)*80);
    }
    
}
