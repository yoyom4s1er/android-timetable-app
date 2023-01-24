package com.example.timetableapp.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.TimeZone;

public class CurrentWeekState {
    public static WeekState getCurrentWeek() {
        int week = LocalDate.now(ZoneId.of("Europe/Moscow")).get(WeekFields.ISO.weekOfYear());
        if (week % 2 == 0) {
            return WeekState.WEEK_TWO;
        }
        return WeekState.WEEK_ONE;
    }

    public static WeekState getNextWeek() {
        int week = LocalDate.now(ZoneId.of("Europe/Moscow")).get(WeekFields.ISO.weekOfYear());
        if (week % 2 == 0) {
            return WeekState.WEEK_ONE;
        }
        return WeekState.WEEK_TWO;
    }

    public static WeekState getWeekState(Calendar calendar) {

        Instant instant = calendar.toInstant();
        ZoneId zoneId = TimeZone.getDefault().toZoneId();
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, zoneId);

        int week = zonedDateTime.get(WeekFields.ISO.weekOfYear());
        if (week % 2 == 0) {
            return WeekState.WEEK_TWO;
        }
        return WeekState.WEEK_ONE;
    }
}
