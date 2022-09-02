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
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserController implements Initializable {
    @FXML
    private DatePicker DoB;
    @FXML
    private Label LabelconfirmationMessage,selection;
    @FXML
    private Button Back,load;

    @FXML
    private TextField firstnameTextField,lastnameTextField,ageTextField,emailTextField,phoneNumberTextField,keywordTextField;
    @FXML
    private ComboBox<String> combobox;
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



    ObservableList<UserViewModel> userViewModelsObservableList = FXCollections.observableArrayList();
    DBController connect = new DBController();
    Connection connectdb = connect.getConnection();




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       combobox();
       displayListOnOpen();
       setcellValueFromTableToFeild();

    }

    public void combobox(){
        String [] items ={"Male","Female"};
        combobox.getItems().addAll(items);

        combobox.setOnAction(event -> {
            String data =combobox.getSelectionModel().getSelectedItem().toString();
            selection.setText(data);
        });
    }

    public void displayListOnOpen(){
        try {
            String query= "SELECT user_id,First_Name,Last_Name,Age,Gender,Date_of_birth,Phone_Number,Joined_on,Email FROM usersdetails";
            Statement statement = connectdb.createStatement();
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

           filter();


        }catch (SQLException e){
            Logger.getLogger(ViewUsersController.class.getName()).log(Level.SEVERE,null,e);
            e.printStackTrace();
        }
    }

    public void filter(){
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
                }else if(userViewModel.getGender().toLowerCase().indexOf(keywordsearch)> -1){
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

    }

    public void backOnAction(ActionEvent event) {
        previousPage();
    }

    public void ClearButtonOnAction(ActionEvent event) {
        clearFeilds();
    }

    public void addUsersOnAction(ActionEvent event) {
        if(firstnameTextField.getText().isBlank()==false && lastnameTextField.getText().isBlank()==false  && emailTextField.getText().isBlank()==false && phoneNumberTextField.getText().isBlank()==false ){
            registerUsers();
        }
        else {
            LabelconfirmationMessage.setText("Please enter some values into the fields!!");
        }

    }

    public void deleteUseronAction(ActionEvent event){
        Delete();
    }

    public void updateUserOnAction(ActionEvent event) throws SQLException {
        try {
            UserViewModel userViewModel = userTableView.getSelectionModel().getSelectedItem();
            String  firstname=firstnameTextField.getText();
            String  lasttname=lastnameTextField.getText();
            String age = ageTextField.getText();
            String gender =selection.getText();
            String dob = String.valueOf(DoB.getValue());
            String phonenumber = phoneNumberTextField.getText();
            String email = emailTextField.getText();
            String insertFields = "UPDATE usersdetails SET First_Name = '"+firstname+"', Last_Name = '"+lasttname+"', Age = '"+age+"', Gender = '"+gender+"', Date_of_birth = '"+dob+"'," +
                    " Phone_Number = '"+phonenumber+"', Email = '"+email+"' WHERE user_id = ?";
            PreparedStatement ps = connectdb.prepareStatement(insertFields);
            ps.setInt(1,userViewModel.getUserId());
            ps.execute();
            LabelconfirmationMessage.setText(userViewModel.getUserId()+" was Updated successfully");
            clearFeilds();
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }



    }

    public void setcellValueFromTableToFeild(){
        userTableView.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                UserViewModel userViewModel = userTableView.getItems().get(userTableView.getSelectionModel().getSelectedIndex());
                firstnameTextField.setText(userViewModel.getFirstname());
                lastnameTextField.setText(userViewModel.getLastname());
                ageTextField.setText(userViewModel.getAge().toString());
                DoB.setValue(LocalDate.parse(userViewModel.getDob().toString()));
                selection.setText(userViewModel.getGender());
                emailTextField.setText(userViewModel.getEmail());
                phoneNumberTextField.setText(userViewModel.getPhonenumber().toString());
            }
        });
    }



    public void registerUsers(){
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

    public void clearFeilds(){
        this.firstnameTextField.setText("");
        this.lastnameTextField.setText("");
        this.ageTextField.setText("");
        this.selection.setText("");
        this.phoneNumberTextField.setText("");
        this.emailTextField.setText("");
    }
    public void closePage(){
        Stage stage = (Stage) Back.getScene().getWindow();
        stage.close();
    }

    public void Refresh(ActionEvent event) {
        userViewModelsObservableList.clear();
        try {
            String query = "SELECT user_id,First_Name,Last_Name,Age,Gender,Date_of_birth,Phone_Number,Joined_on,Email FROM usersdetails";
            Statement statement = connectdb.createStatement();
            ResultSet queryOutput = statement.executeQuery(query);

            while (queryOutput.next()) {
                Integer queryUserId = queryOutput.getInt("user_id");
                String queryFirstName = queryOutput.getString("First_Name");
                String queryLastName = queryOutput.getString("Last_Name");
                Integer queryAge = queryOutput.getInt("Age");
                String queryGender = queryOutput.getString("Gender");
                Date queryDateofBith = queryOutput.getDate("Date_of_Birth");
                Integer queryPhoneNumber = queryOutput.getInt("Phone_Number");
                Timestamp queryJoinedon = queryOutput.getTimestamp("Joined_on");
                String queryEmail = queryOutput.getString("Email");

                userViewModelsObservableList.add(new UserViewModel(queryUserId, queryFirstName, queryLastName, queryAge, queryGender, queryDateofBith, queryPhoneNumber, queryJoinedon, queryEmail));
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

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();

        }
    }
    public void Delete() {
        try
        {
            PreparedStatement ps = connectdb.prepareStatement("DELETE FROM usersdetails WHERE user_Id = ?");
            UserViewModel userViewModel = userTableView.getSelectionModel().getSelectedItem();
            ps.setInt(1, userViewModel.getUserId());
            ps.execute();
            LabelconfirmationMessage.setText(userViewModel.getFirstname()+" Was Deleted");

        }
        catch (SQLException e)
        {
           e.printStackTrace();
           e.getCause();
        }
    }


}
