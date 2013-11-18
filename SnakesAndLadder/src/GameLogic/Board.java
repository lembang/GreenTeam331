/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameLogic;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.*;
import SnakesAndLaddersApplet.SnakesAndLadders;
import javax.swing.JFrame;

/**
 *
 * @author Dylan
 * edited by Divyata
 */
public class Board {
    private BoardNode[] boardArray;
    
    private ArrayList snakeList;
    private ArrayList ladderList;
    private ArrayList playerList;
    private ArrayList starList;
    private static Image bgImage;
    protected int boardSize;
    protected static final int MAX_PLAYERS = 4;
    protected int currPlayers;
    protected int playerTurnNum;
    protected static Dice _dice;
    protected ScoreBoard _scoreBoard;
    protected Winner _winGFX;
    protected boolean winner = false;
    protected boolean educationalMode = false;
    
   
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
        _scoreBoard = new ScoreBoard();
        try {
    	  bgImage = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/imgs/background.png"));
        }
        catch (Exception e) {
     	  System.out.printf(e.toString() + "\n");
        }
    }
    
    public void setEducationalMode(boolean educationalMode) {
        this.educationalMode = educationalMode;
    }
    
    public int getPlayerTurnNum(){
        return this.playerTurnNum;
    }
    
    public void AIMove(){
        //TODO impelent meee
    }
    
    public boolean addPlayer(Player _player){
        if (currPlayers < MAX_PLAYERS){
            getPlayerList().add(_player);
            _scoreBoard.updatePlayerName( this.currPlayers , _player.getPlayerName() );
            currPlayers++;
            _scoreBoard.updateCurrPlayers(this.currPlayers);
            return true;
        }
        return false;
    }
    
    public void tempDisplayStatus(){
        System.out.printf("\n");
        for (int i = 0; i < currPlayers; i++) {
            Player _currPlayer = (Player)this.getPlayerList().get(i);
            System.out.printf("Player" + i + "ON:" + _currPlayer.getBoardPos());
            System.out.printf(" Stars:" + _currPlayer.getStarCount());
            System.out.printf("\n");
        }
        System.out.printf("\n");
    }
    
    public void drawBG(Graphics g, SnakesAndLadders parent){
        g.drawImage(bgImage, 0, 0, parent);
    }

    public boolean addSnake(){
        Snake _snakeInstance = new Snake();
        //check for duplicate
        //if duplicate return false
        for(Iterator iterator = getSnakeList().iterator(); iterator.hasNext();) {
            Snake _currSnake = (Snake) iterator.next();
            if (_currSnake.getHeadPos() == _snakeInstance.getHeadPos())
                return false;
        }
        
        //add snake to board
        int head = _snakeInstance.getHeadPos();
        int tail = _snakeInstance.getTailPos();
        this.boardArray[head-1].containsSnakeHead = true;
        this.boardArray[tail-1].containsSnakeTail = true;
        this.getSnakeList().add(_snakeInstance);
        return true;
    }
    
    public void diceRoll(){
        _dice.twoDice();
    }
    
    public boolean addLadder(){
        Ladder _ladderInstance = new Ladder();
        //check for duplicate
        //if duplicate return false
        int posCheck = _ladderInstance.getBottomPos();
        if (getBoardArray()[posCheck-1].containsLadderBottom == true){
            return false;
        }
        
        //add ladder to board
        int bottom = _ladderInstance.getBottomPos();
        int top = _ladderInstance.getTopPos();
        this.boardArray[bottom-1].containsLadderBottom = true;
        this.boardArray[top-1].containsLadderTop = true;
        this.getLadderList().add(_ladderInstance);
        return true;
    }

    public boolean addStar(){
        Star _starInstance = new Star();
        int pos = _starInstance.getBoardPosition();
        if (getBoardArray()[pos-1].containsStar == true){
            return false;
        }
        //add star to board
        this.boardArray[pos-1].containsStar = true;
        this.getStarList().add(_starInstance);
        return true;
    }

    
    public void drawBoard(Graphics g, SnakesAndLadders parent){
        //draw the BG
        this.drawBG(g, parent); 
        
        this._scoreBoard.draw(g, parent);

        //draw stars
        for(Iterator iterator = getStarList().iterator(); iterator.hasNext();) {
            Star _currStar = (Star) iterator.next();
            _currStar.draw(g, parent);
        }
        
        //draw ladders
        for(Iterator iterator = getLadderList().iterator(); iterator.hasNext();) {
            Ladder _currLadder = (Ladder) iterator.next();
            _currLadder.draw(g, parent);
        }
        //draw the snakes
        for(Iterator iterator = getSnakeList().iterator(); iterator.hasNext();) {
            Snake _currSnake = (Snake) iterator.next();
            _currSnake.draw(g, parent);
        }
        //draw players
        for(Iterator iterator = getPlayerList().iterator(); iterator.hasNext();) {
            Player _currPlayer = (Player) iterator.next();
            _currPlayer.draw(g, parent);
        }
        
        //draw dice
        _dice.paintComponent(g, parent);
        
        if (winner){
            _winGFX.draw(g, parent);
        }
            
    }
       
    public void delBoardStar(int square){
        for(Iterator iterator = getStarList().iterator(); iterator.hasNext();) {
            Star _currStar = (Star) iterator.next();
            if (_currStar.getBoardPosition() == square){
                this.boardArray[square-1].containsStar = false;
                iterator.remove();
            }
                
        } 
        
    }
    
    public boolean movePlayer(){
        int numMoves = getMovement();
        Player _currPlayer = (Player)this.getPlayerList().get(playerTurnNum);
        int firstPos = _currPlayer.getBoardPos();
        int endPos = firstPos + numMoves;
        
        if (educationalMode && endPos <= 100){
            Question _question;
            if ((Math.random() * 2) >= 1){
                _question = new CountingQuestion(firstPos, numMoves);
            } else {
                _question = new AddingQuestion(_dice.getDice1(), _dice.getDice2());
            }
            while (!_question.isFinished()) {  
                    //wait until the question is finished
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        //do nothing
                    }
            }
            if (!_question.isCorrect()){
                playerTurnNum = ++playerTurnNum % currPlayers;
                _scoreBoard.updateCurrTurn(playerTurnNum);
                return true;
            }
        }
        
        
        System.out.print("MOVE PLAYER[" + _currPlayer.getPlayerName() 
                    + "] FROM [" + firstPos + "] TO SQ [" + endPos + "]\n\n");
        
        //check for end of game
        if (endPos >= 100){ 
                _currPlayer.setBoardPos(100);
                 this._winGFX = new Winner(_currPlayer.getPlayerName());
                 this.winner = true;
                return false; //end of game
        }
        for (int i = firstPos; i < endPos; i++){
            if (i%10 == 0) _currPlayer.setBoardPos(i);
            if (i%10 == 1) _currPlayer.setBoardPos(i);
        }
        //do initial move of player
        _currPlayer.setBoardPos(endPos);
        
        //recursively check for movement modifiers
        boolean ret = movePlayerModifiers(_currPlayer, endPos);
        if (ret == false) return false; //immediately check for end of game
        playerTurnNum = ++playerTurnNum % currPlayers;
        _scoreBoard.updateCurrTurn(playerTurnNum);
        return true; 
    }
    
    public boolean movePlayerModifiers(Player _currPlayer, int square){
        //detect infinite cycle movement bug
        if (_currPlayer.getMovePathSize() >= 15) return true;

        if (square >= 100){ 
                _currPlayer.setBoardPos(100);
                 this._winGFX = new Winner(_currPlayer.getPlayerName());
                 this.winner = true;
                return false; //end of game
        }

        int arrayEndPos = square-1;
        if (this.getBoardArray()[arrayEndPos].containsStar){
             _currPlayer.addStar();
             _scoreBoard.addStar(playerTurnNum);
             this.delBoardStar(square);
             return movePlayerModifiers(_currPlayer, square);
        }
        
        if (this.getBoardArray()[arrayEndPos].containsLadderBottom){
        	for(Iterator iterator = getLadderList().iterator(); iterator.hasNext();) {
                Ladder _currLadder = (Ladder) iterator.next();
                 if (_currLadder.getBottomPos() == square){
                     square = _currLadder.getTopPos();
                     _currPlayer.setBoardPos(square);
                     return movePlayerModifiers(_currPlayer, square);
                 }
             }
        }
        
        if (this.getBoardArray()[arrayEndPos].containsSnakeHead){
            //check if user has a star
            boolean star = _currPlayer.hasStar();
            if (star) {
                System.out.print("STAR USED FOR PLAYER" + _currPlayer.getPlayerName() + "\n\n");
                _currPlayer.delStar();
                _scoreBoard.delStar(playerTurnNum);
                return true;
            } else {
                //find snake and update endPos
                for(Iterator iterator = getSnakeList().iterator(); iterator.hasNext();) {
                   Snake _currSnake = (Snake) iterator.next();
                    if (_currSnake.getAffectsSquare() == square){
                        square = _currSnake.getMoveToSquare();
                        _currPlayer.setBoardPos(square);
                        return movePlayerModifiers(_currPlayer, square);
                    }
                }
            }
        }
        
        return true;
    }


    private static int rollDice(){
        return 1 + (int)(Math.random()*6); 
    }
    
    private static int getMovement(){
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

    /**
     * @return the snakeList
     */
    public ArrayList getSnakeList() {
        return snakeList;
    }

    /**
     * @param snakeList the snakeList to set
     */
    public void setSnakeList(ArrayList snakeList) {
        this.snakeList = snakeList;
    }

    /**
     * @return the ladderList
     */
    public ArrayList getLadderList() {
        return ladderList;
    }

    /**
     * @param ladderList the ladderList to set
     */
    public void setLadderList(ArrayList ladderList) {
        this.ladderList = ladderList;
    }

    /**
     * @return the playerList
     */
    public ArrayList getPlayerList() {
        return playerList;
    }

    /**
     * @param playerList the playerList to set
     */
    public void setPlayerList(ArrayList playerList) {
        this.playerList = playerList;
    }

    /**
     * @return the starList
     */
    public ArrayList getStarList() {
        return starList;
    }

    /**
     * @param starList the starList to set
     */
    public void setStarList(ArrayList starList) {
        this.starList = starList;
    }

    /**
     * @return the boardArray
     */
    public BoardNode[] getBoardArray() {
        return boardArray;
    }

    /**
     * @param boardArray the boardArray to set
     */
    public void setBoardArray(BoardNode[] boardArray) {
        this.boardArray = boardArray;
    }
    
    public void swapLists(){
      //do nothing, interface class
    }
    
}

