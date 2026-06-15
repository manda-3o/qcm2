package com.gestionqcm.controller;

import com.gestionqcm.model.Question;
import com.gestionqcm.service.QuestionService;
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

@WebServlet(name = "ApiQuestionServlet", urlPatterns = {"/api/questions"})
public class ApiQuestionServlet extends HttpServlet {
    private final QuestionService questionService = new QuestionService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = WebUtils.parseIntOrDefault(req.getParameter("page"), 1);
        int pageSize = WebUtils.parseIntOrDefault(req.getParameter("size"), 20);
        try {
            List<Question> results = questionService.listPage(page, pageSize);
            resp.setContentType("application/json;charset=UTF-8");
            try (PrintWriter writer = resp.getWriter()) {
                writer.print("[");
                for (int i = 0; i < results.size(); i++) {
                    Question q = results.get(i);
                    writer.print("{");
                    writer.print("\"numQuest\":"); writer.print(q.getNumQuest());
                    writer.print(",\"question\":\""); writer.print(WebUtils.escapeJson(q.getQuestion())); writer.print("\"");
                    writer.print(",\"categorie\":\""); writer.print(WebUtils.escapeJson(q.getCategorie())); writer.print("\"");
                    writer.print(",\"reponse1\":\""); writer.print(WebUtils.escapeJson(q.getReponse1())); writer.print("\"");
                    writer.print(",\"reponse2\":\""); writer.print(WebUtils.escapeJson(q.getReponse2())); writer.print("\"");
                    writer.print(",\"reponse3\":\""); writer.print(WebUtils.escapeJson(q.getReponse3())); writer.print("\"");
                    writer.print(",\"reponse4\":\""); writer.print(WebUtils.escapeJson(q.getReponse4())); writer.print("\"");
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
        int numQuest = WebUtils.parseIntOrDefault(extractJsonValue(body, "numQuest"), 0);
        String question = extractJsonValue(body, "question");
        String categorie = extractJsonValue(body, "categorie");
        String reponse1 = extractJsonValue(body, "reponse1");
        String reponse2 = extractJsonValue(body, "reponse2");
        String reponse3 = extractJsonValue(body, "reponse3");
        String reponse4 = extractJsonValue(body, "reponse4");
        String bonne = extractJsonValue(body, "bonne");
        try {
            Question q = new Question(numQuest, question, categorie, reponse1, reponse2, reponse3, reponse4, bonne);
            questionService.save(q);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write("{\"success\":true}");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Unable to create question\"}");
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
