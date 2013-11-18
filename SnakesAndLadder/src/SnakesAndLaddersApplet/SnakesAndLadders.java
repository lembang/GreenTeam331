/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SnakesAndLaddersApplet;
/**
 *
 * @author kenyonheffner & dylan
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
                waiting = true;
                _gameBoard.diceRoll();
                if (!fastmode){ //FOR DEBUGGING PURPOSES
                this.waitThread = new Thread() {
                        @Override
                        public void run() {
                           try {
                              Thread.sleep(1500);
                              _gameBoard.movePlayer();
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
         }
         repaint();
         e.consume();
         
      }   
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
                waiting = true;
                _gameBoard.diceRoll();
                if (!fastmode){ //FOR DEBUGGING PURPOSES
                this.waitThread = new Thread() {
                        @Override
                        public void run() {
                           try {
                              Thread.sleep(1500);
                              _gameBoard.movePlayer();
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

