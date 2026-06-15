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
import java.util.List;

@WebServlet(name = "ClassementServlet", urlPatterns = {"/classement"})
public class ClassementServlet extends HttpServlet {
    private final ReportService reportService = new ReportService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int page = WebUtils.parseIntOrDefault(req.getParameter("page"), 1);
            int pageSize = 10;
            List<StudentRanking> classement = reportService.listRanking(0);
            int total = classement.size();
            int start = Math.max(0, (page - 1) * pageSize);
            int end = Math.min(total, start + pageSize);
            req.setAttribute("classement", classement.subList(start, end));
            req.setAttribute("currentPage", page);
            req.setAttribute("pageCount", (int) Math.ceil(total / (double) pageSize));
        } catch (Exception e) {
            throw new ServletException(e);
        }
        req.getRequestDispatcher("/WEB-INF/views/classement.jsp").forward(req, resp);
    }
}
