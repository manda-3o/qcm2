package com.gestionqcm.model;

public class Examen {
    private String numExam;
    private String numEtudiant;
    private String anneeUniv;
    private int note;
    private int duree;
    private String statut;

    public Examen() {}

    public Examen(String numExam, String numEtudiant, String anneeUniv, int note) {
        this(numExam, numEtudiant, anneeUniv, note, 0, "non démarré");
    }

    public Examen(String numExam, String numEtudiant, String anneeUniv, int note, int duree, String statut) {
        this.numExam = numExam;
        this.numEtudiant = numEtudiant;
        this.anneeUniv = anneeUniv;
        this.note = note;
        this.duree = duree;
        this.statut = statut;
    }

    public String getNumExam() { return numExam; }
    public void setNumExam(String numExam) { this.numExam = numExam; }
    public String getNumEtudiant() { return numEtudiant; }
    public void setNumEtudiant(String numEtudiant) { this.numEtudiant = numEtudiant; }
    public String getAnneeUniv() { return anneeUniv; }
    public void setAnneeUniv(String anneeUniv) { this.anneeUniv = anneeUniv; }
    public int getNote() { return note; }
    public void setNote(int note) { this.note = note; }
    public int getDuree() { return duree; }
    public void setDuree(int duree) { this.duree = duree; }
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
}
