package com.yiying;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class GetInfoGithub {

    //Total number of student
    public static int numOfStudent = 0;

    //Array to store all students info
    public static StudentInfo[] studentInfo;

    //Method to get name and matric number from the link
    public static void getNameMatric() throws IOException {
        //To get the list of students from the github URL
        String studentURL = "https://github.com/STIW3054-A201/Main-Data/wiki/List_of_Student";
        Document studentDoc = Jsoup.connect(studentURL).get();
        Element bodyOfStudentURL = studentDoc.body();
        Elements student = bodyOfStudentURL.getElementsByTag("tr");

        numOfStudent = student.size() - 1;
        studentInfo = new StudentInfo[numOfStudent];

        //To get each student's name and matric number
        for (int i = 0; i < student.size(); i++) {

            if (i > 0) {
                Element eachStudent = student.get(i);
                Elements name = eachStudent.select("td:eq(2)");
                Elements matricNum = eachStudent.select("td:eq(1)");

                studentInfo[i - 1] = new StudentInfo(name.text(), matricNum.text());
            }
        }
    }
}
