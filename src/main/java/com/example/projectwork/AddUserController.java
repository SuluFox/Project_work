package com.example.projectwork;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.util.Date;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class AddUserController implements Initializable {
    @FXML
    private DatePicker DoB;
    @FXML
    private Label LabelconfirmationMessage,selection;
    @FXML
    private Button Back,clearButton,Add;
    @FXML
    private TextField firstnameTextField,lastnameTextField,ageTextField,emailTextField,phoneNumberTextField;
    @FXML
    private ComboBox<String> combobox;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String [] items ={"Male","Female"};
        combobox.getItems().addAll(items);

        combobox.setOnAction(event -> {
            String data =combobox.getSelectionModel().getSelectedItem().toString();
            selection.setText(data);
        });

    }




    public void backOnAction(ActionEvent event) {
        previousPage();
    }

    public void ClearButtonOnAction(ActionEvent event) {
        clearFeilds();
    }

    public void addUsersOnAction(ActionEvent event) {
        registerUsers();
    }



    public void registerUsers(){
        DBController connect = new DBController();
        Connection connectdb = connect.getConnection();

        String  firstname=firstnameTextField.getText();
        String  lasttname=lastnameTextField.getText();
        String age = ageTextField.getText();
        String gender =selection.getText();
        String dob = String.valueOf(DoB.getValue());
        String phonenumber = phoneNumberTextField.getText();
        String email = emailTextField.getText();
        Timestamp dctcd = new Timestamp(new Date().getTime());


        String insertFields = "INSERT INTO usersdetails(First_Name, Last_Name, Age, Gender, Date_of_birth, Phone_Number,Joined_on, Email) VALUES('";
        String insertValues = firstname+"','"+lasttname+"','"+age+"','"+gender+"','"+dob+"','"+phonenumber+"','"+dctcd+"','"+email+"')";
        String insertToRegister= insertFields+insertValues;

        try {

            Statement statement = connectdb.createStatement();
            statement.executeUpdate(insertToRegister);
            LabelconfirmationMessage.setText("Successfully added user");

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }

    }
    public void previousPage(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("user.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 659, 465);
            Stage stage= new Stage();
            stage.setTitle("Users Page");
            stage.setScene(scene);
            stage.show();
            closePage();

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public void clearFeilds(){
        this.firstnameTextField.setText("");
        this.lastnameTextField.setText("");
        this.ageTextField.setText("");
        this.selection.setText("");
        this.DoB.setValue(null);
        this.phoneNumberTextField.setText("");
        this.emailTextField.setText("");
    }
    public void closePage(){
        Stage stage = (Stage) Back.getScene().getWindow();
        stage.close();

    }


}
