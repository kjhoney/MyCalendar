package com.example.summercoding.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.summercoding.Adapter.ListAdapter;
import com.example.summercoding.Database.DbOpenHelper;
import com.example.summercoding.ItemData;
import com.example.summercoding.R;
import com.example.summercoding.decorators.OnedayDecorator;
import com.example.summercoding.decorators.SaturdayDecorator;
import com.example.summercoding.decorators.SundayDecorator;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Weekly extends Fragment {
    private String mDateFormat = "yyyy년 MM월 dd일";
    private String QueryFormat = "yyyy-MM-dd";
    private MaterialCalendarView materialCalendarView;
    private DbOpenHelper mDbOpenHelper;
    private TextView textView;
    private TextView remain_space;
    private ListView listView;
    private ListAdapter nAdapter;
    private ArrayList<ItemData> Data = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weekly,container,false);
        listView = (ListView)view.findViewById(R.id.weekly_schedule_list_view);
        textView = (TextView)view.findViewById(R.id.text_selected);
        remain_space = (TextView)view.findViewById(R.id.weekly_remain_space);
        materialCalendarView = (MaterialCalendarView) view.findViewById(R.id.WeeklycalendarView);

        mDbOpenHelper = new DbOpenHelper(this.getActivity());
        mDbOpenHelper.open();

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

        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                String SelectedDay = String.format("%02d",date.getYear())+"-"+String.format("%02d",date.getMonth()+1)+"-"+String.format("%02d",date.getDay());
                String ShownDay = date.getYear() + "년 " + (date.getMonth()+1) + "월 " + date.getDay() +"일의 일정";
                Data = mDbOpenHelper.selectColumns(SelectedDay);
                if(Data.size() == 0)
                    remain_space.setVisibility(View.VISIBLE);
                else
                    remain_space.setVisibility(View.INVISIBLE);

                textView.setText(ShownDay);
                nAdapter = new ListAdapter(Data);
                listView.setAdapter(nAdapter);
            }
        });

        Calendar mCalendar = Calendar.getInstance();
        Date today = mCalendar.getTime();
        String TodayInStr = new SimpleDateFormat(mDateFormat).format(today);
        String QueryInStr = new SimpleDateFormat(QueryFormat).format(today);
        textView.setText(TodayInStr+"의 일정");

        Data = mDbOpenHelper.selectColumns(QueryInStr);
        if(Data.size() == 0)
            remain_space.setVisibility(View.VISIBLE);
        else
            remain_space.setVisibility(View.INVISIBLE);
        nAdapter = new ListAdapter(Data);
        listView.setAdapter(nAdapter);
        return view;
    }
}