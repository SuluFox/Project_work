package com.example.projectwork;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SplashscreenController implements Initializable {

    public ImageView spingif;
    @FXML
    private AnchorPane pane;
    private BorderPane borderPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        splash();
    }

    public void splash(){
        new Thread(){
            public void run(){
                try {
                    Thread.sleep(4000);

                }catch (Exception e){
                    e.printStackTrace();
                    e.getCause();
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            borderPane = FXMLLoader.load(getClass().getResource("login.fxml"));
                            Stage stage = new Stage();
                            Scene scene = new Scene(borderPane);
                            stage.setTitle("Login");
                            stage.initStyle(StageStyle.UNDECORATED);
                            stage.setScene(scene);
                            stage.setMinHeight(400);
                            stage.setMinWidth(520);
                            stage.show();
                            pane.getScene().getWindow().hide();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        }.start();
    }
}
