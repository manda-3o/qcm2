package com.gestionqcm.controller;

import com.gestionqcm.model.Etudiant;
import com.gestionqcm.model.Examen;
import com.gestionqcm.model.StudentRanking;
import com.gestionqcm.service.EtudiantService;
import com.gestionqcm.service.ExamService;
import com.gestionqcm.service.ReportService;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ExportExcelServlet", urlPatterns = {"/export/excel"})
public class ExportExcelServlet extends HttpServlet {
    private final EtudiantService etudiantService = new EtudiantService();
    private final ExamService examService = new ExamService();
    private final ReportService reportService = new ReportService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        resp.setHeader("Content-Disposition", "attachment; filename=GestionQCM_Export.xlsx");

        try (Workbook workbook = new XSSFWorkbook()) {
            createStudentSheet(workbook);
            createExamSheet(workbook);
            createRankingSheet(workbook);
            createStatsSheet(workbook);
            workbook.write(resp.getOutputStream());
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void createStudentSheet(Workbook workbook) throws Exception {
        Sheet sheet = workbook.createSheet("Étudiants");
        CellStyle header = workbook.createCellStyle();
        header.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        header.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Row row = sheet.createRow(0);
        String[] headers = {"Numéro", "Nom", "Prénoms", "Niveau", "Email", "Photo"};
        for (int i = 0; i < headers.length; i++) {
            var cell = row.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(header);
        }
        List<Etudiant> etudiants = etudiantService.listAll();
        for (int i = 0; i < etudiants.size(); i++) {
            Etudiant e = etudiants.get(i);
            Row data = sheet.createRow(i + 1);
            data.createCell(0).setCellValue(e.getNumEtudiant());
            data.createCell(1).setCellValue(e.getNom());
            data.createCell(2).setCellValue(e.getPrenoms());
            data.createCell(3).setCellValue(e.getNiveau());
            data.createCell(4).setCellValue(e.getAdrEmail());
            data.createCell(5).setCellValue(e.getPhoto() == null ? "" : e.getPhoto());
        }
        for (int i = 0; i < headers.length; i++) sheet.autoSizeColumn(i);
    }

    private void createExamSheet(Workbook workbook) throws Exception {
        Sheet sheet = workbook.createSheet("Examens");
        CellStyle header = workbook.createCellStyle();
        header.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        header.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Row row = sheet.createRow(0);
        String[] headers = {"Numéro Examen", "Étudiant", "Année", "Note", "Durée", "Statut"};
        for (int i = 0; i < headers.length; i++) {
            var cell = row.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(header);
        }
        List<Examen> examens = examService.listAll();
        for (int i = 0; i < examens.size(); i++) {
            Examen ex = examens.get(i);
            Row data = sheet.createRow(i + 1);
            data.createCell(0).setCellValue(ex.getNumExam());
            data.createCell(1).setCellValue(ex.getNumEtudiant());
            data.createCell(2).setCellValue(ex.getAnneeUniv());
            data.createCell(3).setCellValue(ex.getNote());
            data.createCell(4).setCellValue(ex.getDuree());
            data.createCell(5).setCellValue(ex.getStatut());
        }
        for (int i = 0; i < headers.length; i++) sheet.autoSizeColumn(i);
    }

    private void createRankingSheet(Workbook workbook) throws Exception {
        Sheet sheet = workbook.createSheet("Classement");
        CellStyle header = workbook.createCellStyle();
        header.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        header.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Row row = sheet.createRow(0);
        String[] headers = {"Rang", "Étudiant", "Niveau", "Examens", "Moyenne"};
        for (int i = 0; i < headers.length; i++) {
            var cell = row.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(header);
        }
        List<StudentRanking> ranking = reportService.listRanking(0);
        for (int i = 0; i < ranking.size(); i++) {
            StudentRanking item = ranking.get(i);
            Row data = sheet.createRow(i + 1);
            data.createCell(0).setCellValue(i + 1);
            data.createCell(1).setCellValue(item.getFullName());
            data.createCell(2).setCellValue(item.getNiveau());
            data.createCell(3).setCellValue(item.getExamCount());
            data.createCell(4).setCellValue(item.getAverageScore());
        }
        for (int i = 0; i < headers.length; i++) sheet.autoSizeColumn(i);
    }

    private void createStatsSheet(Workbook workbook) throws Exception {
        Sheet sheet = workbook.createSheet("Statistiques");
        CellStyle header = workbook.createCellStyle();
        header.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        header.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Row row = sheet.createRow(0);
        String[] headers = {"Clé", "Valeur"};
        for (int i = 0; i < headers.length; i++) {
            var cell = row.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(header);
        }
        var stats = reportService.loadDashboard();
        String[][] values = {
                {"Total étudiants", String.valueOf(stats.getTotalStudents())},
                {"Total questions", String.valueOf(stats.getTotalQuestions())},
                {"Total examens", String.valueOf(stats.getTotalExams())},
                {"Moyenne générale", String.valueOf(stats.getAverageScore())},
                {"Taux de réussite (%)", String.format("%.1f", stats.getPassRate())},
                {"Meilleur étudiant", stats.getTopStudentName()},
                {"Pire étudiant", stats.getWorstStudentName()}
        };
        for (int i = 0; i < values.length; i++) {
            Row data = sheet.createRow(i + 1);
            data.createCell(0).setCellValue(values[i][0]);
            data.createCell(1).setCellValue(values[i][1]);
        }
        for (int i = 0; i < headers.length; i++) sheet.autoSizeColumn(i);
    }
}
