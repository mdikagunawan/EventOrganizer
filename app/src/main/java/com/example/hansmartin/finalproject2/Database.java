package com.example.hansmartin.finalproject2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Calendar;

public class Database extends SQLiteOpenHelper {
    public Database(Context context) {
        super(context, "db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Packages (PackageID VARCHAR(255) PRIMARY KEY, PackageName VARCHAR(255), Price INTEGER, Rating VARCHAR(255))");
        db.execSQL("CREATE TABLE Users (UserID VARCHAR(255) PRIMARY KEY, Name VARCHAR(255), Email VARCHAR(255), Password VARCHAR(255), PhoneNumber VARCHAR(255))");
        db.execSQL("CREATE TABLE Purchases (PurchaseID VARCHAR(255) PRIMARY KEY, UserID VARCHAR(255), PackageID VARCHAR(255), Notes VARCHAR(255), Date VARCHAR(255))");
    }

    public Cursor getUserData(){
        Cursor c;
        SQLiteDatabase db = getReadableDatabase();
        c = db.rawQuery("SELECT * FROM Users", null);

        return  c;
    }

    public Cursor validateLogin(String email){
        Cursor c;
        SQLiteDatabase db = getReadableDatabase();
        c = db.rawQuery("SELECT Password FROM Users WHERE Email = '"+email+"'", null);

        return  c;
    }

    public Cursor getUserID(String email){
        Cursor c;
        SQLiteDatabase db = getReadableDatabase();
        c = db.rawQuery("SELECT UserID FROM Users WHERE Email = '"+email+"'", null);

        return  c;
    }

    public void insertUser(String userID, String name, String email, String password, String phone){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO users VALUES ('"+userID+"','"+name+"','"+email+"','"+password+"','"+phone+"')");
    }

    public Cursor getPackageData(){
        Cursor c;
        SQLiteDatabase db = getReadableDatabase();
        c = db.rawQuery("SELECT * FROM Packages", null);

        return  c;
    }

    public void  insertPackage(String pID, String pName, int pPrice, int pRating){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO packages VALUES ('"+pID+"','"+pName+"','"+pPrice+"','"+pRating+"')");
    }

    public Cursor getPackageName(String packageID){
        Cursor c;
        SQLiteDatabase db = getReadableDatabase();
        c = db.rawQuery("SELECT PackageName FROM Packages WHERE PackageID = '"+packageID+"'", null);

        return  c;
    }

    public Cursor getPurchasesData(){
        Cursor c;
        SQLiteDatabase db = getReadableDatabase();
        c = db.rawQuery("SELECT * FROM Purchases", null);

        return  c;
    }

    public void  insertPurchases(String purchaseID, String userID, String packageID, String notes, String date){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO Purchases VALUES ('"+purchaseID+"','"+userID+"','"+packageID+"','"+notes+"','"+date+"')");
    }

    public Cursor getPurchasesDataUser(String userID){
        Cursor c;
        SQLiteDatabase db = getReadableDatabase();
        c = db.rawQuery("SELECT * FROM Purchases WHERE UserID = '"+userID+"'", null);

        return  c;
    }

    public void deletePurchasesData(String purchaseID){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM Purchases WHERE PurchaseID = '"+purchaseID+"'", null);

    }

    public void updatePurchasesData(String purchaseID, String date){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE Purchases SET Date = '"+date+"' WHERE PurchaseID = '"+purchaseID+"'");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
