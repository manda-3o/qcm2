package com.gestionqcm.controller;

import com.gestionqcm.service.ReportService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "HomeServlet", urlPatterns = {"/", "/dashboard"})
public class HomeServlet extends HttpServlet {
    private final ReportService reportService = new ReportService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("dashboard", reportService.loadDashboard());
            req.setAttribute("ranking", reportService.listRanking(5));
            req.setAttribute("averageByLevel", reportService.averageByLevel());
            req.setAttribute("scoreHistogram", reportService.scoreHistogram());
        } catch (Exception e) {
            throw new ServletException(e);
        }
        req.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(req, resp);
    }
}
