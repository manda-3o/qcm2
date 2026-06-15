package com.gestionqcm.dao;

import com.gestionqcm.model.Etudiant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EtudiantDAO {
    public List<Etudiant> listAll() throws SQLException {
        List<Etudiant> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection(); PreparedStatement p = c.prepareStatement("SELECT * FROM etudiants ORDER BY num_etudiant")) {
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                list.add(map(rs));
            }
        }
        return list;
    }

    public int countTotal() throws SQLException {
        try (Connection c = DBConnection.getConnection(); PreparedStatement p = c.prepareStatement("SELECT count(*) FROM etudiants")) {
            ResultSet rs = p.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    public List<Etudiant> listPage(int page, int size) throws SQLException {
        int offset = (Math.max(page, 1) - 1) * size;
        List<Etudiant> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection(); PreparedStatement p = c.prepareStatement("SELECT * FROM etudiants ORDER BY num_etudiant LIMIT ? OFFSET ?")) {
            p.setInt(1, size);
            p.setInt(2, offset);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                list.add(map(rs));
            }
        }
        return list;
    }

    public Etudiant findById(String id) throws SQLException {
        try (Connection c = DBConnection.getConnection(); PreparedStatement p = c.prepareStatement("SELECT * FROM etudiants WHERE num_etudiant = ?")) {
            p.setString(1, id);
            ResultSet rs = p.executeQuery();
            if (rs.next()) return map(rs);
            return null;
        }
    }

    public List<Etudiant> listByKeyword(String keyword) throws SQLException {
        String search = "%" + keyword.toLowerCase() + "%";
        List<Etudiant> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection(); PreparedStatement p = c.prepareStatement(
                "SELECT * FROM etudiants WHERE lower(num_etudiant) LIKE ? OR lower(nom) LIKE ? OR lower(prenoms) LIKE ? OR lower(adr_email) LIKE ? ORDER BY num_etudiant")) {
            for (int i = 1; i <= 4; i++) {
                p.setString(i, search);
            }
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                list.add(map(rs));
            }
        }
        return list;
    }

    public List<Etudiant> listByLevel(String niveau) throws SQLException {
        List<Etudiant> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection(); PreparedStatement p = c.prepareStatement("SELECT * FROM etudiants WHERE niveau = ? ORDER BY num_etudiant")) {
            p.setString(1, niveau);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                list.add(map(rs));
            }
        }
        return list;
    }

    public void create(Etudiant e) throws SQLException {
        try (Connection c = DBConnection.getConnection(); PreparedStatement p = c.prepareStatement("INSERT INTO etudiants(num_etudiant, nom, prenoms, niveau, adr_email, photo) VALUES(?,?,?,?,?,?)")) {
            p.setString(1, e.getNumEtudiant());
            p.setString(2, e.getNom());
            p.setString(3, e.getPrenoms());
            p.setString(4, e.getNiveau());
            p.setString(5, e.getAdrEmail());
            p.setString(6, e.getPhoto());
            p.executeUpdate();
        }
    }

    public void update(Etudiant e) throws SQLException {
        try (Connection c = DBConnection.getConnection(); PreparedStatement p = c.prepareStatement("UPDATE etudiants SET nom=?, prenoms=?, niveau=?, adr_email=?, photo=COALESCE(?, photo) WHERE num_etudiant=?")) {
            p.setString(1, e.getNom());
            p.setString(2, e.getPrenoms());
            p.setString(3, e.getNiveau());
            p.setString(4, e.getAdrEmail());
            p.setString(5, e.getPhoto());
            p.setString(6, e.getNumEtudiant());
            p.executeUpdate();
        }
    }

    public void delete(String id) throws SQLException {
        try (Connection c = DBConnection.getConnection(); PreparedStatement p = c.prepareStatement("DELETE FROM etudiants WHERE num_etudiant = ?")) {
            p.setString(1, id);
            p.executeUpdate();
        }
    }

    private Etudiant map(ResultSet rs) throws SQLException {
        return new Etudiant(
                rs.getString("num_etudiant"),
                rs.getString("nom"),
                rs.getString("prenoms"),
                rs.getString("niveau"),
                rs.getString("adr_email"),
                rs.getString("photo")
        );
    }
}
