package org.itstep;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.OptionalDouble;

public class Service {

    private List<Integer> averageTime;
    private long startDateInMillis = 0;
    private long endDateInMillis = 0;
    private long dateOfRequestInMillis = 0;
    private SimpleDateFormat date = new SimpleDateFormat("dd.MM.yyyy");
    private Date myDate;
    private String startDate = "";
    private String endDate = "";
    private String dateOfRequest = "";

    public void queryProcessing(List<String> listWithWaitingTime, String query) throws ParseException {

        String waitingTime;
        query = query.substring(2);
        String[] splitedWaitingTime = new String[0];
        String[] splitedQuery = new String[0];
        String checkerOnStar = "";
        String service = "";
        String serviceInQuery = "";
        String questionType = "";
        String questionTypeInQuery = "";
        averageTime = new ArrayList<>();

        OptionalDouble average;

        if(query.contains("P")){
            splitedQuery = query.split( "P");
        } else if (query.contains("N")){
            splitedQuery = query.split( "N");
        }


        for(int j = 0; j < listWithWaitingTime.size(); j++){
            waitingTime = listWithWaitingTime.get(j);

            if (waitingTime.contains("P")){
                splitedWaitingTime = waitingTime.substring(2).split("P");
            } else if (waitingTime.contains("N")){
                splitedWaitingTime = waitingTime.substring(2).split("N");
            }

            service = splitedWaitingTime[0].split(" ")[0];
            serviceInQuery = splitedQuery[0].split(" ")[0];

            if (serviceInQuery.contains("*")){
                checkerOnStar = splitedQuery[0].split(" ")[0].replace("*","");
            } else {
                checkerOnStar = splitedQuery[0].split(" ")[0];
            }


            questionType = splitedWaitingTime[0].split(" ")[1];


            if (splitedQuery[0].split(" ").length == 2 && splitedQuery[0].split(" ").length == splitedWaitingTime[0].split(" ").length) {
                questionTypeInQuery = splitedQuery[0].split(" ")[1];
                if (service.contains(checkerOnStar)) {
                    if (questionType.contains(questionTypeInQuery)){
                        checkDate(splitedQuery, splitedWaitingTime);
                    }
                }
            } else {
                if (service.contains(checkerOnStar)){
                    checkDate(splitedQuery, splitedWaitingTime);
                }
            }
        }

        average = averageTime.stream().mapToDouble(i -> i).average();

        if (!average.isPresent()){
            System.out.println("-");
        } else {
            System.out.println(average);
        }
    }

    public void checkDate(String[] splitedQuery, String[] splitedWaitingTime) throws ParseException {
        if (splitedQuery[1].trim().contains("-")){
            startDate = splitedQuery[1].trim().split("-")[0];
            endDate = splitedQuery[1].trim().split("-")[1];
            myDate = date.parse(startDate);
            startDateInMillis = myDate.getTime();
            myDate = date.parse(endDate);
            endDateInMillis = myDate.getTime();
            dateOfRequest = splitedWaitingTime[1].trim().split(" ")[0];
            myDate = date.parse(dateOfRequest);
            dateOfRequestInMillis = myDate.getTime();

            if (dateOfRequestInMillis >= startDateInMillis && dateOfRequestInMillis <= endDateInMillis){
                averageTime.add(Integer.parseInt(splitedWaitingTime[1].trim().split(" ")[1]));
            }
        } else {
            if (splitedQuery[1].trim().equals(splitedWaitingTime[1].trim().split(" ")[0])){
                averageTime.add(Integer.parseInt(splitedWaitingTime[1].trim().split(" ")[1]));
            }
        }
    }
}
