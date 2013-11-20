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
    private String strRMIAddress, strIPAddress;
    private int playnumb;
    private final String strServiceName = "/SnakeLadder";
    private RemoteInterface riface;
    public RMIClient(){} //empty constructor
    
    public void createConnection(int port){
        setStrIPAddress("127.0.0.1");
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
    
    public int getNumber()throws RemoteException{
        playnumb = riface.getPlayerNumber();
        return playnumb;
    }

    public void initialState(){
    
    }
    
    /**
     * @return the setRMIAddress
     */
    public String getStrRMIAddress() {
        return strRMIAddress;
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

