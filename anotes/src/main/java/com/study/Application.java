package com.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("");
            Statement statement = connection.createStatement();
            statement.execute("");
            statement.executeUpdate("");
            statement.executeQuery("");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SpringApplication.run(Application.class,args);
    }
}
