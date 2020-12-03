package com.yiying;

import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;

public class MyBot extends TelegramLongPollingBot {

    String studentName;

    @Override
    public String getBotUsername() {
        return "s262451_bot";
    }

    @Override
    public String getBotToken() {
        return "1409983101:AAGfQTl88oUos998sLl7WuqLn0EDwkmhUTk";
    }

    @Override
    public void onUpdateReceived(Update update) {

        int totalNumOfStudent = GetInfoGithub.studentInfo.length;

        String command = update.getMessage().getText();

        //To show the name and the command gave by the user
        System.out.println("Command: " +command);
        System.out.println("User: " + update.getMessage().getFrom().getFirstName());
        System.out.println();

        SendMessage sendMessage = new SendMessage();
        String replyMessage = "";
        String fullname = update.getMessage().getFrom().getFirstName() + " " + update.getMessage().getFrom().getLastName();


        //User input /start, output:
        if (command.equals("/start")){
            replyMessage =
                    "Good day, " + fullname + ". \n"
                    + "Please enter a matric number to check for the details.\n";

            System.out.println("command /start received");
        //User input the correct matric number, output:
        } else if(command != null && command.length() == 6 && isNumber(command)){
            //To get the student's name by using matric number
            for (int i = 0; i < totalNumOfStudent; i++) {
                if (isFound(i, totalNumOfStudent, command) && command.equals(GetInfoGithub.studentInfo[i].getMatricNum())){

                    studentName = GetInfoGithub.studentInfo[i].getName();
                    String studentNameUp = studentName.toUpperCase();
                    Map<Character, Integer> map = new HashMap<>();

                    //setup map
                    for (int j = 0; j < studentNameUp.length(); j++){
                        int counter = StringUtils.countMatches(studentNameUp, studentNameUp.charAt(j));
                        if (counter >= 3 && studentNameUp.charAt(j) != ' '){
                            map.put(studentNameUp.toUpperCase().charAt(j), counter);
                        }
                    }

                    String output = "";

                    //print map
                    if (map.isEmpty()){
                        output = "The number of each letter in this name are occur less than 3 times.";
                    }else {
                        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
                            output = output + entry.getKey() + " = " + entry.getValue() + "\n";
                        }
                    }

                    replyMessage = "Total Students: " + totalNumOfStudent + "\n"
                            + command + " --> " + studentName + "\n"
                            + "Length: " + studentName.length() + "\n\n"
                            + output;

                    System.out.println(
                            "Total Students: " + totalNumOfStudent + "\n"
                                    + command + " --> " + studentName + "\n\n"
                                    + "Length: " + studentName.length()
                                    + output
                    );
                    break;

                }else{
                    replyMessage = "Sorry, your matric number " + command +" is not found. Please input again.";
                }
            }
        } else{
            replyMessage = "Incorrect format. \nPlease input a valid matric number.\n"
                        + "E.g. 262451";
            System.out.println("This is not a matric number. Please input a valid matric number.\nE.g. 262451");
        }

        sendMessage.setText(replyMessage);
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    //to check whether the input is the matric number of student in class
    public boolean isFound(int i, int num, String command) {
        for (; i < num; i++) {
            if (command.equals(GetInfoGithub.studentInfo[i].getMatricNum()))
                return true;
        }
        return false;
    }

    //To check whether the input is a number or not
    public boolean isNumber(String command){
        try {
            double matricNum = Double.parseDouble(command);
        }catch (NumberFormatException e){
            return false;
        }
        return true;
    }
}
