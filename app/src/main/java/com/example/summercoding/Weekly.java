package com.example.summercoding;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.summercoding.decorators.OnedayDecorator;
import com.example.summercoding.decorators.SaturdayDecorator;
import com.example.summercoding.decorators.SundayDecorator;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Weekly extends Fragment {
    private String QueryFormat = "yyyy-MM-dd";
    private MaterialCalendarView materialCalendarView;
    private DbOpenHelper mDbOpenHelper;
    private ListView listView;
    private ArrayList<ItemData> Data = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weekly,container,false);
        listView =(ListView)view.findViewById(R.id.weekly_schedule_list_view);
        materialCalendarView = (MaterialCalendarView) view.findViewById(R.id.WeeklycalendarView);
        materialCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2017, 0, 1)) // 달력의 시작
                .setMaximumDate(CalendarDay.from(2030, 11, 31)) // 달력의 끝
                .setCalendarDisplayMode(CalendarMode.WEEKS)
                .commit();

        materialCalendarView.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator(),
                new OnedayDecorator());

        Calendar mCalendar = Calendar.getInstance();
        Date today = mCalendar.getTime();
        String QueryInStr = new SimpleDateFormat(QueryFormat).format(today);

        mDbOpenHelper = new DbOpenHelper(this.getActivity());
        mDbOpenHelper.open();
        mDbOpenHelper.create();

        Data = mDbOpenHelper.selectColumns(QueryInStr);

        ListAdapter nAdapter = new ListAdapter(Data);
        listView.setAdapter(nAdapter);
        return view;
    }
}