/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainclient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import mainclient.controllers.ChatHandler;
import mainclient.controllers.LoginController;
import mainclient.controllers.SignUpController;
import mainserver.model.User;

/**
 *
 * @author harshit
 */
public class Start extends Application {

    public static Socket servercon;
    public static ObjectInputStream ois;
    public static ObjectOutputStream oos;
    public static User user;
    public static ServerSocketChannel serverClientSocketChannel;
    public static ServerSocket clientsocket;
    public static ServerSocket framesocket;
    public static ServerSocket chatsocket;
    public static int clientport;
    public static int frameport;
    public static int messageport;
    @Override
    public void start(Stage primaryStage) {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login_View.fxml"));
            System.out.println(System.getProperty("java.class.path"));
            
            root = loader.load();

            LoginController con = loader.getController();
            if (con == null) {
                primaryStage.setTitle("Skype");
            }

            primaryStage.setScene(new Scene(root, 700, 420));
            primaryStage.show();
            primaryStage.centerOnScreen();

        } catch (IOException ex) {
            ex.getMessage();
        }
        //serverClientSocketChannel = ServerSocketChannel.open();
        //serverClientSocketChannel.socket().bind(new InetSocketAddress(8000));
       // Scanner sc=new Scanner(System.in);
//        clientport=sc.nextInt();
//        frameport=sc.nextInt();
//        messageport=sc.nextInt();
        try {
            clientsocket=new ServerSocket(3600);
            framesocket =new ServerSocket(1305);
            chatsocket=new ServerSocket(1102);
            
            new Thread((Runnable) new PeerConnect()).start();
        } catch (IOException ex) {
           ex.printStackTrace();
        }
        new Thread(new ChatHandler()).start();
        
    }

    public static ServerSocketChannel getServerSocketChannel() {
        return serverClientSocketChannel;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.load("/home/harshit/opencv/build/lib/libopencv_java2413.so");
        launch(args);
    }

}
