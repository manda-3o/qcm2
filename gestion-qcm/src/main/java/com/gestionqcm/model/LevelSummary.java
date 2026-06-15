package com.gestionqcm.model;

public class LevelSummary {
    private final String niveau;
    private final int studentCount;

    public LevelSummary(String niveau, int studentCount) {
        this.niveau = niveau;
        this.studentCount = studentCount;
    }

    public String getNiveau() { return niveau; }
    public int getStudentCount() { return studentCount; }
}
