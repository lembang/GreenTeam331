/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package RMINetwork;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import RMINetwork.RMIClient;

/**
 *
 * @author the
 * CSCI331 HST JUNIT
 * Here is a JUNIT test I wrote for the RMIClient class
 */
public class RMIClientTest {
    
    public RMIClientTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void testSetIPString(){
        RMIClient rClient = new RMIClient();
        rClient.setStrIPAddress("127.0.0.1");
        String texp = "127.0.0.1";
        String tact = rClient.getStrIPAddress();
        assertEquals("getIPAddress failed!!",texp, tact);
    }
    @Test
    public void testRMIAddress(){
        RMIClient rClient = new RMIClient();
        rClient.setStrRMIAddress("rmi://127.0.0.1/Test");
        String texp = "rmi://127.0.0.1/Test";
        String tact = rClient.getStrRMIAddress();
        assertEquals("getIPAddress failed!!",texp, tact);
    }
    
}
