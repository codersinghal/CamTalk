/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainclient.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.application.ConditionalFeature.FXML;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import mainclient.Start;
import mainserver.clientqueries.LoginQuery;
import mainserver.clientqueries.Response;
import mainserver.model.User;
import mainserver.query_handler.FileReciever;

/**
 *
 * @author harshit
 */
public class LoginController {

    @FXML
    private JFXTextField email;
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXButton loginbtn;
    @FXML
    private Text newacc_btn;

    public void loginclicked(ActionEvent avt) {
        try {

            LoginQuery lq = new LoginQuery(email.getText(), password.getText());

            if (Start.servercon == null) {
                Start.servercon = new Socket("localhost", 3128);
                Start.oos = new ObjectOutputStream(Start.servercon.getOutputStream());
                Start.ois = new ObjectInputStream(Start.servercon.getInputStream());
            }
                

                try {
                    Start.oos.writeObject(lq);
                    System.out.println("sent");
                    Start.oos.flush();
                    Response rs = (Response) Start.ois.readObject();
                    if (rs.getCode().equals("Success")) {
                        User curruser = (User) rs.getObject();
                        Start.user = curruser;
                        System.out.println("received");
                        String cwd = System.getProperty("user.dir");
                        String folder = cwd + "/profilephotos/";
                        System.out.println("Recieving PP");
                      //  FileReciever fileReciever = new FileReciever();
                        //fileReciever.readFile(fileReciever.createSocketChannel(Start.getServerSocketChannel()), Start.user.getUserid(), folder);
                        System.out.println("File Recieved");
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Stage primaryStage = (Stage) loginbtn.getScene().getWindow();
                                Parent root = null;
                                try {

                                    root = FXMLLoader.load(getClass().getResource("/Dashboard.fxml"));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                // primaryStage.initStyle(StageStyle.UNDECORATED);
                                primaryStage.setScene(new Scene(root, 1080, 825));
                                primaryStage.show();
                                primaryStage.centerOnScreen();

                            }
                        });

                    } else {
                        System.out.println("failed");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void newacc_clicked() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SignUp_View.fxml"));
            System.out.println(getClass().getResource("/SignUp_View.fxml"));
            System.out.println(loader);
            Parent root = loader.load();
            SignUpController con = loader.getController();
            Stage primaryStage = (Stage) loginbtn.getScene().getWindow();
            primaryStage.setScene(new Scene(root, 800, 570));

        } catch (IOException ex) {

        }

    }

}
