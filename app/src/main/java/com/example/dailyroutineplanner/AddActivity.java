package com.example.dailyroutineplanner;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddActivity extends AppCompatActivity {

    private EditText activity, location, detail;
    private Button start, end, datebutton;

    private String date = "empty";
    private String dayofweek = "empty";
    private String stime = "empty";
    private String etime = "empty";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        this.activity = findViewById(R.id.editTextTextPersonName);
        this.location = findViewById(R.id.editTextTextPersonName2);
        this.detail = findViewById(R.id.editTextTextPersonName3);
        this.start = findViewById(R.id.button);
        this.end = findViewById(R.id.button2);
        this.datebutton = findViewById(R.id.button3);
    }


    public void SelectDate(View view){

        Calendar calendar;
        calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(AddActivity.this, new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("EEE", Locale.ENGLISH);
                LocalDate localDate;
                if (i2 < 25){
                    localDate = LocalDate.of(i,i1,i2+3);
                }
                else {
                    localDate = LocalDate.of(i,i1,i2-4);
                }
                dayofweek = localDate.format(dateTimeFormatter);
                System.out.println(dayofweek);
                date = i + "/" + (i1+1) + "/" + i2;
                datebutton.setText(date);
                Toast.makeText(AddActivity.this, date+" "+ dayofweek, Toast.LENGTH_LONG).show();
            }
        },year,month,day);

        datePickerDialog.show();

    }

    public void SelectStartTime(View view){

        Calendar calendar;
        calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(AddActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {

                if(i > 9 && i1 < 10){
                    stime = i + ":0" + i1;
                }
                else if (i < 10 && i1 < 10){
                    stime = "0"+i + ":0" + i1;
                }
                else {
                    stime = i + ":" + i1;
                }
                start.setText(stime);

                Toast.makeText(AddActivity.this, stime, Toast.LENGTH_LONG).show();
            }
        }, hour,minute,true);

        timePickerDialog.show();

    }

    public void SelectEndTime(View view){

        Calendar calendar;
        calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(AddActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                if(i > 9 && i1 < 10){
                    etime = i + ":0" + i1;
                }
                else if (i < 10 && i1 < 10){
                    etime = "0"+i + ":0" + i1;
                }
                else {
                    etime = i + ":" + i1;
                }
                end.setText(etime);
                Toast.makeText(AddActivity.this, etime, Toast.LENGTH_LONG).show();
            }
        }, hour,minute,true);

        timePickerDialog.show();

    }

    public void SaveActivity(View view) {

        if (activity.getText().toString().equals("") || location.getText().toString().equals("") | detail.getText().toString().equals("")) {
            Toast.makeText(AddActivity.this, "please enter all information", Toast.LENGTH_LONG).show();
            return;
        } else if (stime.equals("empty") || etime.equals("empty")) {
            Toast.makeText(AddActivity.this, "Please Start Time and End Time", Toast.LENGTH_LONG).show();
            return;
        } else if (stime.compareTo(etime)>=0){
            Toast.makeText(AddActivity.this, "End Time must be after Start time", Toast.LENGTH_LONG).show();
            return;
        } else if (date.equals("empty") || dayofweek.equals("empty")) {
            Toast.makeText(AddActivity.this, "Please select Date", Toast.LENGTH_LONG).show();
            return;
        }  else {
            try {
                DBConnect dbConnect = new DBConnect(this);
                SQLiteDatabase sqLiteDatabase = dbConnect.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put("date", date);
                contentValues.put("day", dayofweek);
                contentValues.put("startTime", stime);
                contentValues.put("endTime", etime);
                contentValues.put("activityname", activity.getText().toString());
                contentValues.put("location", location.getText().toString());
                contentValues.put("detail", detail.getText().toString());

                sqLiteDatabase.insert("activity", null, contentValues);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm");
            String dateform = date + " " + stime;
            Date datemil = new Date();
            try {
                datemil = simpleDateFormat.parse(dateform);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Intent intent1 = new Intent(AddActivity.this, MyReceiver.class);
            intent1.putExtra("ActivityName",activity.getText().toString());
            intent1.putExtra("Detail", detail.getText().toString());
            PendingIntent pendingIntent = PendingIntent.getBroadcast(AddActivity.this, 0, intent1, PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, datemil.getTime(), pendingIntent);

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(AddActivity.this, "Add Activity Complete", Toast.LENGTH_LONG).show();

        }
    }

}