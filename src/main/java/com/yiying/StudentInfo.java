package com.yiying;

public class StudentInfo {
    private String name = "";
    private String matricNum = "";

    StudentInfo(String name, String matricNum) {
        this.name = name;
        this.matricNum = matricNum;
    }

    public String getName() {
        return name;
    }

    public String getMatricNum() {
        return matricNum;
    }

}
