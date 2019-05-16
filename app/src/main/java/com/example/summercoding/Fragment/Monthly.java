package com.example.summercoding.Fragment;


import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.summercoding.Database.DbOpenHelper;
import com.example.summercoding.ItemData;
import com.example.summercoding.R;
import com.example.summercoding.decorators.EventDecorator;
import com.example.summercoding.decorators.OnedayDecorator;
import com.example.summercoding.decorators.SaturdayDecorator;
import com.example.summercoding.decorators.SundayDecorator;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;

public class Monthly extends Fragment {
    private MaterialCalendarView materialCalendarView;
    private DbOpenHelper mDbOpenHelper;
    private ArrayList<ItemData> Data = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_monthly,container,false);
        mDbOpenHelper = new DbOpenHelper(this.getActivity());
        mDbOpenHelper.open();
        Data = mDbOpenHelper.selectAll();

        materialCalendarView = (MaterialCalendarView) view.findViewById(R.id.MonthlycalendarView);
        materialCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2017, 0, 1)) // 달력의 시작
                .setMaximumDate(CalendarDay.from(2030, 11, 31)) // 달력의 끝
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        materialCalendarView.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator(),
                new OnedayDecorator());
        new ApiSimulator(Data).executeOnExecutor(Executors.newSingleThreadExecutor());

        return view;
    }

    @Override
    public void onResume() {
        Data = mDbOpenHelper.selectAll();
        new ApiSimulator(Data).executeOnExecutor(Executors.newSingleThreadExecutor());
        super.onResume();
    }

    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {

        ArrayList<ItemData> Data;

        ApiSimulator(ArrayList<ItemData> Data){
            this.Data = Data;
        }

        @Override
        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
            Calendar calendar = Calendar.getInstance();
            ArrayList<CalendarDay> dates = new ArrayList<>();

            for(int i = 0; i < Data.size(); i++){
                String[] time = Data.get(i).date.split("-");
                int year = Integer.parseInt(time[0]);
                int month = Integer.parseInt(time[1]);
                int dayy = Integer.parseInt(time[2]);

                calendar.set(year,month-1,dayy);
                CalendarDay day = CalendarDay.from(calendar);
                dates.add(day);
            }
            return dates;
        }

        @Override
        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);

            materialCalendarView.addDecorator(new EventDecorator(Color.RED, calendarDays));
        }
    }
}