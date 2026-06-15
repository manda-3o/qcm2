package com.gestionqcm.controller;

import com.gestionqcm.model.Examen;
import com.gestionqcm.service.ExamService;
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

@WebServlet(name = "ApiExamenServlet", urlPatterns = {"/api/examens"})
public class ApiExamenServlet extends HttpServlet {
    private final ExamService examService = new ExamService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = WebUtils.parseIntOrDefault(req.getParameter("page"), 1);
        int pageSize = WebUtils.parseIntOrDefault(req.getParameter("size"), 20);
        try {
            List<Examen> examens = examService.listPage(page, pageSize);
            resp.setContentType("application/json;charset=UTF-8");
            try (PrintWriter writer = resp.getWriter()) {
                writer.print("[");
                for (int i = 0; i < examens.size(); i++) {
                    Examen e = examens.get(i);
                    writer.print("{");
                    writer.print("\"numExam\":\""); writer.print(WebUtils.escapeJson(e.getNumExam())); writer.print("\"");
                    writer.print(",\"numEtudiant\":\""); writer.print(WebUtils.escapeJson(e.getNumEtudiant())); writer.print("\"");
                    writer.print(",\"anneeUniv\":\""); writer.print(WebUtils.escapeJson(e.getAnneeUniv())); writer.print("\"");
                    writer.print(",\"note\":"); writer.print(e.getNote());
                    writer.print(",\"duree\":"); writer.print(e.getDuree());
                    writer.print(",\"statut\":\""); writer.print(WebUtils.escapeJson(e.getStatut())); writer.print("\"");
                    writer.print("}");
                    if (i < examens.size() - 1) writer.print(",");
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
        String anneeUniv = extractJsonValue(body, "anneeUniv");
        int note = WebUtils.parseIntOrDefault(extractJsonValue(body, "note"), 0);
        int duree = WebUtils.parseIntOrDefault(extractJsonValue(body, "duree"), 0);
        String statut = extractJsonValue(body, "statut");
        try {
            Examen examen = new Examen("X" + System.currentTimeMillis(), numEtudiant, anneeUniv, note, duree, statut.isBlank() ? "terminé" : statut);
            examService.save(examen);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write("{\"success\":true}");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Unable to create exam result\"}");
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
