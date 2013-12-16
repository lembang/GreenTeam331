/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package RMINetwork;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author the
 */
public interface RemoteInterface extends Remote {
    /*
    HST INTERFACE
    All the method on RMIServerUI, will be hidden under this Interface,
    The method only need to be called, from the client side, without knowing
    what is the exact process happen in the RMISERVERUI Class.
    */
    public int getPlayerNumber() throws RemoteException;
    public void connect(String username) throws RemoteException;
    public void disconnect(String username) throws RemoteException;
    public ArrayList generatedSnake()throws RemoteException;
    public ArrayList generatedLadder()throws RemoteException;
    public ArrayList generatedStar()throws RemoteException;
}
