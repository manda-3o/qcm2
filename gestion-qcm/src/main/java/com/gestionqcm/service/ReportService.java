package com.gestionqcm.service;

import com.gestionqcm.dao.EtudiantDAO;
import com.gestionqcm.dao.ExamenDAO;
import com.gestionqcm.dao.QuestionDAO;
import com.gestionqcm.model.DashboardStats;
import com.gestionqcm.model.Etudiant;
import com.gestionqcm.model.Examen;
import com.gestionqcm.model.LevelSummary;
import com.gestionqcm.model.StudentRanking;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

public class ReportService {
    private final EtudiantDAO etudiantDAO = new EtudiantDAO();
    private final ExamenDAO examenDAO = new ExamenDAO();
    private final QuestionDAO questionDAO = new QuestionDAO();

    public DashboardStats loadDashboard() throws SQLException {
        List<Etudiant> etudiants = etudiantDAO.listAll();
        List<Examen> examens = examenDAO.listAll();
        int totalStudents = etudiants.size();
        int totalQuestions = questionDAO.listAll().size();
        int totalExams = examens.size();
        OptionalDouble averageScore = examens.stream().mapToInt(Examen::getNote).average();

        DashboardStats stats = new DashboardStats();
        stats.setTotalStudents(totalStudents);
        stats.setTotalQuestions(totalQuestions);
        stats.setTotalExams(totalExams);
        stats.setAverageScore((float) averageScore.orElse(0.0));
        stats.setLevelCount((int) etudiants.stream().map(Etudiant::getNiveau).filter(n -> n != null && !n.isBlank()).distinct().count());

        List<StudentRanking> ranking = listRanking(1);
        if (!ranking.isEmpty()) {
            stats.setTopStudentName(ranking.get(0).getFullName());
            stats.setTopAverage(ranking.get(0).getAverageScore());
            stats.setWorstStudentName(ranking.get(ranking.size() - 1).getFullName());
            stats.setWorstAverage(ranking.get(ranking.size() - 1).getAverageScore());
        }
        long passed = examens.stream().filter(ex -> ex.getNote() >= 5).count();
        stats.setPassRate(totalExams == 0 ? 0f : (passed * 100f / totalExams));
        return stats;
    }

    public Map<String, Float> averageByLevel() throws SQLException {
        List<Etudiant> etudiants = etudiantDAO.listAll();
        List<Examen> examens = examenDAO.listAll();
        Map<String, String> studentLevel = etudiants.stream()
                .collect(Collectors.toMap(Etudiant::getNumEtudiant, e -> e.getNiveau() == null || e.getNiveau().isBlank() ? "Non renseigné" : e.getNiveau()));

        Map<String, List<Examen>> byLevel = examens.stream()
                .collect(Collectors.groupingBy(e -> studentLevel.getOrDefault(e.getNumEtudiant(), "Non renseigné")));

        Map<String, Float> averages = new LinkedHashMap<>();
        byLevel.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> averages.put(entry.getKey(), (float) entry.getValue().stream().mapToInt(Examen::getNote).average().orElse(0)));
        return averages;
    }

    public Map<Integer, Integer> scoreHistogram() throws SQLException {
        List<Examen> examens = examenDAO.listAll();
        Map<Integer, Integer> histogram = new LinkedHashMap<>();
        for (int i = 0; i <= 10; i++) {
            histogram.put(i, 0);
        }
        for (Examen examen : examens) {
            histogram.computeIfPresent(examen.getNote(), (k, v) -> v + 1);
        }
        return histogram;
    }

    public List<StudentRanking> listRanking(int limit) throws SQLException {
        List<Etudiant> etudiants = etudiantDAO.listAll();
        Map<String, Etudiant> etudiantMap = etudiants.stream().collect(Collectors.toMap(Etudiant::getNumEtudiant, e -> e));
        List<Examen> examens = examenDAO.listAll();

        Map<String, List<Examen>> byStudent = examens.stream().collect(Collectors.groupingBy(Examen::getNumEtudiant));
        List<StudentRanking> ranking = new ArrayList<>();
        for (Map.Entry<String, List<Examen>> entry : byStudent.entrySet()) {
            String numEtudiant = entry.getKey();
            List<Examen> results = entry.getValue();
            double average = results.stream().mapToInt(Examen::getNote).average().orElse(0.0);
            Etudiant etudiant = etudiantMap.get(numEtudiant);
            String nom = etudiant != null ? etudiant.getNom() : "(inconnu)";
            String prenoms = etudiant != null ? etudiant.getPrenoms() : "";
            String niveau = etudiant != null ? etudiant.getNiveau() : "-";
            ranking.add(new StudentRanking(numEtudiant, nom, prenoms, niveau, results.size(), (float) average));
        }

        ranking.sort(Comparator.comparingDouble(StudentRanking::getAverageScore).reversed());
        if (limit > 0 && ranking.size() > limit) {
            return ranking.subList(0, limit);
        }
        return ranking;
    }

    public List<LevelSummary> listLevels() throws SQLException {
        List<Etudiant> etudiants = etudiantDAO.listAll();
        return etudiants.stream()
                .collect(Collectors.groupingBy(e -> e.getNiveau() == null ? "Non renseigné" : e.getNiveau(), Collectors.counting()))
                .entrySet().stream()
                .map(entry -> new LevelSummary(entry.getKey(), entry.getValue().intValue()))
                .sorted(Comparator.comparing(LevelSummary::getNiveau))
                .collect(Collectors.toList());
    }
}
