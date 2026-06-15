package com.gestionqcm.dao;

import com.gestionqcm.model.Question;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {
    public List<Question> listAll() throws SQLException {
        List<Question> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection(); PreparedStatement p = c.prepareStatement("SELECT * FROM questions ORDER BY num_quest")) {
            ResultSet rs = p.executeQuery();
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public int countTotal() throws SQLException {
        try (Connection c = DBConnection.getConnection(); PreparedStatement p = c.prepareStatement("SELECT count(*) FROM questions")) {
            ResultSet rs = p.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    public List<Question> listPage(int page, int size) throws SQLException {
        int offset = (Math.max(page, 1) - 1) * size;
        List<Question> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection(); PreparedStatement p = c.prepareStatement("SELECT * FROM questions ORDER BY num_quest LIMIT ? OFFSET ?")) {
            p.setInt(1, size);
            p.setInt(2, offset);
            ResultSet rs = p.executeQuery();
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public Question findById(int id) throws SQLException {
        try (Connection c = DBConnection.getConnection(); PreparedStatement p = c.prepareStatement("SELECT * FROM questions WHERE num_quest = ?")) {
            p.setInt(1, id);
            ResultSet rs = p.executeQuery();
            if (rs.next()) return map(rs);
            return null;
        }
    }

    public void create(Question q) throws SQLException {
        try (Connection c = DBConnection.getConnection(); PreparedStatement p = c.prepareStatement("INSERT INTO questions(num_quest, question, categorie, reponse1, reponse2, reponse3, reponse4, bonne) VALUES(?,?,?,?,?,?,?,?)")) {
            p.setInt(1, q.getNumQuest());
            p.setString(2, q.getQuestion());
            p.setString(3, q.getCategorie());
            p.setString(4, q.getReponse1());
            p.setString(5, q.getReponse2());
            p.setString(6, q.getReponse3());
            p.setString(7, q.getReponse4());
            p.setString(8, q.getBonne());
            p.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        try (Connection c = DBConnection.getConnection(); PreparedStatement p = c.prepareStatement("DELETE FROM questions WHERE num_quest = ?")) {
            p.setInt(1, id);
            p.executeUpdate();
        }
    }

    private Question map(ResultSet rs) throws SQLException {
        return new Question(
                rs.getInt("num_quest"),
                rs.getString("question"),
                rs.getString("categorie"),
                rs.getString("reponse1"),
                rs.getString("reponse2"),
                rs.getString("reponse3"),
                rs.getString("reponse4"),
                rs.getString("bonne")
        );
    }
}
