/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainclient;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author harshit
 */
public class PeerConnect implements Runnable {

    @Override
    public void run() {
               while(true)
               {
                   try {
                       Socket peer=Start.clientsocket.accept();
                       Thread t=new Thread(new PeerHandler(peer));
                       t.start();
                   } catch (IOException ex) {
                      System.out.println(ex.getMessage());
                   }
               }
    }
    
    
}
