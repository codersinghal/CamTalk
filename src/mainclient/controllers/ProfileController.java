/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainclient.controllers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import mainclient.Start;
import mainserver.clientqueries.AddFriendQuery;
import mainserver.clientqueries.GetStatusQuery;
import mainserver.clientqueries.Response;
import mainserver.model.User;

/**
 *
 * @author harshit
 */
public class ProfileController {

    @FXML
    private Label name_lab, email_lab, status_lab;
    @FXML
    private Button friend_btn;
    @FXML
    private Circle profilepic;
    @FXML
    private ImageView videocall_btn, chat_btn;
    private String status, name, email;
    public static Socket videosocket, framesocket;
    User thisuser;

    @FXML
    public void initialize() {
        thisuser = DashBoard_Controller.profileuser;
        name = thisuser.getName();
        email = thisuser.getEmail();
        profilepic.setFill(new ImagePattern(new Image("/profile.jpeg") {
        }));
        name_lab.setText(name);
        email_lab.setText(email);
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.5);
        videocall_btn.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> {

            videocall_btn.setEffect(colorAdjust);

        });
        videocall_btn.addEventFilter(MouseEvent.MOUSE_EXITED, e -> {
            videocall_btn.setEffect(null);
        });
        GetStatusQuery obj = new GetStatusQuery(thisuser.getUserid());
        try {
            Start.oos.writeObject(obj);
            Response rs = (Response) Start.ois.readObject();
            status = rs.getObject().toString();
            System.out.println(status);
            status_lab.setText(status);
        } catch (Exception ex) {

        }

    }

    @FXML
    public void videocall_clicked() {
        try { 
            System.out.println("Enter clientport to connect\n");
            Scanner sc=new Scanner(System.in);
            
            videosocket = new Socket("localhost", 3601);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("connect toh hua");
                        ObjectOutputStream oos = new ObjectOutputStream(videosocket.getOutputStream());
                        System.out.println("dikkat");
                        ObjectInputStream ois = new ObjectInputStream(videosocket.getInputStream());
                        framesocket = Start.framesocket.accept();
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Stage primaryStage = (Stage) videocall_btn.getScene().getWindow();
                                Parent root = null;
                                try {
                                    System.out.println("opening");
                                    root = FXMLLoader.load(getClass().getResource("/VideoCall.fxml"));
                                } catch (IOException e) {
                                    e.printStackTrace();

                                }
                                // primaryStage.initStyle(StageStyle.UNDECORATED);
                                primaryStage.setScene(new Scene(root, 710, 790));
                                primaryStage.show();
                                primaryStage.centerOnScreen();
                            }
                        });

                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }
                }

            }).start();
        } catch (IOException ex) {

        }

    }

    @FXML
    public void chat_clicked() {

    }

    @FXML
    public void addfriend(ActionEvent avt) {
        AddFriendQuery obj = new AddFriendQuery(Start.user.getUserid(), thisuser.getUserid());
        try {
            Start.oos.writeObject(obj);
            Response rs = (Response) Start.ois.readObject();
            if (rs.getCode().equals("Success")) {
                friend_btn.setText("Friends");
                friend_btn.setDisable(true);
            }
        } catch (Exception ex) {
        }
    }
}
