/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package StandaloneServer;

import RMINetwork.RemoteImplementation;
import RMINetwork.RemoteInterface;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.JOptionPane;

/**
 *
 * @author the
 */
public class RMIServerUI extends javax.swing.JFrame implements Runnable  {

    /**
     * Creates new form RMIServerUI
     */
    /**
     * Variables Here
     */
    Registry reg;
    private static HashMap<String, Socket> connectedUser = new HashMap<String, Socket>();	
    private static Socket ClientSocket = null;
    private static ServerSocket serverSocket;
    private static String username = null;
    private static PrintWriter output;
    private static Thread thread = new Thread();;

    private static final String PUBLICMESSAGE = "PUBLICMESSAGE";
    private static final String ONLINE = "ONLINE";
    private static final String OFFLINE = "OFFLINE";
    public RMIServerUI() {
        initComponents();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        initializeConnection("127.0.0.1", 3103);
        //processConnection(3183);
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
               processConnection(3183);           
            }
        });
        thread.start();
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(RMIServerUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RMIServerUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RMIServerUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RMIServerUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RMIServerUI().setVisible(true);
            }
        });
    }
    
    public void initializeConnection(String ipAddress, int port){
        try{
            ipAddress = "rmi://"+ipAddress+"/SnakeLadder:"+port;
            reg = LocateRegistry.createRegistry(port);
            RemoteInterface riface = new RemoteImplementation();
            reg.rebind(ipAddress, riface);   
        }
        catch(RemoteException ex){
            JOptionPane.showMessageDialog(null,"Server Error :" + ex);
            System.exit(0);
        }
    }
    
    public void deinitializeConnection(){
        try {
            UnicastRemoteObject.unexportObject(reg, true);
        } catch (NoSuchObjectException ex) {
            JOptionPane.showMessageDialog(null,"Shutdown Service fail :" + ex);
        }
        
    }
    
    private void processConnection(int port){
            try
            {
                serverSocket = new ServerSocket(port);
                System.out.println("Server is running on port " + port + " .... ");
            }
            catch (IOException ex)
            {
                JOptionPane.showMessageDialog(null, "Could not listen port " + port, "ERROR", JOptionPane.ERROR_MESSAGE);
                System.exit(-1);
            }

            try
            {
                while (true)
                {
                    addClient(serverSocket.accept());
                    String username = getUsername();
                    sendPublicMessage(PUBLICMESSAGE, "SERVER", "[" + username + "] is now online");
                }
            }
            catch (IOException ex)
            {
                    JOptionPane.showMessageDialog(null, "Could not accept connection.", "ERROR", JOptionPane.ERROR_MESSAGE);
                    System.exit(-1);
            }
    }
    public ArrayList getClientList()
    {
        ArrayList myUser = new ArrayList();

        Iterator i = connectedUser.keySet().iterator();
        String user = null;

        while(i.hasNext())
        {
                user = i.next().toString();
                myUser.add(user);
        }

        return myUser;
    }
    
    public void addClient(Socket clientSocket) throws RemoteException
    {
        connectedUser.put(getUsername(), clientSocket);
        sendPublicMessage(ONLINE, getUsername(),"");
    }

    public void sendPublicMessage(String keyword, String username, String message) throws RemoteException
    {
        Iterator i = connectedUser.keySet().iterator();
        String user = null;
        while(i.hasNext())
        {
            try
            {
                user = i.next().toString();
                ClientSocket =  connectedUser.get(user);
                output = new PrintWriter(ClientSocket.getOutputStream(), true);
                output.println(keyword + "***" + username + "***" + message);
                output.flush();
            }
            catch(IOException ioe)
            {
                connectedUser.remove(user);
                sendPublicMessage(OFFLINE, user, user + " has been left the conversation");
            }
        }
    }

    public void disconnect(String username) throws RemoteException
    {
            connectedUser.remove(username);
            sendPublicMessage(OFFLINE, username, username + " has been left the conversation");
            sendPublicMessage(PUBLICMESSAGE, "SERVER", username + " has been left the conversation");
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    // End of variables declaration//GEN-END:variables

    
     public void connect(String username)
    {
            RMIServerUI.username = username;
    }

    public String getUsername()
    {
            return username;
    }

    @Override
    public void run() {
        
    }
}
