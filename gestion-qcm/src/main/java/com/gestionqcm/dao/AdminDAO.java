package com.gestionqcm.dao;

import com.gestionqcm.model.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAO {
    public void createAdminTableIfMissing() throws SQLException {
        try (Connection c = DBConnection.getConnection(); PreparedStatement p = c.prepareStatement(
                "CREATE TABLE IF NOT EXISTS admins (id SERIAL PRIMARY KEY, username VARCHAR(100) UNIQUE NOT NULL, password VARCHAR(200) NOT NULL)")) {
            p.executeUpdate();
        }
    }

    public Admin findByUsername(String username) throws SQLException {
        try (Connection c = DBConnection.getConnection(); PreparedStatement p = c.prepareStatement("SELECT id, username, password FROM admins WHERE username = ?")) {
            p.setString(1, username);
            ResultSet rs = p.executeQuery();
            if (rs.next()) {
                return new Admin(rs.getInt("id"), rs.getString("username"), rs.getString("password"));
            }
            return null;
        }
    }

    public int countAdmins() throws SQLException {
        try (Connection c = DBConnection.getConnection(); PreparedStatement p = c.prepareStatement("SELECT count(*) FROM admins")) {
            ResultSet rs = p.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }
    }

    public void create(Admin admin) throws SQLException {
        try (Connection c = DBConnection.getConnection(); PreparedStatement p = c.prepareStatement("INSERT INTO admins(username, password) VALUES(?,?)")) {
            p.setString(1, admin.getUsername());
            p.setString(2, admin.getPassword());
            p.executeUpdate();
        }
    }
}
