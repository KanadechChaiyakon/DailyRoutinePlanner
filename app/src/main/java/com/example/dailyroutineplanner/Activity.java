package com.example.dailyroutineplanner;

public class Activity implements Comparable {

    private String ActivityName, Location, Detail, Day, Date, StartTime, EndTime;

    private int ActivityID;

    public Activity(String activityName, String location, String detail, String day, String date, String startTime, String endTime, int activityID) {
        ActivityName = activityName;
        Location = location;
        Detail = detail;
        this.Date = date;
        Day = day;
        StartTime = startTime;
        EndTime = endTime;
        ActivityID = activityID;
    }

    public String getActivityName() {
        return ActivityName;
    }

    public String getLocation() {
        return Location;
    }

    public String getDetail() {
        return Detail;
    }

    public String getDay() {
        return Day;
    }

    public String getStartTime() {
        return StartTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public int getActivityID() {
        return ActivityID;
    }

    public String getDate() {
        return Date;
    }

    @Override
    public int compareTo(Object o) {

        String compare = ((Activity)o).getDate();

        return this.Date.compareTo(compare);
    }
}
