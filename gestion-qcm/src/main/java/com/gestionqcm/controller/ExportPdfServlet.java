package com.gestionqcm.controller;

import com.gestionqcm.model.Examen;
import com.gestionqcm.service.ExamService;
import com.gestionqcm.service.ReportService;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ExportPdfServlet", urlPatterns = {"/examens/export"})
public class ExportPdfServlet extends HttpServlet {
    private final ExamService examService = new ExamService();
    private final ReportService reportService = new ReportService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/pdf");
        resp.setHeader("Content-Disposition", "attachment; filename=GestionQCM_Resultats.pdf");

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, resp.getOutputStream());
            document.open();

            Font title = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, new BaseColor(31, 42, 86));
            Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);
            Font normal = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL, BaseColor.BLACK);

            Paragraph logo = new Paragraph("GestionQCM", title);
            logo.setAlignment(Element.ALIGN_LEFT);
            document.add(logo);
            document.add(new Paragraph("Rapport des examens et statistiques", normal));
            document.add(new Paragraph(" "));

            var stats = reportService.loadDashboard();
            PdfPTable statTable = new PdfPTable(2);
            statTable.setWidthPercentage(100);
            statTable.addCell(new PdfPCell(new Phrase("Étudiants", headerFont)));
            statTable.addCell(new PdfPCell(new Phrase(String.valueOf(stats.getTotalStudents()), normal)));
            statTable.addCell(new PdfPCell(new Phrase("Questions", headerFont)));
            statTable.addCell(new PdfPCell(new Phrase(String.valueOf(stats.getTotalQuestions()), normal)));
            statTable.addCell(new PdfPCell(new Phrase("Examens", headerFont)));
            statTable.addCell(new PdfPCell(new Phrase(String.valueOf(stats.getTotalExams()), normal)));
            statTable.addCell(new PdfPCell(new Phrase("Moyenne générale", headerFont)));
            statTable.addCell(new PdfPCell(new Phrase(String.valueOf(stats.getAverageScore()), normal)));
            document.add(statTable);
            document.add(new Paragraph(" "));

            List<Examen> exams = examService.listAll();
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.addCell(new PdfPCell(new Phrase("Étudiant", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Note", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Année", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Durée(s)", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Statut", headerFont)));
            for (Examen ex : exams) {
                table.addCell(new PdfPCell(new Phrase(ex.getNumEtudiant(), normal)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(ex.getNote()), normal)));
                table.addCell(new PdfPCell(new Phrase(ex.getAnneeUniv(), normal)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(ex.getDuree()), normal)));
                table.addCell(new PdfPCell(new Phrase(ex.getStatut(), normal)));
            }
            document.add(table);
            document.close();
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
