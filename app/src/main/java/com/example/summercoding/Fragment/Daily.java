package com.example.summercoding.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.summercoding.Adapter.ListAdapter;
import com.example.summercoding.Database.DbOpenHelper;
import com.example.summercoding.ItemData;
import com.example.summercoding.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Daily extends Fragment implements View.OnClickListener{
    private String mDateFormat = "E요일";
    private String QueryFormat = "yyyy-MM-dd";
    private String QueryInStr;
    private String yearInStr;
    private String todayInStr;
    private Date day;
    private TextView text_daily_year;
    private TextView text_daily;
    private TextView daily_remain_space;
    private ListView listView;
    private ListAdapter Adapter;
    private DbOpenHelper mDbOpenHelper;
    private ArrayList<ItemData> Data = new ArrayList<>();
    private ImageButton left;
    private ImageButton right;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daily,container,false);
        listView =(ListView)view.findViewById(R.id.daily_schedule_list_view);
        text_daily = (TextView)view.findViewById(R.id.text_daily);
        text_daily_year = (TextView)view.findViewById(R.id.text_daily_year);
        daily_remain_space = (TextView)view.findViewById(R.id.daily_remain_space);
        left = (ImageButton)view.findViewById(R.id.move_left_day);
        left.setOnClickListener(this);
        right = (ImageButton)view.findViewById(R.id.move_right_day);
        right.setOnClickListener(this);
        mDbOpenHelper = new DbOpenHelper(this.getActivity());
        mDbOpenHelper.open();
        mDbOpenHelper.create();

        Calendar mCalendar = Calendar.getInstance();
        day = mCalendar.getTime();
        Display_update(day);

        return view;
    }

    public void Display_update(Date day) {
        yearInStr = (day.getYear()+1900) + "년";
        todayInStr = (day.getMonth()+1) + "월 " + day.getDate() + "일 " + new SimpleDateFormat(mDateFormat).format(day);
        QueryInStr = new SimpleDateFormat(QueryFormat).format(day);
        text_daily_year.setText(yearInStr);
        text_daily.setText(todayInStr);
        Data = mDbOpenHelper.selectColumns(QueryInStr);

        if(Data.size() == 0)
            daily_remain_space.setVisibility(View.VISIBLE);
        else
            daily_remain_space.setVisibility(View.INVISIBLE);

        Adapter = new ListAdapter(Data);
        listView.setAdapter(Adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.move_left_day:
                day.setDate(day.getDate()-1);
                Display_update(day);
                break;
            case R.id.move_right_day:
                day.setDate(day.getDate()+1);
                Display_update(day);
                break;
            default:
                break;
        }
    }
}