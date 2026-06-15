package com.gestionqcm.controller;

import com.gestionqcm.model.StudentRanking;
import com.gestionqcm.service.ReportService;
import com.gestionqcm.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "ApiClassementServlet", urlPatterns = {"/api/classement"})
public class ApiClassementServlet extends HttpServlet {
    private final ReportService reportService = new ReportService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = WebUtils.parseIntOrDefault(req.getParameter("page"), 1);
        int pageSize = WebUtils.parseIntOrDefault(req.getParameter("size"), 20);
        try {
            List<StudentRanking> ranking = reportService.listRanking(0);
            int start = Math.max(0, (page - 1) * pageSize);
            int end = Math.min(ranking.size(), start + pageSize);
            List<StudentRanking> pageData = ranking.subList(start, end);
            resp.setContentType("application/json;charset=UTF-8");
            try (PrintWriter writer = resp.getWriter()) {
                writer.print("[");
                for (int i = 0; i < pageData.size(); i++) {
                    StudentRanking item = pageData.get(i);
                    writer.print("{");
                    writer.print("\"numEtudiant\":\""); writer.print(WebUtils.escapeJson(item.getNumEtudiant())); writer.print("\"");
                    writer.print(",\"fullName\":\""); writer.print(WebUtils.escapeJson(item.getFullName())); writer.print("\"");
                    writer.print(",\"niveau\":\""); writer.print(WebUtils.escapeJson(item.getNiveau())); writer.print("\"");
                    writer.print(",\"examCount\":"); writer.print(item.getExamCount());
                    writer.print(",\"averageScore\":"); writer.print(item.getAverageScore());
                    writer.print("}");
                    if (i < pageData.size() - 1) writer.print(",");
                }
                writer.print("]");
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
