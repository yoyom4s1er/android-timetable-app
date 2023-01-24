package com.example.timetableapp;

public class Lesson {

    private String timeBegin;
    private String timeEnd;
    private String lesson;
    private String teacher;
    private String groups;

    public Lesson(String timeBegin, String timeEnd, String lesson, String teacher, String groups) {
        this.timeBegin = timeBegin;
        this.timeEnd = timeEnd;
        this.lesson = lesson;
        this.teacher = teacher;
        this.groups = groups;
    }

    public Lesson() {
    }

    public String getTimeBegin() {
        return timeBegin;
    }

    public void setTimeBegin(String timeBegin) {
        this.timeBegin = timeBegin;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getLesson() {
        return lesson;
    }

    public void setLesson(String lesson) {
        this.lesson = lesson;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "timeBegin='" + timeBegin + '\'' +
                ", timeEnd='" + timeEnd + '\'' +
                ", lesson='" + lesson + '\'' +
                ", teacher='" + teacher + '\'' +
                ", groups='" + groups + '\'' +
                '}';
    }
}
