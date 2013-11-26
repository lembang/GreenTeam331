/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SnakesAndLaddersApplet;

/**
 *
 * @author the
 */

import GameLogic.BoardMulti;
import GameLogic.Player;
import RMINetwork.*;
import java.awt.Color;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class SnakesAndLaddersMulti extends SnakesAndLadders implements Runnable {
   private RMIClient rClient;
   private static final boolean fastmode = false; //FOR DEBUGGING PURPOSES
   private int plyrnumb;
   
   public SnakesAndLaddersMulti(){
       this.gameMode = 0;
       this.snakes = 10;
       this.ladders = 10;
       this.stars = 5;
   }
   
   public SnakesAndLaddersMulti(int gameMode, boolean educationalMode, int snakes, int ladders, int stars){
        this.gameMode = gameMode;
        this.snakes = snakes;
        this.ladders = ladders;
        this.stars = stars;
        this.educationalMode = educationalMode;
   }
   
   @Override
   public void init() {
        this.requestFocus();
        this.screenWidth = getSize().width;
        this.screenHeight = getSize().height;
        setBackground(Color.BLACK);
        addKeyListener( this );
        addMouseListener( this );
        
        _gameBoard = new BoardMulti();
        
        boolean ok = true;
        while (ok){ //add max players
            Player _newPlayer = new Player();
            ok = _gameBoard.addPlayer(_newPlayer);
        }
        
        for (int i = 0; i < this.snakes; i++){
            boolean result = _gameBoard.addSnake();
            while (!result)
                result = _gameBoard.addSnake();
        }
        for (int i = 0; i < this.ladders; i++){
            boolean result = _gameBoard.addLadder();
            while (!result)
                result = _gameBoard.addLadder();
        }
        for (int i = 0; i < this.stars; i++){
            boolean result = _gameBoard.addStar();
            while (!result){
                result = _gameBoard.addStar();
            }
        }
        
        _gameBoard.swapLists();
        
        
        //define a new thread
        boardThread = new Thread(this);
        boardThread.start();
       try {
           intrntMulti();
       } catch (RemoteException ex) {
           JOptionPane.showMessageDialog(null,"Connection Error :" + ex);
            System.exit(0);
       }
    }
   
    /**
     * Internet Multi player
     * @author the 
     * @throws java.rmi.RemoteException 
     */
    public void intrntMulti() throws RemoteException{
        rClient = new RMIClient();
        rClient.createConnection(3103);
        plyrnumb = rClient.getNumber();
        rClient.connect(String.valueOf(plyrnumb));
        //System.err.println(String.valueOf(plyrnumb));
    }
    /**
     * 
     * Need Dylan Helps
     */
    
    public void generateGameBoard(){
        //should be the location of snake and ladders & stars
        
    }
    
    public void SyncState(){
        //location of pawns, star, star owned
    }
}