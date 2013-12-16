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
    /*
    HST OVERRIDING
    This is all the stub required by the RMI system, and done through overriding.
    */
    @Override
    public void connect(String username) throws RemoteException
    {
        rServer.connect(username);
    }
    @Override
    public void disconnect(String username) throws RemoteException
    {
        rServer.disconnect(username);
    }
    @Override
    public int getPlayerNumber() throws RemoteException {
        return rServer.getPlayerNumber();
    }

    @Override
    public ArrayList generatedSnake() throws RemoteException {
        return rServer.generatedSnake();
    }

    @Override
    public ArrayList generatedLadder() throws RemoteException {
        return rServer.generatedLadder();
    }

    @Override
    public ArrayList generatedStar() throws RemoteException {
        return rServer.generatedStar();
    }
}
