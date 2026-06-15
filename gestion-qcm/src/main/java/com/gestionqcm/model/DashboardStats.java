package com.gestionqcm.model;

public class DashboardStats {
    private int totalStudents;
    private int totalQuestions;
    private int totalExams;
    private float averageScore;
    private String topStudentName;
    private float topAverage;
    private String worstStudentName;
    private float worstAverage;
    private float passRate;
    private int levelCount;

    public int getTotalStudents() { return totalStudents; }
    public void setTotalStudents(int totalStudents) { this.totalStudents = totalStudents; }
    public int getTotalQuestions() { return totalQuestions; }
    public void setTotalQuestions(int totalQuestions) { this.totalQuestions = totalQuestions; }
    public int getTotalExams() { return totalExams; }
    public void setTotalExams(int totalExams) { this.totalExams = totalExams; }
    public float getAverageScore() { return averageScore; }
    public void setAverageScore(float averageScore) { this.averageScore = averageScore; }
    public String getTopStudentName() { return topStudentName; }
    public void setTopStudentName(String topStudentName) { this.topStudentName = topStudentName; }
    public float getTopAverage() { return topAverage; }
    public void setTopAverage(float topAverage) { this.topAverage = topAverage; }
    public String getWorstStudentName() { return worstStudentName; }
    public void setWorstStudentName(String worstStudentName) { this.worstStudentName = worstStudentName; }
    public float getWorstAverage() { return worstAverage; }
    public void setWorstAverage(float worstAverage) { this.worstAverage = worstAverage; }
    public float getPassRate() { return passRate; }
    public void setPassRate(float passRate) { this.passRate = passRate; }
    public int getLevelCount() { return levelCount; }
    public void setLevelCount(int levelCount) { this.levelCount = levelCount; }
}
