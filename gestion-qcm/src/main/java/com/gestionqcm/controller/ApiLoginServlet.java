package com.gestionqcm.controller;

import com.gestionqcm.service.AdminService;
import com.gestionqcm.utils.JwtUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet(name = "ApiLoginServlet", urlPatterns = {"/api/login"})
public class ApiLoginServlet extends HttpServlet {
    private final AdminService adminService = new AdminService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String body = readBody(req);
        String username = extractJsonValue(body, "username");
        String password = extractJsonValue(body, "password");
        resp.setContentType("application/json;charset=UTF-8");
        try {
            if (adminService.authenticate(username, password)) {
                String token = JwtUtils.generateToken(username);
                resp.getWriter().write("{\"token\":\"" + token + "\"}");
            } else {
                resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
                resp.getWriter().write("{\"error\":\"Invalid credentials\"}");
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"Server error\"}");
        }
    }

    private String readBody(HttpServletRequest req) throws IOException {
        StringBuilder body = new StringBuilder();
        try (BufferedReader reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                body.append(line);
            }
        }
        return body.toString();
    }

    private String extractJsonValue(String body, String key) {
        if (body == null || key == null) {
            return "";
        }
        String pattern = "\"" + key + "\"\s*:\s*\"";
        int start = body.indexOf(pattern);
        if (start < 0) {
            return "";
        }
        start += pattern.length();
        int end = body.indexOf('"', start);
        if (end < 0) {
            return "";
        }
        return body.substring(start, end);
    }
}
