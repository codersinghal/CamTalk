/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainclient.controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import mainclient.Start;
import mainserver.clientqueries.Response;
import mainserver.model.Message;

/**
 *
 * @author harshit
 */
public class ChatHandler implements Runnable {

    Socket chatsock;
    ObjectOutputStream coos;
    ObjectInputStream cois;
    Message obj;

    @Override
    public void run() {
        while (true) {
            try {
                chatsock = Start.chatsocket.accept();
                coos = new ObjectOutputStream(chatsock.getOutputStream());
                cois = new ObjectInputStream(chatsock.getInputStream());
                Response res = (Response) cois.readObject();
                obj = (Message) res.getObject();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            String cwd = System.getProperty("user.dir");
            String path = cwd + "/chatdb/" + obj.getReceiveruid() + obj.getSenderuid();
            File f = new File(path);
            try {
                f.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            try {
                BufferedWriter fout = new BufferedWriter(new FileWriter(f, true));
                String message = obj.getMsg();
                System.out.println(message);
//                String[] mesarr=message.split("\n");
//                for(String s:mesarr)
//                {
//                    fout.write(s);
//                }
                fout.write(message + "\n");
                fout.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
