package com.gestionqcm.controller;

import com.gestionqcm.service.AdminService;
import com.gestionqcm.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    private final AdminService adminService = new AdminService();

    @Override
    public void init() {
        try {
            adminService.initializeDefaultAdmin();
        } catch (Exception ignored) {
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String remembered = WebUtils.getCookieValue(req, "rememberUser");
        if (remembered != null && req.getParameter("username") == null) {
            req.setAttribute("username", remembered);
        }
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String redirect = req.getParameter("redirect");
        if (redirect == null || redirect.endsWith("/login")) {
            redirect = req.getContextPath() + "/dashboard";
        }
        try {
            boolean valid = adminService.authenticate(username, password);
            if (valid) {
                req.getSession().setAttribute("user", username);
                req.getSession().setMaxInactiveInterval(20 * 60);
                if ("on".equals(req.getParameter("remember"))) {
                    javax.servlet.http.Cookie cookie = new javax.servlet.http.Cookie("rememberUser", username);
                    cookie.setMaxAge(7 * 24 * 60 * 60);
                    cookie.setPath(req.getContextPath());
                    resp.addCookie(cookie);
                }
                resp.sendRedirect(redirect);
                return;
            }
            req.setAttribute("error", "Nom d'utilisateur ou mot de passe invalide.");
        } catch (Exception e) {
            req.setAttribute("error", "Erreur de connexion. Réessayez plus tard.");
        }
        req.setAttribute("username", username);
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }
}
