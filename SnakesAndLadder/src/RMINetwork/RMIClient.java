/* @author the
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *
 *DOCUMENTATION
 *https://github.com/lembang/GreenTeam331/wiki/RMINetwork/
 */

package RMINetwork;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
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
    /*
    HST ENCAPSULATION
    RMIAddress and IPAddress are protected, as a local variables
    the IP Address and the RMIAddress will be changed by the the get & set function.
    Why?
    It's because RMIAddress and Server IPAddress, has a specific pattern, is better to parse the value through the get set method to be able to check it first, before it's being used.
    */
    private String strRMIAddress, strIPAddress;
    /*
    HST STATICBINDING
    ServiceName variable are meant to be the same all the time, and unchangeable.
    When the server run, it will only have one specific RMI service name, to make sure the client will connected to the right RMI Server.
    */
    protected static String strServiceName = "/SnakeService";
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
    
    public ArrayList GLadder() throws RemoteException{
        return riface.generatedLadder();
    }
    
    public ArrayList GSnake() throws RemoteException{
        return riface.generatedSnake();
    }
    
    public ArrayList GStar() throws RemoteException{
        return riface.generatedStar();
    }
}

