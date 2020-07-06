/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainclient.controllers;

//import java.awt.Image;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.application.ConditionalFeature.FXML;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javax.imageio.ImageIO;
import mainclient.Start;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextBuilder;
import javafx.stage.Stage;
import mainserver.clientqueries.FriendSuggQuery;
import mainserver.clientqueries.GetStatusQuery;
import mainserver.clientqueries.LogoutQuery;
import mainserver.clientqueries.MessageSendQuery;
import mainserver.clientqueries.OnlineUserQuery;
import mainserver.clientqueries.Response;
import mainserver.clientqueries.SearchUser;
import mainserver.model.Message;
import mainserver.model.User;
import mainserver.query_handler.OnlineList;

/**
 *
 * @author harshit
 */
public class DashBoard_Controller {
    
    static User profileuser, frienduser;
    ArrayList<User> users, friends;
    String path;
    @FXML
    ImageView profileimg;
    @FXML
    Circle cir2;
    @FXML
    JFXTextField searchbox;
    @FXML
    ImageView searchbtn;
    @FXML
    ListView<User> searchlist;
    @FXML
    JFXListView<User> online;
    @FXML
    ImageView refresh;
    @FXML
    ImageView logout_btn;
    @FXML
    Button connect_btn;
    @FXML
    VBox vbox;
    @FXML
    ImageView send_btn;
    @FXML
    TextField chatfield;
    @FXML
    ScrollPane scp;
    @FXML
    Label name_label;
    @FXML
    JFXListView<User> friendsuglist;

    @FXML
    public void initialize() throws IOException {
        String cwd = System.getProperty("user.dir");
        String folder = cwd + "/profilephotos/";
        cir2.setFill(new ImagePattern(new Image("/profile.jpeg") {
        }));
        name_label.setText(Start.user.getName());
        addEffects();
        loadfriendsug();
        
    }
    
    public void search_clicked() {
        String searchedname = searchbox.getText();
        SearchUser obj = new SearchUser(searchedname);
        try {
            Start.oos.writeObject(obj);
            Start.oos.flush();
            Response res = (Response) Start.ois.readObject();
            if (res.getCode().equals("Success")) {
                users = (ArrayList<User>) (res.getObject());
                System.out.println(users);
                if (users != null) {
                    searchlist.setVisible(true);
                }
                //searchlist.prefHeight(20*users.size());
                searchlist.getItems().clear();
//                for (User it : users) {
//                    searchlist.getItems().add(it.getName());
//                }
                ObservableList<User> userObservableList = FXCollections.observableArrayList();
                userObservableList.addAll(users);
                System.out.println(userObservableList);
                searchlist.setItems(userObservableList);
                searchlist.setCellFactory(new UserCellFactory());
                System.out.println("fucked");
                
            } else {
                System.out.println("failed");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
    
    public void selectuser() {
        System.out.println(searchlist.getSelectionModel().getSelectedIndex());
        int index = searchlist.getSelectionModel().getSelectedIndex();
        profileuser = users.get(index);
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Profilepage.fxml"));
            root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Profile");
            stage.setScene(new Scene(root, 600, 600));
            stage.show();
            stage.centerOnScreen();
            // Hide this current window (if this is what you want)
            //((Node)(event.getSource())).getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public void refresh_clicked() {
        OnlineUserQuery obj = new OnlineUserQuery(Start.user.getUserid());
        try {
            Start.oos.writeObject(obj);
            Start.oos.flush();
            Response res = (Response) Start.ois.readObject();
            if (res.getCode().equals("Success")) {
                friends = (ArrayList<User>) res.getObject();
                if (friends != null) {
                    online.setVisible(true);
                }
                System.out.println(friends);
                online.getItems().clear();
                for (User it : friends) {
                    System.out.println(it.getName() + " " + it.getStatus());
                    GetStatusQuery gsq = new GetStatusQuery(it.getUserid());
                    Start.oos.writeObject(gsq);
                    Start.oos.flush();
                    Response rs = (Response) Start.ois.readObject();
                    it.setStatus(rs.getObject().toString());
                    // online.getItems().add(TextBuilder.create().text("BOLD").style("-fx-font-weight:bold;").build());
                    //online.getItems().add(it.getName() + "         " + it.getStatus());
                    System.out.println(it.getName() + " " + it.getStatus());
                }
                ObservableList<User> userObservableList = FXCollections.observableArrayList();
                userObservableList.addAll(friends);
                online.setItems(userObservableList);
                online.setCellFactory(new UserCellFactory());
            }
        } catch (Exception ex) {
            
        }
    }
    
    public void logout_clicked() {
        LogoutQuery obj = new LogoutQuery(Start.user.getUserid());
        try {
            Start.oos.writeObject(obj);
            Start.oos.flush();
            Response res = (Response) Start.ois.readObject();
            if (res.getCode().equals("Success")) {
                Stage primaryStage = (Stage) logout_btn.getScene().getWindow();
                Parent root = null;
                try {
                    
                    root = FXMLLoader.load(getClass().getResource("/Login_View.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                
                primaryStage.setScene(new Scene(root, 700, 420));
                primaryStage.show();
            } else {
                System.out.println("failed logout");
            }
        } catch (Exception ex) {
            
        }
    }
    
    public void chat_friend() {
        scp.setContent(vbox);
        List<Separator> sep = new ArrayList<>();
        System.out.println(online.getSelectionModel().getSelectedIndex());
        int index = online.getSelectionModel().getSelectedIndex();
        frienduser = friends.get(index);
        vbox.setVisible(true);
        send_btn.setVisible(true);
        chatfield.setVisible(true);
        List<Label> chatlist = new ArrayList<>();
        vbox.getStyleClass().add("vbox");
        String cwd = System.getProperty("user.dir");
        path = cwd + "/chatdb/" + Start.user.getUserid() + frienduser.getUserid();
        System.out.println(path);
        File f = new File(path);
        try {
            f.createNewFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Platform.runLater(() -> vbox.getChildren().clear());
                    Platform.runLater(() -> vbox.getChildren().clear());
                    chatlist.clear();
                    sep.clear();
                    try {
                        BufferedReader reader = new BufferedReader(new FileReader(path));
                        try {
                            String mess;
                            int i = 0;
                            while ((mess = reader.readLine()) != null) {
                                System.out.println(mess);
                                String[] s = mess.split(" ", 2);
                                if (Start.user.getName().equals(s[0])) {
                                    chatlist.add(new Label(s[1]));
                                    chatlist.get(i).getStyleClass().add("mess");
                                    chatlist.get(i).setAlignment(Pos.CENTER_RIGHT);
                                } else {
                                    chatlist.add(new Label(s[1]));
                                    chatlist.get(i).getStyleClass().add("mess");
                                    chatlist.get(i).setAlignment(Pos.CENTER_LEFT);
                                }
                                sep.add(new Separator());
                                sep.get(i).getStyleClass().add("sepa");
                                final Label l = chatlist.get(i);
                                final Separator sp = sep.get(i);
                                Platform.runLater(() -> vbox.getChildren().add(l));
                                Platform.runLater(() -> vbox.getChildren().add(sp));
                                
                                i++;
                            }
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException ex) {
                                
                            }
                        } catch (IOException ex) {
                            Logger.getLogger(DashBoard_Controller.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    }
                    
                }
            }
        }).start();
//        for (int i = 0; i < 16; i++) {
//            chatlist.add(new Label("hello"));
//            chatlist.get(i).getStyleClass().add("mess");
//            if (i % 2 == 0) {
//                chatlist.get(i).setAlignment(Pos.CENTER_LEFT);
//                System.out.println(i);
//            } else {
//                chatlist.get(i).setAlignment(Pos.CENTER_RIGHT);
//            }
//            vbox.getChildren().add(chatlist.get(i));
//            sep.add(new Separator());
//            sep.get(i).getStyleClass().add("sepa");
//            vbox.getChildren().add(sep.get(i));
//        }

    }
    
    public void send() {
        System.out.println("send clicked");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path, true));
            String msgsend = Start.user.getName().concat(" " + chatfield.getText());
            Message obj = new Message(Start.user.getName(), frienduser.getName(), msgsend, Start.user.getUserid(), frienduser.getUserid());
            MessageSendQuery msq = new MessageSendQuery(obj);
            writer.write(msgsend + "\n");
            writer.close();
            Start.oos.writeObject(msq);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }

    public void addEffects() {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.5);
        profileimg.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> {
            
            profileimg.setEffect(colorAdjust);
            
        });
        profileimg.addEventFilter(MouseEvent.MOUSE_EXITED, e -> {
            profileimg.setEffect(null);
        });
        searchbtn.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> {
            
            searchbtn.setEffect(colorAdjust);
            
        });
        searchbtn.addEventFilter(MouseEvent.MOUSE_EXITED, e -> {
            searchbtn.setEffect(null);
        });
        refresh.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> {
            
            refresh.setEffect(colorAdjust);
            
        });
        refresh.addEventFilter(MouseEvent.MOUSE_EXITED, e -> {
            refresh.setEffect(null);
        });
        logout_btn.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> {
            
            logout_btn.setEffect(colorAdjust);
            
        });
        logout_btn.addEventFilter(MouseEvent.MOUSE_EXITED, e -> {
            logout_btn.setEffect(null);
        });
        send_btn.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> {
            
            send_btn.setEffect(colorAdjust);
            
        });
        send_btn.addEventFilter(MouseEvent.MOUSE_EXITED, e -> {
            send_btn.setEffect(null);
        });
    }

    void loadfriendsug() {
        try {
            FriendSuggQuery fsq = new FriendSuggQuery(Start.user.getUserid());
            Start.oos.writeObject(fsq);
            Start.oos.flush();
            Response res = (Response) Start.ois.readObject();
            if (res.getCode().equals("Success")) {
                ArrayList<User> friendsug = (ArrayList<User>) res.getObject();
                ObservableList<User> userObservableList = FXCollections.observableArrayList();
                userObservableList.addAll(friendsug);
                this.friendsuglist.setItems(userObservableList);
                this.friendsuglist.setCellFactory(new UserCellFactory());
            } else {
                System.out.println("failed here");
            }
        } catch (Exception ex) {
            
        }
    }
}
