package com.example.projectwork;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.sql.*;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewUsersController  implements Initializable {
    @FXML
    private TableView<UserViewModel> userTableView;
    @FXML
    private TableColumn<UserViewModel,Integer> useridTableColumn;
    @FXML
    private TableColumn<UserViewModel,String> firstnameTableColumn;
    @FXML
    private TableColumn<UserViewModel,String> lastnameTableColumn;
    @FXML
    private TableColumn<UserViewModel,Integer> ageTableColumn;
    @FXML
    private TableColumn<UserViewModel,String> genderTableColumn;
    @FXML
    private TableColumn<UserViewModel, Date> dateofbirthTableColumn;
    @FXML
    private TableColumn<UserViewModel,Integer> phonenumberTableColumn;
    @FXML
    private TableColumn<UserViewModel, Timestamp> joinedonTableColumn;
    @FXML
    private TableColumn<UserViewModel,String> emailTableColumn;
    @FXML
    private TextField keywordTextField;


    ObservableList<UserViewModel>userViewModelsObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DBController connect = new DBController();
        Connection connection = connect.getConnection();

        String query= "SELECT user_id,First_Name,Last_Name,Age,Gender,Date_of_birth,Phone_Number,Joined_on,Email FROM usersdetails";

        try {
            Statement statement = connection.createStatement();
            ResultSet queryOutput = statement.executeQuery(query);

            while (queryOutput.next()){
                Integer queryUserId = queryOutput.getInt("user_id");
                String queryFirstName = queryOutput.getString("First_Name");
                String queryLastName = queryOutput.getString("Last_Name");
                Integer queryAge = queryOutput.getInt("Age");
                String queryGender = queryOutput.getString("Gender");
                Date queryDateofBith= queryOutput.getDate("Date_of_Birth");
                Integer queryPhoneNumber = queryOutput.getInt("Phone_Number");
                Timestamp queryJoinedon = queryOutput.getTimestamp("Joined_on");
                String queryEmail = queryOutput.getString("Email");

                userViewModelsObservableList.add(new UserViewModel(queryUserId,queryFirstName,queryLastName,queryAge,queryGender,queryDateofBith,queryPhoneNumber,queryJoinedon,queryEmail));
            }

            useridTableColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
            firstnameTableColumn.setCellValueFactory(new PropertyValueFactory<>("firstname"));
            lastnameTableColumn.setCellValueFactory(new PropertyValueFactory<>("lastname"));
            ageTableColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
            genderTableColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
            dateofbirthTableColumn.setCellValueFactory(new PropertyValueFactory<>("dob"));
            phonenumberTableColumn.setCellValueFactory(new PropertyValueFactory<>("phonenumber"));
            joinedonTableColumn.setCellValueFactory(new PropertyValueFactory<>("joinedon"));
            emailTableColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

            userTableView.setItems(userViewModelsObservableList);

            FilteredList<UserViewModel> filteredData = new FilteredList<>(userViewModelsObservableList, b -> true);

            keywordTextField.textProperty().addListener((observable, oldValue, newValue)->{
                filteredData.setPredicate(userViewModel ->{
                    if (newValue.isEmpty() || oldValue.isEmpty() || newValue == null){
                        return true;
                    }

                    String keywordsearch = newValue.toLowerCase();
                    if (userViewModel.getFirstname().toLowerCase().indexOf(keywordsearch)> -1){
                        return true;
                    }else if(userViewModel.getLastname().toLowerCase().indexOf(keywordsearch)> -1){
                        return true;
                    }else if(userViewModel.getEmail().toLowerCase().indexOf(keywordsearch)> -1){
                        return true;
                    }else if(userViewModel.getDob().toString().indexOf(keywordsearch)> -1){
                        return true;
                    }else if(userViewModel.getPhonenumber().toString().indexOf(keywordsearch)> -1){
                        return true;
                    }else if(userViewModel.getJoinedon().toString().indexOf(keywordsearch)> -1){
                        return true;
                    }else if(userViewModel.getAge().toString().indexOf(keywordsearch)> -1){
                        return true;
                    }else {
                        return false;
                    }
                });
            });

            SortedList<UserViewModel> sortData = new SortedList<>(filteredData);
                       //Bind sorted result with table
            sortData.comparatorProperty().bind(userTableView.comparatorProperty());

            //apply filtered and sorted data to the table
            userTableView.setItems(sortData);


        }catch (SQLException e){
            Logger.getLogger(ViewUsersController.class.getName()).log(Level.SEVERE,null,e);
            e.printStackTrace();
        }
    }
}
