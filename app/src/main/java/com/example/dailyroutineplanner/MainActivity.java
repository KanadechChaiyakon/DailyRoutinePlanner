package com.example.dailyroutineplanner;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;
import java.util.logging.SimpleFormatter;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("DailyRoutine");
        setSupportActionBar(toolbar);

        DBConnect dbConnect = new DBConnect(this);
        SQLiteDatabase sqLiteDatabase = dbConnect.getWritableDatabase();

        ArrayList<Summary> summaryArrayList = new ArrayList<>();
        Cursor Sumcursor = sqLiteDatabase.rawQuery("SELECT * FROM summary",null);
        while (Sumcursor.moveToNext()){
            int id = Sumcursor.getInt(Sumcursor.getColumnIndex("summaryID"));
            int success = Sumcursor.getInt(Sumcursor.getColumnIndex("success"));
            int miss = Sumcursor.getInt(Sumcursor.getColumnIndex("miss"));

            summaryArrayList.add(new Summary(id,success,miss));
        }
        if(summaryArrayList.size() == 0){
            ContentValues contentValues = new ContentValues();
            contentValues.put("success",0);
            contentValues.put("miss",0);
            sqLiteDatabase.insert("summary",null,contentValues);
        }

        ArrayList<Activity> activities = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM activity", null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("activityID"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            String day = cursor.getString(cursor.getColumnIndex("day"));
            String location = cursor.getString(cursor.getColumnIndex("location"));
            String detail = cursor.getString(cursor.getColumnIndex("detail"));
            String activityname = cursor.getString(cursor.getColumnIndex("activityname"));
            String stime = cursor.getString(cursor.getColumnIndex("startTime"));
            String etime = cursor.getString(cursor.getColumnIndex("endTime"));

            String currenttime = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
            String currentdate = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());


            activities.add(new Activity(activityname,location,detail,day,date,stime,etime,id));

        }
        sqLiteDatabase.close();
        Collections.sort(activities);

        ActivityAdapter activityAdapter = new ActivityAdapter(this,activities,summaryArrayList);

        this.recyclerView = findViewById(R.id.recyclerView2);

        recyclerView.setAdapter(activityAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public void onSelectClearStatActivity(MenuItem menuItem){
        DBConnect dbConnect = new DBConnect(this);
        SQLiteDatabase sqLiteDatabase = dbConnect.getWritableDatabase();
        sqLiteDatabase.execSQL("UPDATE Summary SET miss = 0 , success = 0 WHERE summaryID = 1");
        Toast.makeText(this, "Clear Stat Complete", Toast.LENGTH_LONG).show();
    }

    public void onSelectDeleteAllActivity(MenuItem menuItem){
        DBConnect dbConnect = new DBConnect(this);
        SQLiteDatabase sqLiteDatabase = dbConnect.getWritableDatabase();
        sqLiteDatabase.execSQL("DELETE FROM activity");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        Toast.makeText(this, "Delete All Activity", Toast.LENGTH_LONG).show();
    }


    public void onSelectAddActivity(MenuItem menu){
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);

    }

    public void onSelectDeleteActivity(MenuItem menuItem){
        Intent intent = new Intent(this,DeleteActivity.class);
        startActivity(intent);
    }

    public void onSelectSelectSummary(MenuItem menuItem){
        Intent intent = new Intent(this, SummaryActivity.class);
        startActivity(intent);
    }

}