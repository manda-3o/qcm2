package com.gestionqcm.model;

public class StudentRanking {
    private final String numEtudiant;
    private final String nom;
    private final String prenoms;
    private final String niveau;
    private final int examCount;
    private final float averageScore;

    public StudentRanking(String numEtudiant, String nom, String prenoms, String niveau, int examCount, float averageScore) {
        this.numEtudiant = numEtudiant;
        this.nom = nom;
        this.prenoms = prenoms;
        this.niveau = niveau;
        this.examCount = examCount;
        this.averageScore = averageScore;
    }

    public String getNumEtudiant() { return numEtudiant; }
    public String getNom() { return nom; }
    public String getPrenoms() { return prenoms; }
    public String getNiveau() { return niveau; }
    public int getExamCount() { return examCount; }
    public float getAverageScore() { return averageScore; }
    public String getFullName() { return (nom == null ? "" : nom) + (prenoms == null || prenoms.isBlank() ? "" : " " + prenoms); }
}
