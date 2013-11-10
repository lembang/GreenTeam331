/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package snakesandladders;
/**
 *
 * @author kenyonheffner
 * edited by dylan
 */
import GameLogic.*;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;


public class SnakesAndLadders extends Applet
   implements Runnable, KeyListener {
     
   private Graphics dbg;
   private Image dbImage;
   private int screenHeight;
   private int screenWidth;
   private Thread boardThread;
   Board _gameBoard = new Board();
   private static boolean start = false;
   
  private static boolean singePlayerMode;
   
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
             _gameBoard.movePlayer();
         }          
         start = !start;
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
   @Override
    public void init() {
        screenWidth = getSize().width;
        screenHeight = getSize().height;
        Color newColor = Color.decode("0x000000");
        setBackground(newColor);
        addKeyListener( this );
        

        
        boardThread = new Thread (this);
        boolean ok = true;
        
        while (ok){ //add max players
            Player _newPlayer = new Player();
            ok = _gameBoard.addPlayer(_newPlayer);
        }
        
        for (int i = 0; i < 7; i++){
            boolean result = _gameBoard.addSnake();
            while (!result)
                result = _gameBoard.addSnake();
        }
        for (int i = 0; i < 7; i++){
            boolean result = _gameBoard.addLadder();
            while (!result)
                result = _gameBoard.addLadder();
        }
        for (int i = 0; i < 5; i++){
            boolean result = _gameBoard.addStar();
            while (!result){
                result = _gameBoard.addStar();
            }
        }
    }
   @Override
    public void paint( Graphics g ) {

        //g.drawImage(myBackgroundImage, 0, 0, this);      
        //dRoll.paintComponent(g, this);
        //myPiece.paint(g,this);
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
   @Override
    public void start ()
    {
        boardThread.start ();
    }

   @Override
    public void stop() { }

    @Override
    public void run() {
        // lower ThreadPriority
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);

        // run a long while (true) this means in our case "always"
        while (true)
        {
            // repaint the applet
            repaint();
            
            // set ThreadPriority to maximum value
            Thread.currentThread().setPriority(Thread.MAX_PRIORITY);

        }     
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("Snakes and Ladders!");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setPreferredSize(new Dimension(900,638));
        StartMenu _menu = new StartMenu();
        _menu.setVisible(true);
        boolean goNext = false;
        while (goNext == false) { 
            goNext = _menu.finished;
            System.out.println(goNext);
        }
        singePlayerMode = _menu.isSPMode();
	SnakesAndLadders game = new SnakesAndLadders() {};
	frame.getContentPane().add(game);      
        frame.pack();
        game.centerFrame(frame);
        game.init();
              	
    }
    
}

