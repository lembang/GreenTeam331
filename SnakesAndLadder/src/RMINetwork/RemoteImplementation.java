/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package RMINetwork;

import java.rmi.server.UnicastRemoteObject;
import StandaloneServer.*;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author the
 */
public class RemoteImplementation extends UnicastRemoteObject implements RemoteInterface {
    private final RMIServerUI rServer = new RMIServerUI();
    public RemoteImplementation()throws RemoteException{}

    @Override
    public void sendPublicMessage(String keyword, String username, String message) throws RemoteException {
        rServer.sendPublicMessage(keyword, username, message);
    }

    @Override
    public ArrayList getClientList() throws RemoteException {
        return rServer.getClientList();
    }

    @Override
    public void connect(String username) throws RemoteException {
        rServer.connect(username);
    }

    @Override
    public void disconnect(String username) throws RemoteException {
        rServer.disconnect(username);
    }
    
}
