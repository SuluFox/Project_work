package com.example.projectwork;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TrainerController implements Initializable {
    @FXML
    private Button Back;
    @FXML
    private TextField firstnameTextField,lastnameTextField,emailTextField,phoneNumberTextField,keywordTextField;
    @FXML
    private Label LabelconfirmationMessage,selection;
    @FXML
    private TableColumn<TrainerModel, String> emailTableColumn;
    @FXML
    private TableColumn<TrainerModel, String> firstnameTableColumn;
    @FXML
    private TableColumn<TrainerModel, String> lastnameTableColumn;
    @FXML
    private TableColumn<TrainerModel, Integer> phonenumberTableColumn;
    @FXML
    private TableColumn<TrainerModel, Integer> traineridTableColumn;
    @FXML
    private TableView<TrainerModel> trainerTableView;



    ObservableList<TrainerModel> trainerModelObservableList = FXCollections.observableArrayList();
    DBController connect = new DBController();
    Connection connectdb = connect.getConnection();




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loader();
        filter();
        setcellValueFromTableToFeild();

    }
    public void loader(){
        try {
            String query= "SELECT trainer_id,trainer_firstname,trainer_lastname,trainer_phonenumber,email FROM trainers";
            Statement statement = connectdb.createStatement();
            ResultSet queryOutput = statement.executeQuery(query);

            while (queryOutput.next()){
                Integer queryId = queryOutput.getInt("trainer_id");
                String queryFirstName = queryOutput.getString("trainer_firstname");
                String queryLastName = queryOutput.getString("trainer_lastname");
                Integer queryPhoneNumber = queryOutput.getInt("trainer_phonenumber");
                String queryEmail = queryOutput.getString("email");

                trainerModelObservableList.add(new TrainerModel(queryId,queryFirstName,queryLastName,queryPhoneNumber,queryEmail));
            }

            traineridTableColumn.setCellValueFactory(new PropertyValueFactory<>("trainerId"));
            firstnameTableColumn.setCellValueFactory(new PropertyValueFactory<>("firstname"));
            lastnameTableColumn.setCellValueFactory(new PropertyValueFactory<>("lastname"));
            phonenumberTableColumn.setCellValueFactory(new PropertyValueFactory<>("phonenumber"));
            emailTableColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

            trainerTableView.setItems(trainerModelObservableList);



        }catch (SQLException e){
            Logger.getLogger(ViewUsersController.class.getName()).log(Level.SEVERE,null,e);
            e.printStackTrace();
        }

    }
    public void filter(){
        FilteredList<TrainerModel> filteredData = new FilteredList<>(trainerModelObservableList, b -> true);

        keywordTextField.textProperty().addListener((observable, oldValue, newValue)->{
            filteredData.setPredicate(TrainerModel ->{
                if (newValue.isEmpty() || oldValue.isEmpty() || newValue == null){
                    return true;
                }

                String keywordsearch = newValue.toLowerCase();
                if (TrainerModel.getFirstname().toLowerCase().indexOf(keywordsearch)> -1){
                    return true;
                }else if(TrainerModel.getLastname().toLowerCase().indexOf(keywordsearch)> -1){
                    return true;
                }else if(TrainerModel.getEmail().toLowerCase().indexOf(keywordsearch)> -1){
                    return true;
                }else if(TrainerModel.getPhonenumber().toString().indexOf(keywordsearch)> -1){
                    return true;
                }else {
                    return false;
                }
            });
        });

        SortedList<TrainerModel> sortData = new SortedList<>(filteredData);
        //Bind sorted result with table
        sortData.comparatorProperty().bind(trainerTableView.comparatorProperty());

        //apply filtered and sorted data to the table
        trainerTableView.setItems(sortData);


    }

    public void backOnAction(ActionEvent event) {
        previousPage();
    }
    public void addTrainersPageOnAction(ActionEvent event) {
        if(firstnameTextField.getText().isBlank()==false && lastnameTextField.getText().isBlank()==false && emailTextField.getText().isBlank()==false && phoneNumberTextField.getText().isBlank()==false){
            addTrainer();
        }
        else {
            LabelconfirmationMessage.setText("Please enter some values in the fields!!!");
        }

    }
    public void deleteTrainersOnAction(ActionEvent event) {
        delete();
    }
    public void updateTrainerOnAction(ActionEvent event) throws SQLException {
       update();
    }
    public void Refresh(ActionEvent event) {
        trainerModelObservableList.clear();
        try {
            String query = "SELECT trainer_id,trainer_firstname,trainer_lastname,trainer_phonenumber,email FROM trainers";
            Statement statement = connectdb.createStatement();
            ResultSet queryOutput = statement.executeQuery(query);

            while (queryOutput.next()) {
                Integer queryId = queryOutput.getInt("trainer_id");
                String queryFirstName = queryOutput.getString("trainer_firstname");
                String queryLastName = queryOutput.getString("trainer_lastname");
                Integer queryPhoneNumber = queryOutput.getInt("trainer_phonenumber");
                String queryEmail = queryOutput.getString("email");

                trainerModelObservableList.add(new TrainerModel(queryId, queryFirstName, queryLastName, queryPhoneNumber, queryEmail));
            }

            traineridTableColumn.setCellValueFactory(new PropertyValueFactory<>("trainerId"));
            firstnameTableColumn.setCellValueFactory(new PropertyValueFactory<>("firstname"));
            lastnameTableColumn.setCellValueFactory(new PropertyValueFactory<>("lastname"));
            phonenumberTableColumn.setCellValueFactory(new PropertyValueFactory<>("phonenumber"));
            emailTableColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

            trainerTableView.setItems(trainerModelObservableList);
            clearFeilds();
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
    public void ClearButtonOnAction(ActionEvent event) {
        clearFeilds();
    }

    public void update(){
        try {
            TrainerModel trainerModel = trainerTableView.getSelectionModel().getSelectedItem();
            String  firstname=firstnameTextField.getText();
            String  lasttname=lastnameTextField.getText();
            String phonenumber = phoneNumberTextField.getText();
            String email = emailTextField.getText();
            String insertFields = "UPDATE trainers SET trainer_firstname = '"+firstname+"', trainer_lastname = '"+lasttname+"', trainer_phonenumber = '"+phonenumber+"', email = '"+email+"' WHERE trainers.trainer_id = ?";
            PreparedStatement ps = connectdb.prepareStatement(insertFields);
            ps.setInt(1,trainerModel.getTrainerId());
            ps.execute();
            LabelconfirmationMessage.setText(trainerModel.getTrainerId()+" was Updated successfully");
            clearFeilds();
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
    public void delete(){
        try
        {
            PreparedStatement ps = connectdb.prepareStatement("DELETE FROM trainers WHERE trainer_id = ?");
            TrainerModel trainerModel = trainerTableView.getSelectionModel().getSelectedItem();
            ps.setInt(1, trainerModel.getTrainerId());
            ps.execute();
            LabelconfirmationMessage.setText(trainerModel.getFirstname()+" Was Deleted");
        }
        catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
    }
    public void addTrainer(){
        String  firstname=firstnameTextField.getText();
        String  lasttname=lastnameTextField.getText();
        String phonenumber = phoneNumberTextField.getText();
        String email = emailTextField.getText();


        String insertFields = "INSERT INTO trainers(trainer_firstname,trainer_lastname,trainer_phonenumber,email) VALUES('";
        String insertValues = firstname+"','"+lasttname+"','"+phonenumber+"','"+email+"')";
        String insertToRegister= insertFields+insertValues;



        try {

            Statement statement = connectdb.createStatement();
            statement.executeUpdate(insertToRegister);
            LabelconfirmationMessage.setText("New Trainer Added!!");
            clearFeilds();



        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
    public void previousPage(){
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
    public void closePage(){
        Stage stage = (Stage) Back.getScene().getWindow();
        stage.close();

    }
    public void clearFeilds(){
        this.firstnameTextField.setText("");
        this.lastnameTextField.setText("");
        this.selection.setText("");
        this.phoneNumberTextField.setText("");
        this.emailTextField.setText("");
    }
    public void setcellValueFromTableToFeild(){
        trainerTableView.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                TrainerModel trainerModel = trainerTableView.getItems().get(trainerTableView.getSelectionModel().getSelectedIndex());
                firstnameTextField.setText(trainerModel.getFirstname());
                lastnameTextField.setText(trainerModel.getLastname());
                emailTextField.setText(trainerModel.getEmail());
                phoneNumberTextField.setText(String.valueOf(trainerModel.getPhonenumber()));
            }
        });
    }



}
