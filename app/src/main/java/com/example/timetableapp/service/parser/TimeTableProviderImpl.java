package com.example.timetableapp.service.parser;

import com.example.timetableapp.Lesson;
import com.example.timetableapp.util.WeekState;
import org.jsoup.nodes.Element;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.WeekFields;
import java.util.*;

public class TimeTableProviderImpl implements TimeTableProvider{

    TimeTableParser timeTableParser;

    public String getTimetableByWeek(String group,WeekState weekState) {
        Element table = timeTableParser.getTimeTableByGroup(group, weekState);
        List<Element> elements = table.getElementsByTag("tr");

        Map<String, Map<String, String>> lessons = new LinkedHashMap<>();

        List<String> days = elements.get(0).children().eachText();

        for (int i = 0; i < days.size(); i++) {
            if (days.get(i).contains(" ")) {
                days.set(i, days.get(i).split(" ")[0]);
            }
            lessons.put(days.get(i), new LinkedHashMap<>());
        }

        elements.remove(0);
        int index = 1;
        StringBuilder timetable = new StringBuilder();

        for (Map.Entry<String, Map<String, String>> entry: lessons.entrySet()) {

            timetable.append('*').append(entry.getKey()).append('*').append("\n");

            for (int i = 0; i < elements.size(); i++) {
                String time = elements.get(i).children().first().child(0).text();
                List<String> cellText = elements.get(i).children().get(index).children().eachText();

                if (!cellText.isEmpty()) {
                    timetable.append(" ").append(time).append("\n");

                    for (int j = 0; j < cellText.size(); j++) {
                        if (cellText.get(j).endsWith(".")) {
                            timetable.append(" ").append(cellText.get(j)).append("\n");
                        }
                        else if(cellText.get(j).split(" ")[0].equals("Аудитория")) {
                            timetable.append(" ").append(cellText.get(j)).append("\n");
                        }
                        else if(cellText.get(j).split(" ")[0].length() == 7 && cellText.get(j).split(" ")[0].charAt(3) == '-') {
                            timetable.append(" ").append(cellText.get(j)).append("\n");
                        }
                        else {
                            timetable.append(cellText.get(j)).append("\n");
                        }
                    }

                    timetable.append("\n");
                }
            }

            timetable.append("-------------------").append("\n");

            index++;
        }
        return timetable.toString();
    }

    private Map<String, Map<String, String>> parseTimetableByWeek(String group, WeekState weekState) {
        Element table = timeTableParser.getTimeTableByGroup(group, weekState);
        List<Element> elements = table.getElementsByTag("tr");

        Map<String, Map<String, String>> lessons = new LinkedHashMap<>();

        List<String> days = elements.get(0).children().eachText();

        for (int i = 0; i < days.size(); i++) {
            if (days.get(i).contains(" ")) {
                days.set(i, days.get(i).split(" ")[0]);
            }
            lessons.put(days.get(i), new LinkedHashMap<>());
        }

        elements.remove(0);
        int index = 1;

        for (Map.Entry<String, Map<String, String>> entry: lessons.entrySet()) {


            for (int i = 0; i < elements.size(); i++) {
                String time = elements.get(i).children().first().child(0).text();
                List<String> cellText = elements.get(i).children().get(index).children().eachText();

                StringBuilder splitText = new StringBuilder();

                for (int j = 0; j < cellText.size(); j++) {
                    if (cellText.get(j).endsWith(".")) {
                        splitText.append(" ").append(cellText.get(j)).append("\n");
                    }
                    else if(cellText.get(j).split(" ")[0].equals("Аудитория")) {
                        splitText.append(" ").append(cellText.get(j)).append("\n");
                    }
                    else if(cellText.get(j).split(" ")[0].length() == 7 && cellText.get(j).split(" ")[0].charAt(3) == '-') {
                        splitText.append(" ").append(cellText.get(j)).append("\n");
                    }
                    else {
                        splitText.append(cellText.get(j)).append("\n");
                    }
                }

                entry.getValue().put(time, splitText.toString());
            }
            index++;
        }
        return lessons;
    }

    @Override
    public List<Lesson> getTimetableByDay(String group, DayOfWeek day, WeekState weekState) {
        Map<String, Map<String, String>> lessons = parseTimetableByWeek(group, weekState);

        Map<String, String> lesson = new LinkedHashMap<>();
        StringBuilder builder = new StringBuilder();
        String dayWithState = "";

        switch (day){
            case MONDAY : {lesson = lessons.get("Понедельник"); dayWithState = "Понедельник"; break;}
            case TUESDAY : {lesson = lessons.get("Вторник"); dayWithState = "Вторник"; break;}
            case WEDNESDAY : {lesson = lessons.get("Среда"); dayWithState = "Среда"; break;}
            case THURSDAY : {lesson = lessons.get("Четверг"); dayWithState = "Четверг"; break;}
            case FRIDAY : {lesson = lessons.get("Пятница"); dayWithState = "Пятница"; break;}
            case SATURDAY : {lesson = lessons.get("Суббота"); dayWithState = "Суббота"; break;}
            case SUNDAY : {lesson = lessons.get("Воскресенье"); dayWithState = "Воскресенье"; break;}
        }



        builder.append(dayWithState).append("\n");

        List<Lesson> lessonList = new ArrayList<>();

        if (lesson != null) {
            for (Map.Entry<String, String> entry: lesson.entrySet()) {

                Lesson lessonObj = new Lesson();
                lessonObj.setTimeBegin(entry.getKey().split("\\s+")[0]);
                lessonObj.setTimeEnd(entry.getKey().split("\\s+")[2]);

                if (!entry.getValue().isEmpty()) {
                    lessonObj.setLesson(entry.getValue().split(System.lineSeparator())[0]);
                    lessonObj.setTeacher(entry.getValue().split(System.lineSeparator())[1]);
                    lessonObj.setGroups(entry.getValue().split(System.lineSeparator())[2]);
                }

                lessonList.add(lessonObj);
            }
        }

        return lessonList;
    }

    public TimeTableProviderImpl(TimeTableParser timeTableParser) {
        this.timeTableParser = timeTableParser;
    }
}
