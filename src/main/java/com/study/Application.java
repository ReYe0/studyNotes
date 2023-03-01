package com.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

@SpringBootApplication
public class Application {
    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection("");
        connection.rollback();
        SpringApplication.run(Application.class,args);
    }
}
