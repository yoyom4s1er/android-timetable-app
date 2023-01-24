package com.example.timetableapp.util;

import java.time.DayOfWeek;

public class DayOfWeekConverter {
    public static DayOfWeek convertToDayOfWeek(int dayCalendar) {
        DayOfWeek day = null;

        switch (dayCalendar) {
            case 1 :{ day = DayOfWeek.SUNDAY; break;}
            case 2 :{ day = DayOfWeek.MONDAY; break;}
            case 3 :{ day = DayOfWeek.TUESDAY; break;}
            case 4 :{ day = DayOfWeek.WEDNESDAY; break;}
            case 5 :{ day = DayOfWeek.THURSDAY; break;}
            case 6 :{ day = DayOfWeek.FRIDAY; break;}
            case 7 :{ day = DayOfWeek.SATURDAY; break;}
        }

        return day;
    }
}
