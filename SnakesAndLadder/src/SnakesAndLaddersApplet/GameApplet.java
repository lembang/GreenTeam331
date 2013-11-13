package SnakesAndLaddersApplet;

import java.awt.Dimension;
import javax.swing.JApplet;
import javax.swing.JFrame;

public class GameApplet extends JApplet {
    public static void main(String[] args) {
        StartOptionsMenu _menu = new StartOptionsMenu();
        _menu.setVisible(true);
        
        while (!_menu.isFinished()) {  
            //wait until the menu is finished
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                //do nothing
            }
        }
        //fetch parameters from options menu
        int gameMode = _menu.getGameMode();
        int snakes = _menu.getSnakes();
        int ladders = _menu.getLadders();
        int stars = _menu.getStars();
        boolean educationalMode = _menu.isEducationalMode();
        
        //load the game
        JFrame frame = new JFrame("Snakes and Ladders!");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setPreferredSize(new Dimension(900,638));
	SnakesAndLadders game = new SnakesAndLadders(gameMode, educationalMode, snakes, ladders, stars);
	frame.getContentPane().add(game);      
        frame.pack();
        game.centerFrame(frame);
        game.init();
    }
}