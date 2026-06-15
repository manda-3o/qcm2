package com.gestionqcm.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = System.getenv().getOrDefault("GESTION_QCM_DB_URL", "jdbc:postgresql://localhost:5432/gestion_qcm");
    private static final String USER = System.getenv().getOrDefault("GESTION_QCM_DB_USER", "postgres");
    private static final String PASS = System.getenv().getOrDefault("GESTION_QCM_DB_PASS", "postgres");

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
