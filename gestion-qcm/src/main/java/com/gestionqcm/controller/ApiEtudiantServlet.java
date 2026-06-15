package com.gestionqcm.controller;

import com.gestionqcm.model.Etudiant;
import com.gestionqcm.service.EtudiantService;
import com.gestionqcm.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "ApiEtudiantServlet", urlPatterns = {"/api/etudiants"})
public class ApiEtudiantServlet extends HttpServlet {
    private final EtudiantService etudiantService = new EtudiantService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String keyword = req.getParameter("keyword");
        int page = WebUtils.parseIntOrDefault(req.getParameter("page"), 1);
        int pageSize = WebUtils.parseIntOrDefault(req.getParameter("size"), 20);
        try {
            List<Etudiant> results;
            if (keyword != null && !keyword.isBlank()) {
                results = etudiantService.search(keyword);
            } else {
                results = etudiantService.listPage(page, pageSize);
            }
            resp.setContentType("application/json;charset=UTF-8");
            try (PrintWriter writer = resp.getWriter()) {
                writer.print("[");
                for (int i = 0; i < results.size(); i++) {
                    Etudiant e = results.get(i);
                    writer.print("{");
                    writer.print("\"numEtudiant\":\""); writer.print(WebUtils.escapeJson(e.getNumEtudiant())); writer.print("\"");
                    writer.print(",\"nom\":\""); writer.print(WebUtils.escapeJson(e.getNom())); writer.print("\"");
                    writer.print(",\"prenoms\":\""); writer.print(WebUtils.escapeJson(e.getPrenoms())); writer.print("\"");
                    writer.print(",\"niveau\":\""); writer.print(WebUtils.escapeJson(e.getNiveau())); writer.print("\"");
                    writer.print(",\"adrEmail\":\""); writer.print(WebUtils.escapeJson(e.getAdrEmail())); writer.print("\"");
                    writer.print(",\"photo\":\""); writer.print(WebUtils.escapeJson(e.getPhoto())); writer.print("\"");
                    writer.print("}");
                    if (i < results.size() - 1) writer.print(",");
                }
                writer.print("]");
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String body = readBody(req);
        String numEtudiant = extractJsonValue(body, "numEtudiant");
        String nom = extractJsonValue(body, "nom");
        String prenoms = extractJsonValue(body, "prenoms");
        String niveau = extractJsonValue(body, "niveau");
        String adrEmail = extractJsonValue(body, "adrEmail");
        try {
            Etudiant etudiant = new Etudiant(numEtudiant, nom, prenoms, niveau, adrEmail);
            etudiantService.save(etudiant);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write("{\"success\":true}");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Unable to create student\"}");
        }
    }

    private String readBody(HttpServletRequest req) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        return sb.toString();
    }

    private String extractJsonValue(String body, String key) {
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
