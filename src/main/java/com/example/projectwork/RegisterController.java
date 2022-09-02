package com.example.projectwork;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;


public class RegisterController  implements Initializable {
    @FXML
    private TextField firstnameTextField,lastnameTextField,usernameTextField;
    @FXML
    private PasswordField setPasswordField,confirmPasswordField;
    @FXML
    private Button closeButton;
    @FXML
    private Label noticeLabel,confirmPasswordLabel;

    public void registerButtonOnAction(ActionEvent event) {
        if (firstnameTextField.getText().isBlank()==false && lastnameTextField.getText().isBlank()==false && usernameTextField.getText().isBlank()==false){
            checkIfPasswordsMatch();
        }else{
            noticeLabel.setText("Can not register when a field is empty please provide all details");
        }
    }

    public void closeButtonOnAction(ActionEvent event) {
        closePage();
    }

    public void loginButtonOnAction(ActionEvent event) {
        loginPage();
    }

    public void checkIfPasswordsMatch(){
        if (setPasswordField.getText().equals(confirmPasswordField.getText())){
            noticeLabel.setText("");
            registerAdmin();
        }else {
            confirmPasswordLabel.setText("password does not match");
        }
    }
    public void registerAdmin(){
        DBController connect = new DBController();
        Connection connectdb = connect.getConnection();

        String  firstname=firstnameTextField.getText();
        String  lasttname=lastnameTextField.getText();
        String  username=usernameTextField.getText();
        String  setpassword=setPasswordField.getText();
        String  confirmpassword=confirmPasswordField.getText();

        String insertFields = "INSERT INTO admin_accounts(adminfirstname,adminlastname,adminusername,adminpassword) VALUES('";
        String insertValues = firstname+"','"+lasttname+"','"+username+"','"+setpassword+"')";
        String insertToRegister= insertFields+insertValues;

        try {

            Statement statement = connectdb.createStatement();
            statement.executeUpdate(insertToRegister);
            noticeLabel.setText("Successful");
            loginPage();
            closePage();

        }catch (Exception e){
            e.printStackTrace();
            noticeLabel.setText(String.valueOf(e));
            e.getCause();
        }

    }
    public void loginPage(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 520, 400);
            Stage stage= new Stage();
            stage.setTitle("Hello!");
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.show();
            closePage();

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
    public void closePage(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
