package com.sealcia.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcUtils {
    private static JdbcUtils instance;
    private final Connection connection;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private JdbcUtils() throws SQLException {
        connection =
                DriverManager.getConnection("jdbc:mysql://localhost/englishdb", "root", "root");
    }

    public static JdbcUtils getInstance() throws SQLException {
        if (instance == null) {
            instance = new JdbcUtils();
        }
        return instance;
    }

    public Connection connect() {
        return connection;
    }

    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}
