package com.example.projectwork;
import java.sql.Connection;
import java.sql.DriverManager;


public class DBController {
    public Connection dataBaseLink;

    public Connection getConnection(){
        String dbname = "trytwojavafx";
        String dbuser =  "learnfx";
        String dbpassword = "123456789";
        String url = "jdbc:mysql://localhost:3306/" + dbname;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            dataBaseLink = DriverManager.getConnection(url,dbuser,dbpassword);

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
        return dataBaseLink;
    }

}
