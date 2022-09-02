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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductsController  implements Initializable {

    @FXML
    private TextField productnametxd,numofproducttxd,keywordTextField;
    @FXML
    private Button backButton;

    @FXML
    private TableView<ProductModel> productviews;

    @FXML
    private TableColumn<ProductModel, Integer> idcolumn;

    @FXML
    private TableColumn<ProductModel, Integer> numofproductcolumn;

    @FXML
    private TableColumn<ProductModel, String> productnamecolumn;

    @FXML
    private Label statusLabel;

    ObservableList<ProductModel> ProductModelObservableList = FXCollections.observableArrayList();

    DBController connect = new DBController();
    Connection connectdb = connect.getConnection();



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loader();
        filter();
        setcellValueFromTableToFeild();
    }

    public void loader() {
        String query = "SELECT * FROM products";

        try {
            Statement statement = connectdb.createStatement();
            ResultSet queryOutput = statement.executeQuery(query);

            while (queryOutput.next()) {
                Integer queryProductId = queryOutput.getInt("product_id");
                String queryProductname = queryOutput.getString("product_name");
                Integer querynumberOfProduct = queryOutput.getInt("number_of_product");

                ProductModelObservableList.add(new ProductModel(queryProductId, queryProductname, querynumberOfProduct));
            }

            idcolumn.setCellValueFactory(new PropertyValueFactory<>("product_id"));
            productnamecolumn.setCellValueFactory(new PropertyValueFactory<>("product_name"));
            numofproductcolumn.setCellValueFactory(new PropertyValueFactory<>("number_of_product"));


            productviews.setItems(ProductModelObservableList);

        }catch (SQLException e){
            Logger.getLogger(ViewUsersController.class.getName()).log(Level.SEVERE,null,e);
            e.printStackTrace();
        }
    }
    public void filter(){
        FilteredList<ProductModel> filteredData = new FilteredList<>(ProductModelObservableList, b -> true);

        keywordTextField.textProperty().addListener((observable, oldValue, newValue)->{
            filteredData.setPredicate(ProductModel ->{
                if (newValue.isEmpty() || oldValue.isEmpty() || newValue == null){
                    return true;
                }

                String keywordsearch = newValue.toLowerCase();
                if (ProductModel.product_name.toLowerCase().indexOf(keywordsearch)> -1){
                    return true;
                }else {
                    return false;
                }
            });
        });

        SortedList<ProductModel> sortData = new SortedList<>(filteredData);
        //Bind sorted result with table
        sortData.comparatorProperty().bind(productviews.comparatorProperty());

        //apply filtered and sorted data to the table
        productviews.setItems(sortData);
    }

    public void updateProductsOnAction(ActionEvent event){
        update();
        refresh();
    }
    public void addProductsOnAction(ActionEvent event){
        if(productnametxd.getText().isBlank()==false && numofproducttxd.getText().isBlank()==false){
            addProduct();
            refresh();
        }
        else {
            statusLabel.setText("Please enter Some values");

        }
    }
    public void deleteProductOnAction(ActionEvent event){
        deleteProduct();
        refresh();
    }



    public void addProduct(){
        String  productname = productnametxd.getText();
        String  numberOfproduct=numofproducttxd.getText();

        String insertFields = "INSERT INTO products (product_name, number_of_product)  VALUES('";
        String insertValues = productname+"','"+numberOfproduct+"')";
        String insertToRegister= insertFields+insertValues;

        try {

            Statement statement = connectdb.createStatement();
            statement.executeUpdate(insertToRegister);
            statusLabel.setText("New Product Added");

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
    public void refresh(){
        ProductModelObservableList.clear();
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
            ProductModel productModel = productviews.getSelectionModel().getSelectedItem();
            String  productname = productnametxd.getText();
            String  numberofproduct=numofproducttxd.getText();
            String insertFields = "UPDATE products SET product_name = '"+productname+"', number_of_product = '"+numberofproduct+"' WHERE product_id = ?";
            PreparedStatement ps = connectdb.prepareStatement(insertFields);
            ps.setInt(1,productModel.product_id);
            ps.execute();
            statusLabel.setText(productModel.product_name+" was Updated successfully");
            clearFeilds();

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }


    public void deleteProduct(){
        try
        {
            PreparedStatement ps = connectdb.prepareStatement("DELETE FROM products WHERE product_Id = ?");
            ProductModel productModel = productviews.getSelectionModel().getSelectedItem();
            ps.setInt(1, productModel.product_id);
            ps.execute();
            statusLabel.setText("id:"+productModel.product_name+" Was Deleted");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            e.getCause();
        }

    }
    public void clearFeilds(){
        this.productnametxd.setText("");
        this.numofproducttxd.setText("");
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
        productviews.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                ProductModel productModel = productviews.getItems().get(productviews.getSelectionModel().getSelectedIndex());
                productnametxd.setText(productModel.getProduct_name());
                numofproducttxd.setText(productModel.getNumber_of_product().toString());
            }
        });
    }
}
