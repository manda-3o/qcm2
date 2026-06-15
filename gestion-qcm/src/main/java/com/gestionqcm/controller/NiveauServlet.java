package com.gestionqcm.controller;

import com.gestionqcm.model.Etudiant;
import com.gestionqcm.model.LevelSummary;
import com.gestionqcm.service.EtudiantService;
import com.gestionqcm.service.ReportService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "NiveauServlet", urlPatterns = {"/niveaux"})
public class NiveauServlet extends HttpServlet {
    private final EtudiantService etudiantService = new EtudiantService();
    private final ReportService reportService = new ReportService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String niveau = req.getParameter("niveau");
            List<Etudiant> etudiants = etudiantService.listByLevel(niveau);
            List<LevelSummary> levels = reportService.listLevels();
            req.setAttribute("etudiants", etudiants);
            req.setAttribute("levels", levels);
            req.setAttribute("selectedLevel", niveau);
        } catch (Exception e) {
            throw new ServletException(e);
        }
        req.getRequestDispatcher("/WEB-INF/views/niveaux.jsp").forward(req, resp);
    }
}
