/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package RMINetwork;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.JOptionPane;


/**
 *
 * @author the
 */
public class RMIClient {
    /**
     * Variables to be used as a global variable
     */
    private int playnumb;
    private String strRMIAddress, strIPAddress;
    private final String strServiceName = "/SnakeService";
    private RemoteInterface riface;
    public RMIClient(){} //empty constructor
    
    public void createConnection(int port){
        setStrRMIAddress("rmi://"+getStrIPAddress()+":"+port+strServiceName);
        try{    
            Registry Reg = LocateRegistry.getRegistry(port);
            riface = (RemoteInterface)Reg.lookup(getStrRMIAddress());
        }
        catch(NotBoundException | RemoteException ex){
            JOptionPane.showMessageDialog(null,"Connection Error :" + ex);
            System.exit(0);
        }
        System.out.println("Client Connected");
    }

    /**
     * @return the setRMIAddress
     */
    public String getStrRMIAddress() {
        return strRMIAddress;
    }
    
    public void connect(String username){
    try {
        riface.connect(username);
        } 
    catch (RemoteException ex) {
        JOptionPane.showMessageDialog(null,"Username Error :" + ex);
        }
    }
    public int getNumber()throws RemoteException{
        playnumb = riface.getPlayerNumber();
        return playnumb;
    }
    /**
     * 
     * @param strRMIAddress
     */
    public void setStrRMIAddress(String strRMIAddress) {
        this.strRMIAddress = strRMIAddress;
    }

    /**
     * @return the strIPAddress
     */
    public String getStrIPAddress() {
        return strIPAddress;
    }

    /**
     * @param strIPAddress the strIPAddress to set
     */
    public void setStrIPAddress(String strIPAddress) {
        this.strIPAddress = strIPAddress;
    }
    
    
    
}

