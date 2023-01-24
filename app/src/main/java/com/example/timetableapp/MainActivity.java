package com.example.timetableapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.timetableapp.service.HtmlServiceImpl;
import com.example.timetableapp.service.parser.TimeTableParser;
import com.example.timetableapp.service.parser.TimeTableProvider;
import com.example.timetableapp.service.parser.TimeTableProviderImpl;
import com.example.timetableapp.util.CurrentWeekState;
import com.example.timetableapp.util.DayOfWeekConverter;
import com.example.timetableapp.util.WeekState;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TimeTableParser timeTableParser = new TimeTableParser("https://www.miit.ru/timetable", new HtmlServiceImpl());
        TimeTableProvider timeTableProvider = new TimeTableProviderImpl(timeTableParser);

        ListView listView = (ListView) findViewById(R.id.customListView);

        CustomBaseAdapter customBaseAdapter = new CustomBaseAdapter(this, R.layout.activity_custom_list_view, new ArrayList<>());
        listView.setAdapter(customBaseAdapter);

        /* starts before 1 month from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

        /* ends after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);

        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .build();


        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                // on below line we are printing date
                // in the logcat which is selected.
                Log.e("TAG", "CURRENT DATE IS " + date);

                Thread thread = new Thread(() ->{
                        try  {
                            List<Lesson> lessons = timeTableProvider.getTimetableByDay("УИС-312", DayOfWeekConverter.convertToDayOfWeek(date.get(Calendar.DAY_OF_WEEK)), CurrentWeekState.getWeekState(date));
                            System.out.println(lessons);
                            customBaseAdapter.getLessons().clear();
                            customBaseAdapter.getLessons().addAll(lessons);

                            runOnUiThread(customBaseAdapter::notifyDataSetChanged);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });

                thread.start();
            }
        });
    }
}