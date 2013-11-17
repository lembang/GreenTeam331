package GameLogic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import SnakesAndLaddersApplet.SnakesAndLadders;

/**
 *
 * @author Dylan
 */
public class Winner extends JPanel {
    private String winnerName;
    private int startX;
    private int startY;
    private int width;
    private int height;
    private ImageIcon dance;
    
    public void setWinnerName(String name){
        this.winnerName = name;
    }
    
    public Winner(){
        this.winnerName = "Nobody Yet";
        this.startX = 100;
        this.startY = 100;
        this.width = 400;
        this.height = 400;
        try {
          this.dance = new ImageIcon(this.getClass().getResource
               ("/resources/imgs/dance.gif"));   
          this.dance.getImage().flush();
        } catch (Exception E) {
            //do nothing
        }
    }
    
        public Winner(String winnerName){
        this.winnerName = winnerName;
        this.startX = 100;
        this.startY = 100;
        this.width = 400;
        this.height = 400;
        try {
          this.dance = new ImageIcon(this.getClass().getResource
               ("/resources/imgs/dance.gif"));   
          this.dance.getImage().flush();
        } catch (Exception E) {
            //do nothing
        }
    }
    
    public void draw(Graphics g, SnakesAndLadders parent) {
        super.paintComponent(g);
        
        Color BG = new Color(200,200,200);
        g.setColor(BG);
        g.fillRect(startX,startY,width,height);
        g.setColor(Color.BLACK);
        Font font = new Font("Arial", Font.PLAIN, 20);
        g.setFont(font);
        g.drawString("WINNER: " + winnerName, startX + 100, startY + 385);
        g.setFont(font);
        
        dance.paintIcon(parent, g, startX+10, startY);
        
    }
}


