package com.gestionqcm.model;

public class Question {
    private int numQuest;
    private String question;
    private String categorie;
    private String reponse1;
    private String reponse2;
    private String reponse3;
    private String reponse4;
    private String bonne;

    public Question() {}

    public Question(int numQuest, String question, String categorie, String reponse1, String reponse2, String reponse3, String reponse4, String bonne) {
        this.numQuest = numQuest;
        this.question = question;
        this.categorie = categorie;
        this.reponse1 = reponse1;
        this.reponse2 = reponse2;
        this.reponse3 = reponse3;
        this.reponse4 = reponse4;
        this.bonne = bonne;
    }

    public int getNumQuest() { return numQuest; }
    public void setNumQuest(int numQuest) { this.numQuest = numQuest; }
    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }
    public String getCategorie() { return categorie; }
    public void setCategorie(String categorie) { this.categorie = categorie; }
    public String getReponse1() { return reponse1; }
    public void setReponse1(String reponse1) { this.reponse1 = reponse1; }
    public String getReponse2() { return reponse2; }
    public void setReponse2(String reponse2) { this.reponse2 = reponse2; }
    public String getReponse3() { return reponse3; }
    public void setReponse3(String reponse3) { this.reponse3 = reponse3; }
    public String getReponse4() { return reponse4; }
    public void setReponse4(String reponse4) { this.reponse4 = reponse4; }
    public String getBonne() { return bonne; }
    public void setBonne(String bonne) { this.bonne = bonne; }
}
