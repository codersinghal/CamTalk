/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainclient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author harshit
 */
public class PeerHandler implements Runnable{
    Socket peer;
    ObjectOutputStream poos;
    ObjectInputStream pois;
    public static Socket framesock;
    public PeerHandler(Socket peer) {
        try {
            this.peer = peer;
            System.out.println("idhar bi hua");
            poos=new ObjectOutputStream(peer.getOutputStream());
            pois=new ObjectInputStream(peer.getInputStream());
           // Scanner sc=new Scanner(System.in);
            //System.out.println("Please enter frame port to connect\n");
            //int frameport=sc.nextInt();
            framesock=new Socket("localhost",1305);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        int flag=1;
              while(true)
              {
                  if(flag==1)
                  {  flag=0;
                      Platform.runLater(new Runnable() {
                      @Override
                      public void run() {
                          try {
                          
                          Parent root;
                          System.out.println("opening here also");
                          FXMLLoader loader=new FXMLLoader(getClass().getResource("/VideoCall.fxml"));
                          root = loader.load();
                          Stage stage = new Stage();
                          stage.setScene(new Scene(root, 710, 790));
                          stage.show();
                          stage.centerOnScreen();
                      } catch (IOException ex) {
                          System.exit(0);
                          Logger.getLogger(PeerHandler.class.getName()).log(Level.SEVERE, null, ex);
                      }
                  }
                      
                          
                  });
                      
              }
    }
    }
    public Socket getSocket()
    {
        return framesock;
    }
    
    
}
