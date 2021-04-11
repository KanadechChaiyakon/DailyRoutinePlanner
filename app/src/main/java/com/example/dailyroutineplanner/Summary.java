package com.example.dailyroutineplanner;

public class Summary {

    private int SummaryID, Success, Miss;

    public Summary(int summaryID, int success, int miss) {
        SummaryID = summaryID;
        Success = success;
        Miss = miss;
    }

    public int getSummaryID() {
        return SummaryID;
    }

    public int getSuccess() {
        return Success;
    }

    public int getMiss() {
        return Miss;
    }
}
