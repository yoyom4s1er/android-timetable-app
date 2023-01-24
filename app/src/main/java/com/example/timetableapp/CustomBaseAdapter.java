package com.example.timetableapp;

import android.app.Person;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CustomBaseAdapter extends BaseAdapter {

    Context mContext;
    int mResource;

    List<Lesson> lessons;

    public CustomBaseAdapter(Context context, int resource, List<Lesson> objects) {
        mContext = context;
        mResource = resource;
        lessons = objects;
    }

    @Override
    public int getCount() {
        return lessons.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String timeBegin = lessons.get(position).getTimeBegin();
        String timeEnd = lessons.get(position).getTimeEnd();
        String lesson = lessons.get(position).getLesson();
        String teacher = lessons.get(position).getTeacher();
        String groups = lessons.get(position).getGroups();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView timeBeginView = (TextView) convertView.findViewById(R.id.textViewTimeBegin);
        TextView timeEndView = (TextView) convertView.findViewById(R.id.textViewTimeEnd);
        TextView lessonView = (TextView) convertView.findViewById(R.id.textViewLesson);
        TextView teacherView = (TextView) convertView.findViewById(R.id.textViewTeacher);
        TextView groupsView = (TextView) convertView.findViewById(R.id.textViewGroups);

        timeBeginView.setText(timeBegin);
        timeEndView.setText(timeEnd);
        lessonView.setText(lesson);
        teacherView.setText(teacher);
        groupsView.setText(groups);

        return convertView;

    }

    public List<Lesson> getLessons() {
        return lessons;
    }
}
