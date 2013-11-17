/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package StandaloneServer;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import RMINetwork.*;
import java.rmi.RemoteException;
import javax.swing.JOptionPane;
/**
 *
 * @author the
 */
public class RMIServer implements Runnable{
    private String strRMIAddress;
    
    public RMIServer(){} //empty constructor
    
    public void initializeConnection(int port){
        try{
            Registry reg = LocateRegistry.createRegistry(port);
            RemoteInterface riface = new RemoteImplementation();
            reg.rebind(getStrRMIAddress(), riface);   
        }
        catch(RemoteException ex){
            JOptionPane.showMessageDialog(null,"Server Error :" + ex);
            System.exit(0);
        }
    }
    
    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the strRMIAddress
     */
    public String getStrRMIAddress() {
        return strRMIAddress;
    }

    /**
     * @param strRMIAddress the strRMIAddress to set
     */
    public void setStrRMIAddress(String strRMIAddress) {
        this.strRMIAddress = strRMIAddress;
    }
}
