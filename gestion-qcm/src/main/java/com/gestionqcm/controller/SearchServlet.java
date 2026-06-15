package com.gestionqcm.controller;

import com.gestionqcm.model.Etudiant;
import com.gestionqcm.service.EtudiantService;
import com.gestionqcm.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "SearchServlet", urlPatterns = {"/api/etudiants/search"})
public class SearchServlet extends HttpServlet {
    private final EtudiantService etudiantService = new EtudiantService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String keyword = req.getParameter("keyword");
        try {
            List<Etudiant> results = etudiantService.search(keyword);
            resp.setContentType("application/json;charset=UTF-8");
            try (PrintWriter writer = resp.getWriter()) {
                writer.print("[");
                for (int i = 0; i < results.size(); i++) {
                    Etudiant e = results.get(i);
                    writer.print("{");
                    writer.print("\"numEtudiant\":\"");
                    writer.print(WebUtils.escapeJson(e.getNumEtudiant()));
                    writer.print("\"");
                    writer.print(",\"nom\":\"");
                    writer.print(WebUtils.escapeJson(e.getNom()));
                    writer.print("\"");
                    writer.print(",\"prenoms\":\"");
                    writer.print(WebUtils.escapeJson(e.getPrenoms()));
                    writer.print("\"");
                    writer.print(",\"niveau\":\"");
                    writer.print(WebUtils.escapeJson(e.getNiveau()));
                    writer.print("\"");
                    writer.print(",\"adrEmail\":\"");
                    writer.print(WebUtils.escapeJson(e.getAdrEmail()));
                    writer.print("\"");
                    writer.print("}");
                    if (i < results.size() - 1) writer.print(",");
                }
                writer.print("]");
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
