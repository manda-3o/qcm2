package com.gestionqcm.service;

import com.gestionqcm.dao.AdminDAO;
import com.gestionqcm.model.Admin;
import com.gestionqcm.utils.PasswordUtils;

import java.sql.SQLException;

public class AdminService {
    private final AdminDAO dao = new AdminDAO();

    public void initializeDefaultAdmin() throws SQLException {
        dao.createAdminTableIfMissing();
        if (dao.countAdmins() == 0) {
            String defaultPassword = System.getenv().getOrDefault("GESTION_QCM_ADMIN_PASS", "Admin@123");
            String hashed = PasswordUtils.hashPassword(defaultPassword);
            dao.create(new Admin(0, "admin", hashed));
        }
    }

    public boolean authenticate(String username, String password) throws SQLException {
        Admin admin = dao.findByUsername(username);
        return admin != null && PasswordUtils.verifyPassword(password, admin.getPassword());
    }
}
