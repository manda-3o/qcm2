package com.gestionqcm.controller;

import com.gestionqcm.service.EmailService;
import com.gestionqcm.service.ReportService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "EmailServlet", urlPatterns = {"/email/send"})
public class EmailServlet extends HttpServlet {
    private final EmailService emailService = new EmailService();
    private final ReportService reportService = new ReportService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String target = System.getenv().getOrDefault("MAIL_TO", "");
        if (target.isBlank()) {
            resp.sendRedirect(req.getContextPath() + "/dashboard?toastType=warning&toastMessage=Email+non+configur%C3%A9");
            return;
        }

        try {
            var stats = reportService.loadDashboard();
            var classement = reportService.listRanking(10);
            StringBuilder body = new StringBuilder();
            body.append("<h2>Rapport GestionQCM</h2>");
            body.append("<p>Total étudiants: " + stats.getTotalStudents() + "</p>");
            body.append("<p>Total examens: " + stats.getTotalExams() + "</p>");
            body.append("<p>Moyenne générale: " + stats.getAverageScore() + "</p>");
            body.append("<p>Top étudiant: " + stats.getTopStudentName() + "</p>");
            body.append("<h3>Top classement</h3>");
            body.append("<ul>");
            for (var item : classement) {
                body.append("<li>").append(item.getFullName()).append(" - moyenne: ").append(item.getAverageScore()).append("</li>");
            }
            body.append("</ul>");
            emailService.sendEmail(target, "Rapport GestionQCM", body.toString());
            resp.sendRedirect(req.getContextPath() + "/dashboard?toastType=success&toastMessage=Email+envoy%C3%A9");
        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/dashboard?toastType=danger&toastMessage=Erreur+envoi+email");
        }
    }
}
