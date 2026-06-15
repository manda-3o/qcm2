package com.gestionqcm.dao;

import com.gestionqcm.model.Examen;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExamenDAO {
    public List<Examen> listAll() throws SQLException {
        List<Examen> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection(); PreparedStatement p = c.prepareStatement("SELECT * FROM examens ORDER BY num_exam")) {
            ResultSet rs = p.executeQuery();
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public int countTotal() throws SQLException {
        try (Connection c = DBConnection.getConnection(); PreparedStatement p = c.prepareStatement("SELECT count(*) FROM examens")) {
            ResultSet rs = p.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    public List<Examen> listPage(int page, int size) throws SQLException {
        int offset = (Math.max(page, 1) - 1) * size;
        List<Examen> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection(); PreparedStatement p = c.prepareStatement("SELECT * FROM examens ORDER BY num_exam LIMIT ? OFFSET ?")) {
            p.setInt(1, size);
            p.setInt(2, offset);
            ResultSet rs = p.executeQuery();
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public void create(Examen ex) throws SQLException {
        try (Connection c = DBConnection.getConnection(); PreparedStatement p = c.prepareStatement("INSERT INTO examens(num_exam, num_etudiant, annee_univ, note, duree, statut) VALUES(?,?,?,?,?,?)")) {
            p.setString(1, ex.getNumExam());
            p.setString(2, ex.getNumEtudiant());
            p.setString(3, ex.getAnneeUniv());
            p.setInt(4, ex.getNote());
            p.setInt(5, ex.getDuree());
            p.setString(6, ex.getStatut());
            p.executeUpdate();
        }
    }

    private Examen map(ResultSet rs) throws SQLException {
        return new Examen(
                rs.getString("num_exam"),
                rs.getString("num_etudiant"),
                rs.getString("annee_univ"),
                rs.getInt("note"),
                rs.getInt("duree"),
                rs.getString("statut")
        );
    }
}
