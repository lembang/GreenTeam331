/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SnakesAndLaddersApplet;
/**
 *
 * @author dylan and kenyon
 */

 /* CSCI331 DS SUPERCLASS
 *  SnakesAndLadders is a class that stands on its own but it is also
 *  a superclass for the SnakesAndLaddersMulti class. 
 *  > I abstracted out the ideas that went into the superclass because the 
 * superclass and subclass are each going to be.
 *  > The subclass adds the ability to override code in the superclass so 
 *  that different code will be ran when an instance of the subtype is 
 *  created rather than an instance of this type.
 *  > There are no other siblings or subclasses for this superclass, but a 
 *  possible other subclass would be to seperate local singleplayer mode and 
 *  local multiplayer mode by creating a local multiplayer class using the same 
 *  overriding system rather than control statements. The reason why we just 
 *  used control statements is because the amount of change is very small 
 *  between the two modes.
 */

import GameLogic.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class SnakesAndLadders extends JPanel
   implements Runnable, KeyListener, MouseListener {
     
   private Graphics dbg;
   private Image dbImage;
   protected int screenHeight;
   protected int screenWidth;
   protected Thread boardThread;
   protected Board _gameBoard = new Board();
   protected int gameMode;
   protected int snakes;
   protected int ladders;
   protected int stars;
   protected boolean educationalMode;
   protected boolean waiting = false;
   protected Thread waitThread;
   private static final boolean fastmode = false; //FOR DEBUGGING PURPOSES
   
   public SnakesAndLadders(){
       this.gameMode = 0;
       this.snakes = 10;
       this.ladders = 10;
       this.stars = 5;
   }
   
   public SnakesAndLadders(int gameMode, boolean educationalMode, int snakes, int ladders, int stars){
        this.gameMode = gameMode;
        this.snakes = snakes;
        this.ladders = ladders;
        this.stars = stars;
        this.educationalMode = educationalMode;
   }
   
   
   
   @Override
   public void keyPressed( KeyEvent e ) { }
   @Override
   public void keyReleased( KeyEvent e ) { }
   @Override
   public void keyTyped( KeyEvent e ) {
    char c = e.getKeyChar();      
      if ( c != KeyEvent.CHAR_UNDEFINED ) {
                               
         if (c == ' ') 
         {                 
             if (!waiting){
                this.movePlayer();
             }
         }
         repaint();
         e.consume();
         
      }   
   }

   public void movePlayer(){
	   waiting = true;
       _gameBoard.diceRoll();
       if (!fastmode){ //FOR DEBUGGING PURPOSES
       this.waitThread = new Thread() {
               /* CSCI331 DS OVERRIDING
                * Here is an example of method overriding.
                * We are overriding the run method for the class Thread
                * for our new instance. This lets us specify our own code
                * for the thread to run when our thread calls the run method 
                * during its default operation.
                */
               @Override
               public void run() {
                  try {
                     Thread.sleep(1500);
                     _gameBoard.movePlayer();
                      if (gameMode == 0){
                   	      _gameBoard.AIMove();
                      }
                     waiting = false;
                  } catch(Exception v) {
   
                  }
               }  
       };
       waitThread.start();
       } else { //FOR DEBUGGING PURPOSES
           waiting = false;
           _gameBoard.movePlayer();
       }//FOR DEBUGGING PURPOSES
   }
   public void centerFrame (JFrame frame)
    {
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
   
    public void init() {
        this.requestFocus();
        screenWidth = getSize().width;
        screenHeight = getSize().height;
        setBackground(Color.BLACK);
        addKeyListener( this );
        addMouseListener( this );
        
        _gameBoard.setEducationalMode(this.educationalMode);
        
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
        
        
        
        //define a new thread
        boardThread = new Thread(this);
        boardThread.start();
    }
   @Override
    public void paint( Graphics g ) {
        super.paint(g);
        _gameBoard.drawBoard(g,this);
      
    }  
   
   @Override
    public void update (Graphics g) 
    {
	    if(dbImage == null || screenWidth != this.getSize().width || screenHeight != this.getSize().height)
	    {
	    	screenWidth = this.getSize().width;
	    	screenHeight = this.getSize().height;
	    	dbImage = createImage (screenWidth, screenHeight);
	    	dbg = dbImage.getGraphics ();
	    }

	    dbg.setColor (getBackground());
	    dbg.fillRect (0,0, screenWidth, screenHeight);
	    
	    dbg.setColor (getForeground());
	    paint (dbg);
	    g.drawImage (dbImage, 0, 0, this);
    }
   
   
    public void start ()
    {
        boardThread.start();
    }

    public void stop() { }

    @Override
    public void run() {
        while (true){
            // repaint the applet
            repaint();
            try {
                Thread.sleep(30);
            } catch (InterruptedException ex) {
                //do nothing
            }
            

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }
    @Override
    public void mousePressed(MouseEvent e) {
             if (!waiting){
                this.movePlayer();
             }
             repaint();
             e.consume();
    }
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}

