package com.board.utils;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static Connection connection = null;

    public static Connection getConnection() {
        try {
            Properties properties = new Properties();
            String dbSettingsPropertyFile = "C:\\Users\\Acer\\Desktop\\NoticeBoard\\src\\main\\resources\\jdbc.properties";
            FileReader fReader = new FileReader(dbSettingsPropertyFile);
            properties.load(fReader);
            String dbDriverClass = properties.getProperty("db.driver.class");
            String dbConnUrl = properties.getProperty("db.conn.url");
            String dbUserName = properties.getProperty("db.username");
            String dbPassword = properties.getProperty("db.password");
            Class.forName(dbDriverClass);

            connection = DriverManager.getConnection(dbConnUrl, dbUserName, dbPassword);
        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
