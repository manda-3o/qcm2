package com.gestionqcm.controller;

import com.gestionqcm.model.Etudiant;
import com.gestionqcm.service.EtudiantService;
import com.gestionqcm.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@WebServlet(name = "EtudiantServlet", urlPatterns = {"/etudiants"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 6 * 1024 * 1024)
public class EtudiantServlet extends HttpServlet {
    private final EtudiantService etudiantService = new EtudiantService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int page = WebUtils.parseIntOrDefault(req.getParameter("page"), 1);
            int pageSize = 10;
            int total = etudiantService.countTotal();
            req.setAttribute("etudiants", etudiantService.listPage(page, pageSize));
            req.setAttribute("totalStudents", total);
            req.setAttribute("currentPage", page);
            req.setAttribute("pageSize", pageSize);
            req.setAttribute("pageCount", (int) Math.ceil(total / (double) pageSize));
        } catch (Exception e) {
            throw new ServletException(e);
        }
        req.getRequestDispatcher("/WEB-INF/views/etudiants.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String id = req.getParameter("num_etudiant");
        try {
            if ("delete".equals(action)) {
                etudiantService.delete(id);
            } else {
                String photoPath = null;
                Part photoPart = req.getPart("photo");
                if (photoPart != null && photoPart.getSize() > 0) {
                    photoPath = storePhoto(req, photoPart);
                }
                Etudiant e = new Etudiant(id, req.getParameter("nom"), req.getParameter("prenoms"), req.getParameter("niveau"), req.getParameter("adr_email"));
                e.setPhoto(photoPath);
                etudiantService.save(e);
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
        resp.sendRedirect(req.getContextPath() + "/etudiants");
    }

    private String storePhoto(HttpServletRequest req, Part photoPart) throws IOException {
        String filename = Path.of(photoPart.getSubmittedFileName()).getFileName().toString();
        String extension = filename.contains(".") ? filename.substring(filename.lastIndexOf('.')) : "";
        String normalized = UUID.randomUUID().toString() + extension;
        Path uploads = Path.of(req.getServletContext().getRealPath("/uploads"));
        Files.createDirectories(uploads);
        Path target = uploads.resolve(normalized);
        photoPart.write(target.toString());
        return req.getContextPath() + "/uploads/" + normalized;
    }
}
