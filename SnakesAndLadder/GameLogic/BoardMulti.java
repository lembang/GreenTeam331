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

/**
 *
 * @author Dylan
 * 
 */
public class BoardMulti extends Board {
    private ArrayList initSnakeList;
    private ArrayList initLadderList;
    private ArrayList initPlayerList;
    private ArrayList initStarList;
    private BoardNode[] initBoardArray;
    
    public BoardMulti(){
        super();
        this.initBoardArray = new BoardNode[100];
        for (int i = 0; i < 100; i++){
            this.initBoardArray[i] = new BoardNode(i);
        }
        initSnakeList = new ArrayList();
        initLadderList = new ArrayList();
        initPlayerList = new ArrayList();
        initStarList = new ArrayList();
    }
    
    public void swapLists(){
            this.setPlayerList(this.initPlayerList);
            this.setSnakeList(this.initSnakeList);
            this.setLadderList(this.initLadderList);
            this.setStarList(this.initStarList);
            this.setBoardArray(this.initBoardArray);
    }
    
    @Override
    public boolean addSnake(){
        Snake _snakeInstance = new Snake();
        //check for duplicate
        //if duplicate return false
        for(Iterator iterator = this.initSnakeList.iterator(); iterator.hasNext();) {
            Snake _currSnake = (Snake) iterator.next();
            if (_currSnake.getHeadPos() == _snakeInstance.getHeadPos())
                return false;
        }
        
        //add snake to board
        int head = _snakeInstance.getHeadPos();
        int tail = _snakeInstance.getTailPos();
        this.initBoardArray[head-1].containsSnakeHead = true;
        this.initBoardArray[tail-1].containsSnakeTail = true;
        this.initSnakeList.add(_snakeInstance);
        return true;
    }
    
    @Override
    public boolean addLadder(){
        Ladder _ladderInstance = new Ladder();
        //check for duplicate
        //if duplicate return false
        int posCheck = _ladderInstance.getBottomPos();
        if (initBoardArray[posCheck-1].containsLadderBottom == true){
            return false;
        }
        
        //add ladder to board
        int bottom = _ladderInstance.getBottomPos();
        int top = _ladderInstance.getTopPos();
        this.initBoardArray[bottom-1].containsLadderBottom = true;
        this.initBoardArray[top-1].containsLadderTop = true;
        this.initLadderList.add(_ladderInstance);
        return true;
    }

    @Override
    public boolean addStar(){
        Star _starInstance = new Star();
        int pos = _starInstance.getBoardPosition();
        if (initBoardArray[pos-1].containsStar == true){
            return false;
        }
        //add star to board
        this.initBoardArray[pos-1].containsStar = true;
        this.initStarList.add(_starInstance);
        return true;
    }
    
    @Override
    public boolean addPlayer(Player _player){
        if (this.currPlayers < this.MAX_PLAYERS){
            initPlayerList.add(_player);
            _scoreBoard.updatePlayerName( currPlayers , _player.getPlayerName() );
            currPlayers++;
            _scoreBoard.updateCurrPlayers(this.currPlayers);
            return true;
        }
        return false;
    }

    /**
     * @return the initSnakeList
     */
    public ArrayList getInitSnakeList() {
        return initSnakeList;
    }

    /**
     * @param initSnakeList the initSnakeList to set
     */
    public void setInitSnakeList(ArrayList initSnakeList) {
        this.initSnakeList = initSnakeList;
    }

    /**
     * @return the initLadderList
     */
    public ArrayList getInitLadderList() {
        return initLadderList;
    }

    /**
     * @param initLadderList the initLadderList to set
     */
    public void setInitLadderList(ArrayList initLadderList) {
        this.initLadderList = initLadderList;
    }

    /**
     * @return the initPlayerList
     */
    public ArrayList getInitPlayerList() {
        return initPlayerList;
    }

    /**
     * @param initPlayerList the initPlayerList to set
     */
    public void setInitPlayerList(ArrayList initPlayerList) {
        this.initPlayerList = initPlayerList;
    }

    /**
     * @return the initStarList
     */
    public ArrayList getInitStarList() {
        return initStarList;
    }

    /**
     * @param initStarList the initStarList to set
     */
    public void setInitStarList(ArrayList initStarList) {
        this.initStarList = initStarList;
    }
}