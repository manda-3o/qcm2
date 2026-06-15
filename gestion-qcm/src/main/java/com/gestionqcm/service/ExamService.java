package com.gestionqcm.service;

import com.gestionqcm.dao.ExamenDAO;
import com.gestionqcm.model.Examen;

import java.sql.SQLException;
import java.util.List;

public class ExamService {
    private final ExamenDAO dao = new ExamenDAO();

    public List<Examen> listAll() throws SQLException {
        return dao.listAll();
    }

    public List<Examen> listPage(int page, int size) throws SQLException {
        return dao.listPage(page, size);
    }

    public int countTotal() throws SQLException {
        return dao.countTotal();
    }

    public void save(Examen examen) throws SQLException {
        dao.create(examen);
    }
}
