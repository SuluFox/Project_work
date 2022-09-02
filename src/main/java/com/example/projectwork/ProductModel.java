package com.example.projectwork;

public class ProductModel {
    Integer product_id;
    String product_name;
    Integer number_of_product;

    public void ProductModel(){

    }

    public ProductModel(Integer product_id, String product_name, Integer number_of_product) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.number_of_product = number_of_product;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public Integer getNumber_of_product() {
        return number_of_product;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public void setNumber_of_product(Integer number_of_product) {
        this.number_of_product = number_of_product;
    }
}
