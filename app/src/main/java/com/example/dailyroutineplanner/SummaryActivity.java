package com.example.dailyroutineplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class SummaryActivity extends AppCompatActivity {

    private ArrayList<Summary> summaryArrayList = new ArrayList<>();
    private int success, miss;
    private float sum, result_success, result_miss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DBConnect dbConnect = new DBConnect(this);
        SQLiteDatabase sqLiteDatabase = dbConnect.getWritableDatabase();
        Cursor Sumcursor = sqLiteDatabase.rawQuery("SELECT * FROM summary",null);
        while (Sumcursor.moveToNext()){
            int id = Sumcursor.getInt(Sumcursor.getColumnIndex("summaryID"));
            success = Sumcursor.getInt(Sumcursor.getColumnIndex("success"));
            miss = Sumcursor.getInt(Sumcursor.getColumnIndex("miss"));

            summaryArrayList.add(new Summary(id,success,miss));
        }

        sum = success+miss;
        result_success = (success/sum)*100;
        result_miss = (miss/sum)*100;
        setContentView(new Myview(this));
    }

    class Myview extends View{

        Paint paint = new Paint();

        public Myview(Context context) {
            super(context);
        }


        @Override
        protected void onDraw(Canvas canvas){
            super.onDraw(canvas);

            paint.setColor(Color.BLACK);
            paint.setTextSize(100);
            canvas.drawText("Summary",500,300,paint);
            paint.setTextSize(64);
            canvas.drawText("Success",380,1500,paint);
            canvas.drawText("Miss",830,1500,paint);
            paint.setColor(Color.BLACK);
            paint.setTextSize(80);
            canvas.drawText("Number of Success",150,1800,paint);
            canvas.drawText(success+" Time",150,1900,paint);
            canvas.drawText("Number of Miss",150,2100,paint);
            canvas.drawText(miss+" Time",150,2200,paint);

            paint.setColor(Color.GREEN);
            canvas.drawRect(400,1400-(result_success*10),600,1400,paint);
            paint.setColor(Color.RED);
            canvas.drawRect(800,1400-(result_miss*10),1000,1400,paint);

        }
    }
}