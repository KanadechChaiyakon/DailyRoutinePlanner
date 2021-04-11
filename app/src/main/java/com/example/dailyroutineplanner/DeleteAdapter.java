package com.example.dailyroutineplanner;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DeleteAdapter extends RecyclerView.Adapter<DeleteAdapter.ViewHolder> {

    private ArrayList<Activity> activityArrayList;
    private Context context;

    public DeleteAdapter(ArrayList<Activity> activityArrayList, Context context) {
        this.activityArrayList = activityArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public DeleteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.deleteactivityform, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeleteAdapter.ViewHolder holder, int position) {

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
        Button delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ActivityName = itemView.findViewById(R.id.deletetextView7);
            ActivityTime = itemView.findViewById(R.id.deletetextView9);
            ActivityLocation = itemView.findViewById(R.id.deletetextView8);
            ActivityDetail = itemView.findViewById(R.id.deletetextView10);
            delete = itemView.findViewById(R.id.deletebutton4);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    DBConnect dbConnect = new DBConnect(context);
                    SQLiteDatabase sqLiteDatabase = dbConnect.getWritableDatabase();
                    sqLiteDatabase.execSQL("DELETE FROM activity WHERE activityID ="+ activityArrayList.get(getAdapterPosition()).getActivityID());

                    Intent intent = new Intent(context,MainActivity.class);
                    context.startActivity(intent);

                }
            });
        }


    }
}
