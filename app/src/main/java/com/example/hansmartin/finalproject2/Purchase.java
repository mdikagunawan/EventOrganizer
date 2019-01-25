package com.example.hansmartin.finalproject2;

import java.util.Calendar;

public class Purchase {

    public Purchase(String name, Calendar date, String note, String purchaseID, String userID, String packageID) {

        this.name = name;
        this.date = date;
        this.note = note;
        this.purchaseID = purchaseID;
        this.userID = userID;
        this.packageID = packageID;
    }
    public String name;
    public Calendar date;
    public String note;
    public String purchaseID;
    public String userID;
    public String packageID;
}
