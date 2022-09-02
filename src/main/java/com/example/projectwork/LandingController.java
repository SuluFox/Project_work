package com.example.projectwork;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class LandingController implements Initializable {
    @FXML
    private Button logout;
    @FXML
    private Label total_members,total_gymequipments;


    DBController connect = new DBController();
    Connection connectdb = connect.getConnection();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        totalUsers();
        totalequip();

    }
    public void userPageOnAction(ActionEvent event){
        userPage();
    }
    public void equipmentPageOnAction(ActionEvent event){
        equipmentPage();

    }
    public void productPageOnAction(ActionEvent event){
        producttPage();

    }
    public void trainerPageOnAction(ActionEvent event){
        trainerPage();

    }
    public void logoutButtonOnAction(ActionEvent event){
        logOut();
    }


    public void userPage(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("user1.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1358, 694);
            Stage stage= new Stage();
            stage.setTitle("Users Page");
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
            closePage();

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
    public void equipmentPage(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("equipment.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 769, 658);
            Stage stage= new Stage();
            stage.setTitle("Equipments");
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
            closePage();

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
    public void producttPage(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("products.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 769, 658);
            Stage stage= new Stage();
            stage.setTitle("Products");
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
            closePage();

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
    public void trainerPage(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("trainer.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1358, 694);
            Stage stage= new Stage();
            stage.setTitle("Trainer");
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
            closePage();

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public void loginPage(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 520, 400);
            Stage stage= new Stage();
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
            closePage();

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
    public void logOut(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("You are about to log out of this Application");
        alert.setContentText("Are you sure you want to log out of this Application!!!");
        if (alert.showAndWait().get() == ButtonType.OK){
            loginPage();
        }
    }
    public void closePage(){
        Stage stage = (Stage) logout.getScene().getWindow();
        stage.close();
    }

    public void totalUsers(){
        String sql ="SELECT COUNT(user_id) As usercount FROM usersdetails";
        try {
            PreparedStatement preparedStatement = connectdb.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int count = resultSet.getInt("usercount");

                total_members.setText(String.valueOf(count));

            }
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
    public void totalequip(){
        String sql ="SELECT COUNT(equipment_id) As equipcount FROM equipments";
        try {
            PreparedStatement preparedStatement = connectdb.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int count = resultSet.getInt("equipcount");

                total_gymequipments.setText(String.valueOf(count));

            }
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }


}
