/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainserver.start;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import mainserver.helper.ConnectSQL;
import mainserver.helper.SystemProp;

/**
 *
 * @author harshit
 */
public class Start {

    public static String user, password, host;
    public static Connection con;
    private static ServerSocketChannel serverSocketChannel;

    public static ServerSocketChannel getServerSocketChannel() {
        return serverSocketChannel;
    }

    public static void main(String args[]) {
        SystemProp obj = new SystemProp();
        user = obj.getMYSQLUser();
        password = obj.getMYSQLPass();
        host = obj.getMYSQLHost();
        con = ConnectSQL.connect();
        System.out.println("connected");
        //serverSocketChannel = ServerSocketChannel.open();
        //serverSocketChannel.socket().bind(new InetSocketAddress(9000));
        try {
            ServerSocket serversocket = new ServerSocket(3128);
            Socket client = null;
            while (true) {
                client = serversocket.accept();
                System.out.println("accepted");
                Thread t = new Thread(new ClientHandler(client));
                t.start();
            }

        } catch (IOException ex) {

        }

    }

}
