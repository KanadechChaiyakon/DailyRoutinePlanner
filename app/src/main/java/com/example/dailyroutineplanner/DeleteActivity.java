package com.example.dailyroutineplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.util.ArrayList;

public class DeleteActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        this.toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);

        ArrayList<Activity> activities = new ArrayList<>();

        DBConnect dbConnect = new DBConnect(this);
        SQLiteDatabase sqLiteDatabase = dbConnect.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM activity",null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("activityID"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            String day = cursor.getString(cursor.getColumnIndex("day"));
            String location = cursor.getString(cursor.getColumnIndex("location"));
            String detail = cursor.getString(cursor.getColumnIndex("detail"));
            String activityname = cursor.getString(cursor.getColumnIndex("activityname"));
            String stime = cursor.getString(cursor.getColumnIndex("startTime"));
            String etime = cursor.getString(cursor.getColumnIndex("endTime"));

            activities.add(new Activity(activityname,location,detail,day,date,stime,etime,id));
        }
        sqLiteDatabase.close();

        DeleteAdapter deleteAdapter = new DeleteAdapter(activities,this);

        this.recyclerView = findViewById(R.id.recyclerView3);

        recyclerView.setAdapter(deleteAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }


}