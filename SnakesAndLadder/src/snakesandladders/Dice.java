/**
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package snakesandladders;

import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author kenyonheffner
 */
public class Dice extends JLabel{
    
    private int dice1;
    private int dice2; 
    private ImageIcon ic;   
    private boolean roll = false;
    //private Graphic2D dg;
    
    public Dice(){
      this.dice1 = 0;
      this.dice2 = 0;      
      this.ic = null;
    }
    public void oneDice(){ //not implemented yet
      
       this.dice1 = (int )(Math.random() * 6 + 1);
       this.dice2 = 0;
       //JOptionPane.showMessageDialog(null,dice1);
       
    }
    public void twoDice(){
            
       this.dice1 = (int )(Math.random() * 6 + 1);
       this.dice2 = (int )(Math.random() * 6 + 1); 
       this.ic = new ImageIcon(this.getClass().getResource
               ("/resources/imgs/dice/"+dice1+"_"+dice2+".gif"));   
       this. ic.getImage().flush();      
       this.roll = true;
     
    }
    public void paintComponent(Graphics g, SnakesAndLadders parent){
        super.paintComponent(g);
        if(this.roll){
            ic.paintIcon(parent, g, 600, 400);
            
        }  
    }    
    public void resetRoll(){
        this.roll = false;
    }
    public void setRoll(){
        this.roll = true;
    }
    public Boolean getStatus(){
        return this.roll;
    }
    public int getDiceTotal(){
        return this.dice1 + this.dice2;
    }
    
}

