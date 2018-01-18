package com.vudn.ailatrieuphu.model;

/**
 * Created by admin on 17/12/2017.
 */

public class Question {
    private String question;
    private String caseA;
    private String caseB;
    private String caseC;
    private String caseD;
    private int trueCase;

    public Question(){

    }

    public Question(String question,
                    String caseA, String caseB, String caseC, String caseD, int trueCase) {
        this.question = question;
        this.caseA = caseA;
        this.caseB = caseB;
        this.caseC = caseC;
        this.caseD = caseD;
        this.trueCase = trueCase;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCaseA() {
        return caseA;
    }

    public void setCaseA(String caseA) {
        this.caseA = caseA;
    }

    public String getCaseB() {
        return caseB;
    }

    public void setCaseB(String caseB) {
        this.caseB = caseB;
    }

    public String getCaseC() {
        return caseC;
    }

    public void setCaseC(String caseC) {
        this.caseC = caseC;
    }

    public String getCaseD() {
        return caseD;
    }

    public void setCaseD(String caseD) {
        this.caseD = caseD;
    }

    public int getTrueCase() {
        return trueCase;
    }

    public void setTrueCase(int trueCase) {
        this.trueCase = trueCase;
    }
}
