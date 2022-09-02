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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.*;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EquipmentController implements Initializable {
    @FXML
    private Button backButton;

    @FXML
    private TextField equiptypetxd,idtxd,numofequiptxd,keywordTextField;

    @FXML
    private TableView<EquipmentModel> equipviews;

    @FXML
    private TableColumn<EquipmentModel, Integer> idcolumn;

    @FXML
    private TableColumn<EquipmentModel, Integer> numofequipcolumn;

    @FXML
    private TableColumn<EquipmentModel, String> equiptypecolumn;

    @FXML
    private Label statusLabel;

    ObservableList<EquipmentModel> EquipmentModelObservableList = FXCollections.observableArrayList();

    DBController connect = new DBController();
    Connection connectdb = connect.getConnection();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loader();
        filter();
        setcellValueFromTableToFeild();
    }
    public void loader() {
        String query = "SELECT * FROM equipments";

        try {
            Statement statement = connectdb.createStatement();
            ResultSet queryOutput = statement.executeQuery(query);

            while (queryOutput.next()) {
                Integer queryEquipmentId = queryOutput.getInt("equipment_id");
                String queryEquipmentType = queryOutput.getString("equipment_type");
                Integer querynumberOfEquipment = queryOutput.getInt("number_of_equipment");

                EquipmentModelObservableList.add(new EquipmentModel(queryEquipmentId, queryEquipmentType, querynumberOfEquipment));
            }

            idcolumn.setCellValueFactory(new PropertyValueFactory<>("equipment_id"));
            equiptypecolumn.setCellValueFactory(new PropertyValueFactory<>("equipment_type"));
            numofequipcolumn.setCellValueFactory(new PropertyValueFactory<>("number_of_equipment"));


            equipviews.setItems(EquipmentModelObservableList);

        }catch (SQLException e){
            Logger.getLogger(ViewUsersController.class.getName()).log(Level.SEVERE,null,e);
            e.printStackTrace();
        }
    }
    public void filter(){
        FilteredList<EquipmentModel> filteredData = new FilteredList<>(EquipmentModelObservableList, b -> true);

        keywordTextField.textProperty().addListener((observable, oldValue, newValue)->{
            filteredData.setPredicate(EquipmentModel ->{
                if (newValue.isEmpty() || oldValue.isEmpty() || newValue == null){
                    return true;
                }

                String keywordsearch = newValue.toLowerCase();
                if (EquipmentModel.equipment_type.toLowerCase().indexOf(keywordsearch)> -1){
                    return true;
                }else {
                    return false;
                }
            });
        });

        SortedList<EquipmentModel> sortData = new SortedList<>(filteredData);
        //Bind sorted result with table
        sortData.comparatorProperty().bind(equipviews.comparatorProperty());

        //apply filtered and sorted data to the table
        equipviews.setItems(sortData);
    }



    public void updateEquipOnAction(ActionEvent event){
        update();
        refresh();
    }
    public void addEquipmentOnAction(ActionEvent event){
        if(equiptypetxd.getText().isBlank()==false && numofequiptxd.getText().isBlank()==false){
            addEquiment();
            refresh();
        }
        else {
            statusLabel.setText("Please enter Some values");

        }
    }
    public void deleteEquipmentOnAction(ActionEvent event){
        deleteEquipments();
        refresh();
    }



    public void addEquiment(){
        String  equipmentType = equiptypetxd.getText();
        String  numberOfEquipment=numofequiptxd.getText();

        String insertFields = "INSERT INTO equipments (equipment_type, number_of_equipment)  VALUES('";
        String insertValues = equipmentType+"','"+numberOfEquipment+"')";
        String insertToRegister= insertFields+insertValues;

        try {

            Statement statement = connectdb.createStatement();
            statement.executeUpdate(insertToRegister);
            statusLabel.setText("New Equipment Added");

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
    public void refresh(){
       EquipmentModelObservableList.clear();
        try {
            loader();

            clearFeilds();
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
        statusLabel.setText(" ");
    }
    public void update(){
        try {
            EquipmentModel equipmentModel = equipviews.getSelectionModel().getSelectedItem();
            String  equipmentType = equiptypetxd.getText();
            String  numberOfEquipment=numofequiptxd.getText();
            String insertFields = "UPDATE equipments SET equipment_type = '"+equipmentType+"', number_of_equipment = '"+numberOfEquipment+"' WHERE equipment_id = ?";
            PreparedStatement ps = connectdb.prepareStatement(insertFields);
            ps.setInt(1,equipmentModel.equipment_id);
            ps.execute();
            statusLabel.setText(equipmentModel.equipment_id+" was Updated successfully");
            clearFeilds();

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }


    public void deleteEquipments(){
        try
        {
            PreparedStatement ps = connectdb.prepareStatement("DELETE FROM equipments WHERE equipment_Id = ?");
            EquipmentModel equipmentModel = equipviews.getSelectionModel().getSelectedItem();
            ps.setInt(1, equipmentModel.equipment_id);
            ps.execute();
            statusLabel.setText("id:"+equipmentModel.equipment_id+" Was Deleted");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            e.getCause();
        }

    }
    public void clearFeilds(){
        this.equiptypetxd.setText("");
        this.numofequiptxd.setText("");
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
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();

    }
    public void setcellValueFromTableToFeild(){
        equipviews.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                EquipmentModel equipmentModel = equipviews.getItems().get(equipviews.getSelectionModel().getSelectedIndex());
                equiptypetxd.setText(equipmentModel.getEquipment_type());
                numofequiptxd.setText(equipmentModel.getNumber_of_equipment().toString());
            }
        });
    }





}
