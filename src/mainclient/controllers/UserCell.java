/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainclient.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import mainserver.model.User;

/**
 *
 * @author harshit
 */
public class UserCell extends ListCell<User>{
    @FXML
    Circle pp;
    @FXML
    Label name,work;
    @FXML AnchorPane anchor;
    @FXML Circle online_status;
    public UserCell() {
        loadFXML();
    }

    private void loadFXML() {
        try {
            System.out.println("loading");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/task_cell.fxml"));
            loader.setController(this);
           // loader.setRoot(this);
            loader.load();
            
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    protected void updateItem(User item, boolean empty) {
        super.updateItem(item, empty);
        if(empty||item==null)
        {  //setPrefHeight(45.0);
            setText(null);
            setGraphic(null);
        }
        else
        {
        System.out.println(item);
       // setPrefHeight(70.0);
       if(("online").equals(item.getStatus()))
           online_status.setVisible(true);
        pp.setFill(new ImagePattern(new Image("/profile.jpeg") {
        }));
        name.setText(item.getName());
        work.setText(item.getWorkplace());
        
            setText(null);
            setGraphic(anchor);
    }
    }
    
}
