package com.example.projectwork;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField enterPasswordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button createAccountButton;
    @FXML
    private Label noticeLabel;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void loginButtonOnAction(ActionEvent event) {
        if(usernameTextField.getText().isBlank()==false && enterPasswordField.getText().isBlank()==false){
            verifyAdmin();
        }
        else {
            noticeLabel.setText("Please enter username and password!!");
        }

    }

    public void cancelButtonOnAction(ActionEvent event) {
        closePage();
    }


    public void registrationForm(ActionEvent event) {
        registerPage();
    }

    public void registerPage(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("register.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 521, 625);
            Stage stage= new Stage();
            stage.setTitle("Register");
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.show();
            closePage();

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
    public void landingPage(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("landing.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
            Stage stage= new Stage();
            stage.setTitle("Dashboard");
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
            closePage();

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }


    public void verifyAdmin(){
        DBController connect = new DBController();
        Connection connectdb = connect.getConnection();

        String query = "SELECT count(1) FROM admin_accounts WHERE adminusername='" + usernameTextField.getText() + "' AND adminpassword='" + enterPasswordField.getText()+ "'";

        try {
            Statement statement = connectdb.createStatement();
            ResultSet queryResults = statement.executeQuery(query);

            while (queryResults.next()){
                if (queryResults.getInt(1)==1){
                    landingPage();
                }
                else {
                    noticeLabel.setText("Wrong Details!!");
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public void closePage(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();

    }


}
