package com.example.timetableapp.service.parser;


import com.example.timetableapp.Lesson;
import com.example.timetableapp.util.WeekState;

import java.time.DayOfWeek;
import java.util.List;

public interface TimeTableProvider {

    public String getTimetableByWeek(String group, WeekState weekState);

    public List<Lesson> getTimetableByDay(String group, DayOfWeek day, WeekState weekState);
}
