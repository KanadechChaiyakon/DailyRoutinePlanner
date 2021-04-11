package com.example.dailyroutineplanner;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> {

    private ArrayList<Activity> activityArrayList;
    private ArrayList<Summary> summaryArrayList;
    private Context context;
    private int success, miss;

    public ActivityAdapter(Context context, ArrayList<Activity> activityArrayList, ArrayList<Summary> summaryArrayList) {
        this.context = context;
        this.activityArrayList = activityArrayList;
        this.summaryArrayList = summaryArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activityform, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String date = activityArrayList.get(position).getDate();
        String stime = activityArrayList.get(position).getStartTime();
        String etime = activityArrayList.get(position).getEndTime();
        String currenttime = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
        String currentdate = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
        if (currenttime.compareTo(etime)<0 && date.compareTo(currentdate)==0){
            holder.Done.setBackgroundColor(Color.parseColor("#41FF7B"));
        }
        else if(date.compareTo(currentdate)>0){
            holder.Done.setTextSize(10);
            holder.Done.setText("Not Yet");
            holder.Done.setBackgroundColor(Color.parseColor("#A9A9A9"));
        }
        else {
            holder.Done.setText("MISS");
            holder.Done.setBackgroundColor(Color.parseColor("#FF4141"));
        }

        holder.ActivityName.setText(activityArrayList.get(position).getActivityName());
        holder.ActivityTime.setText(activityArrayList.get(position).getDate()+" "+activityArrayList.get(position).getDay()+" "+activityArrayList.get(position).getStartTime()+"-"+activityArrayList.get(position).getEndTime());
        holder.ActivityLocation.setText(activityArrayList.get(position).getLocation());
        holder.ActivityDetail.setText(activityArrayList.get(position).getDetail());
    }

    @Override
    public int getItemCount() {
        return activityArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView ActivityName, ActivityTime, ActivityLocation, ActivityDetail;

        Button Done;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ActivityName = itemView.findViewById(R.id.textView7);
            ActivityTime = itemView.findViewById(R.id.textView9);
            ActivityLocation = itemView.findViewById(R.id.textView8);
            ActivityDetail = itemView.findViewById(R.id.textView10);
            Done = itemView.findViewById(R.id.button4);

            Done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String day = activityArrayList.get(getAdapterPosition()).getDate();
                    success = summaryArrayList.get(0).getSuccess();
                    miss = summaryArrayList.get(0).getMiss();
                    String stime = activityArrayList.get(getAdapterPosition()).getStartTime();
                    String etime = activityArrayList.get(getAdapterPosition()).getEndTime();
                    String currenttime = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
                    String currentdate = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
                    DBConnect dbConnect = new DBConnect(context);
                    SQLiteDatabase sqLiteDatabase = dbConnect.getWritableDatabase();

                    if (currenttime.compareTo(etime)<0 && day.compareTo(currentdate)==0){
                        success += 1;
                        sqLiteDatabase.execSQL("UPDATE Summary SET success ="+ success +" WHERE summaryID = 1");
                        sqLiteDatabase.execSQL("DELETE FROM activity WHERE activityID ="+ activityArrayList.get(getAdapterPosition()).getActivityID());
                        Intent intent = new Intent(context,MainActivity.class);
                        context.startActivity(intent);
                    }
                    else if(day.compareTo(currentdate)>0){
                        Toast.makeText(context, "Please do it other day", Toast.LENGTH_LONG).show();
                    }
                    else {
                        miss += 1;
                        sqLiteDatabase.execSQL("UPDATE Summary SET miss ="+ miss +" WHERE summaryID = 1");
                        sqLiteDatabase.execSQL("DELETE FROM activity WHERE activityID ="+ activityArrayList.get(getAdapterPosition()).getActivityID());
                        Intent intent = new Intent(context,MainActivity.class);
                        context.startActivity(intent);
                    }

                }
            });
        }
    }
}
