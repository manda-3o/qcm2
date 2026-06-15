package com.gestionqcm.service;

import com.gestionqcm.dao.EtudiantDAO;
import com.gestionqcm.model.Etudiant;

import java.sql.SQLException;
import java.util.List;

public class EtudiantService {
    private final EtudiantDAO dao = new EtudiantDAO();

    public List<Etudiant> listAll() throws SQLException {
        return dao.listAll();
    }

    public List<Etudiant> listPage(int page, int size) throws SQLException {
        return dao.listPage(page, size);
    }

    public int countTotal() throws SQLException {
        return dao.countTotal();
    }

    public Etudiant findById(String id) throws SQLException {
        return dao.findById(id);
    }

    public void save(Etudiant etudiant) throws SQLException {
        if (etudiant == null || etudiant.getNumEtudiant() == null) {
            return;
        }
        if (dao.findById(etudiant.getNumEtudiant()) != null) {
            dao.update(etudiant);
        } else {
            dao.create(etudiant);
        }
    }

    public void delete(String id) throws SQLException {
        dao.delete(id);
    }

    public List<Etudiant> search(String keyword) throws SQLException {
        if (keyword == null || keyword.trim().isEmpty()) {
            return dao.listAll();
        }
        return dao.listByKeyword(keyword.trim());
    }

    public List<Etudiant> listByLevel(String niveau) throws SQLException {
        if (niveau == null || niveau.isBlank()) {
            return dao.listAll();
        }
        return dao.listByLevel(niveau.trim());
    }
}
