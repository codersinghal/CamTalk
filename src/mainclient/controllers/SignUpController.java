/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainclient.controllers;

import static javafx.application.ConditionalFeature.FXML;
import javafx.fxml.FXML;
import com.jfoenix.controls.*;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import mainclient.Start;
import mainserver.model.User;
import mainserver.clientqueries.Response;
import mainserver.clientqueries.SignUpQuery;
import mainserver.query_handler.FileSender;
/**
 *
 * @author harshit
 */
public class SignUpController {
    @FXML private JFXTextField name;
    @FXML private JFXTextField email;
    @FXML private JFXTextField phone;
    @FXML private JFXPasswordField password;
    @FXML private JFXTextField workplace;
    @FXML private JFXButton signup_btn;
    @FXML private TextField profilepic;
    String ppPath;
    @FXML private Button browse;
    public void signup(ActionEvent avt)
    {  
       User user=new User();
       user.setName(name.getText());
       user.setEmail(email.getText());
       user.setPhone(phone.getText());
       user.setWorkplace(workplace.getText());
       user.setUserid(UUID.randomUUID().toString());
       SignUpQuery sq=new SignUpQuery(password.getText(),user);
       if(Start.servercon==null)
       {   try {
           Start.servercon=new Socket("localhost",3128);
           
               Start.oos=new ObjectOutputStream(Start.servercon.getOutputStream());
               Start.ois=new ObjectInputStream(Start.servercon.getInputStream());
           } catch (IOException ex) {
           }
       
       }
           try {
               
               Start.oos.writeObject(sq);
               Start.oos.flush();
               FileSender fsend=new FileSender();
               fsend.sendFile(fsend.createSocketChannel("localhost"),ppPath);
               try{
                   //Start.ois.readObject();
                    Response rs=(Response) Start.ois.readObject();
            System.out.println(rs.getCode());
            Stage primaryStage=(Stage) signup_btn.getScene().getWindow();
             Parent root = null;
                                try {

                                    root = FXMLLoader.load(getClass().getResource("/Login_View.fxml"));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

            primaryStage.setScene(new Scene(root, 700, 420));
            primaryStage.show();
               }
               catch(Exception e)
               {
                   e.printStackTrace();
               }
           } catch (IOException ex) {
           
           }
       
    }
    public void browse_clicked(ActionEvent avt)
    {
         Stage stage = (Stage) browse.getScene().getWindow();
        FileChooser fileChooser=new FileChooser();
        File file=fileChooser.showOpenDialog(stage);
        if(file!=null)
        {
            ppPath=file.getAbsolutePath();
            
        }
        profilepic.setText(ppPath);
        
    }
    
    
}
