package com.example.summercoding;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Daily extends Fragment implements View.OnClickListener{
    private String mDateFormat = "yyyy년 MM월 dd일 E요일";
    private String QueryFormat = "yyyy-MM-dd";
    private TextView text_daily;
    private ListView listView;
    private DbOpenHelper mDbOpenHelper;
    private ArrayList<ItemData> Data = new ArrayList<>();
    Button left;
    Button right;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daily,container,false);
        listView =(ListView)view.findViewById(R.id.daily_schedule_list_view);
        text_daily = (TextView)view.findViewById(R.id.text_daily);
        left = (Button)view.findViewById(R.id.move_left_day);
        left.setOnClickListener(this);
        right = (Button)view.findViewById(R.id.move_right_day);
        right.setOnClickListener(this);
        mDbOpenHelper = new DbOpenHelper(this.getActivity());
        mDbOpenHelper.open();
        mDbOpenHelper.create();

        Calendar mCalendar = Calendar.getInstance();
        Date today = mCalendar.getTime();
        String todayInStr = new SimpleDateFormat(mDateFormat).format(today);
        String QueryInStr = new SimpleDateFormat(QueryFormat).format(today);
        text_daily.setText(todayInStr);

        Data = mDbOpenHelper.selectColumns(QueryInStr);

        ListAdapter oAdapter = new ListAdapter(Data);
        listView.setAdapter(oAdapter);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.move_left_day:

                break;
            case R.id.move_right_day:

                break;
            default:
                break;
        }
    }
}