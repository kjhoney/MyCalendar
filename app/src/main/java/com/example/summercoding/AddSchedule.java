package com.example.summercoding;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.summercoding.Database.DbOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

public class AddSchedule extends AppCompatActivity implements View.OnClickListener {
    String mDateFormat = "yyyy-MM-dd";
    TextView edit_title;
    Button edit_date;
    TextView edit_content;
    Button register;
    Button cancel;
    Intent intent;
    DatePickerDialog dialog;
    Calendar minDate = Calendar.getInstance();
    Calendar maxDate = Calendar.getInstance();
    private DbOpenHelper mDbOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);

        Toolbar tb = (Toolbar) findViewById(R.id.add_toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        edit_title = (TextView)findViewById(R.id.edit_title);
        edit_date = (Button)findViewById(R.id.edit_date);
        edit_date.setOnClickListener(this);
        edit_content = (TextView)findViewById(R.id.edit_content);
        register = (Button)findViewById(R.id.register);
        register.setOnClickListener(this);
        cancel = (Button)findViewById(R.id.cancel);
        cancel.setOnClickListener(this);

        mDbOpenHelper = new DbOpenHelper(this);
        mDbOpenHelper.open();
        mDbOpenHelper.create();

        Calendar mCalendar = Calendar.getInstance();
        Date today = mCalendar.getTime();
        String todayInStr = new SimpleDateFormat(mDateFormat).format(today);
        edit_date.setText(todayInStr);

        dialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, listener, today.getYear() + 1900, today.getMonth(), today.getDate());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edit_date:
                minDate.set(2017,0,1);
                dialog.getDatePicker().setMinDate(minDate.getTime().getTime());
                maxDate.set(2030,11,31);
                dialog.getDatePicker().setMaxDate(maxDate.getTimeInMillis());
                dialog.getDatePicker().setCalendarViewShown(false);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();
                break;
            case R.id.register:
                String title = edit_title.getText().toString();
                if(title.length() == 0) {
                    Toast.makeText(AddSchedule.this, "할 일을 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    String date = edit_date.getText().toString();
                    String content = edit_content.getText().toString();
                    mDbOpenHelper.open();
                    mDbOpenHelper.insertColumn(title, date, content);
                    intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.cancel:
                intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            monthOfYear++;
            String SelectedDate = String.format("%04d",year)+"-"+String.format("%02d",monthOfYear)+"-"+String.format("%02d",dayOfMonth);
            edit_date.setText(SelectedDate);
        }
    };
}