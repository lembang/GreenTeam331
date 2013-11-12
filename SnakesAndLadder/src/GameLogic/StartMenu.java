package GameLogic;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 *
 * @author Dylan
 */

public class StartMenu extends JFrame {
    private ButtonGroup group;
    private JRadioButton singlePlayer, multiPlayer;
    private JButton okButton;
    
    public boolean singlePlayerMode;
    public boolean finished = false;
    
    public StartMenu() {
        this.singlePlayerMode = true;
        JPanel panel = new JPanel();
        panel.setLayout(null);
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension dim = tk.getScreenSize();
        
        okButton = new JButton("OK");
        okButton.setMnemonic(KeyEvent.VK_ENTER);    
        singlePlayer = new JRadioButton("Single-Player Game");
        multiPlayer = new JRadioButton("Multi-Player Game");
        group = new ButtonGroup();
        
        this.setLocation(((dim.width/2)-(350/2)), (dim.height/2)-(200/2));
        this.setTitle("Snakes and Ladders Start Options");
        this.setSize(350, 200);
        
        okButton.setLocation(((this.getSize().width)/2-45), (this.getSize().height)-70);
        okButton.setSize(90, 30);
        singlePlayer.setLocation(20, 20);
        singlePlayer.setSize((this.getSize().width)-20,30);
        singlePlayer.setSelected(true);
        multiPlayer.setLocation(20, 60);
        multiPlayer.setSize((this.getSize().width)-20,30);
        
        group.add(singlePlayer);
        group.add(multiPlayer);
        
        panel.add(okButton);
        panel.add(singlePlayer);
        panel.add(multiPlayer);
        this.add(panel);
        
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event){
                if (multiPlayer.isSelected()) {
                    singlePlayerMode = false;
                }
                finished = true;
                setVisible(false);
            }
        });
        
        this.setResizable(false);
        this.setVisible(false);
        this.repaint();
    }

    public boolean isSPMode() {
        return this.singlePlayerMode;
    }

    public boolean isFinished() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
