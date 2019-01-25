package com.example.hansmartin.finalproject2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static java.util.Calendar.DATE;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.DAY_OF_YEAR;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

public class PackageDetail extends AppCompatActivity implements View.OnClickListener {

    LoginActivity loginActivity;

    String purchaseID;
    String userID;
    TextView pName;
    TextView pPrice;
    TextView pRating;
    DatePicker dDate;
    EditText tNote;
    Button bReserve;

    String id;
    String name;
    String price;
    String rating;

    Database db = new Database(this);

    public void generateID() {

        Cursor cPurchasesData = db.getPurchasesData();

        if (cPurchasesData.getCount() == 0) {
            purchaseID = "PC001";
        } else if (cPurchasesData.getCount() != 0) {
            while (cPurchasesData.moveToNext()) {
                String lastID = cPurchasesData.getString(0);
                int id = Integer.parseInt(lastID.substring(lastID.indexOf("C") + 1, lastID.length()));
                if (id < 9) {
                    purchaseID = "PC00" + (id + 1);
                } else if (id < 99) {
                    purchaseID = "PC0" + (id + 1);
                } else if (id < 999) {
                    purchaseID = "PC" + (id + 1);
                }
                //final delete
                try {
                    Log.i("purchaseID", cPurchasesData.getString(0));
                    Log.i("userID", cPurchasesData.getString(1));
                    Log.i("packageID", cPurchasesData.getString(2));
                    Log.i("notes", cPurchasesData.getString(3));
                    Log.i("date", cPurchasesData.getString(4));
                } catch (Exception e) {

                }
                //final delete
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_detail);

        pName = findViewById(R.id.pName);
        pPrice = findViewById(R.id.pPrice);
        pRating = findViewById(R.id.pRating);
        dDate = findViewById(R.id.dDate);
        tNote = findViewById(R.id.tNote);
        bReserve = findViewById(R.id.bReserve);

        bReserve.setOnClickListener(this);

        Bundle productDetail = getIntent().getExtras();

        id = (String) productDetail.getSerializable("id");
        name = (String) productDetail.getSerializable("name");
        price = (String) productDetail.getSerializable("price").toString();
        rating = (String) productDetail.getSerializable("rating").toString();

        userID = (String) productDetail.getSerializable("userID");

        pName.setText("Name: " + name);
        pPrice.setText("Price: " + price);
        pRating.setText("Rating: " + rating);

    }

    @Override
    public void onClick(View v) {
        loginActivity = new LoginActivity();

        Intent intent = null;

        if (v == bReserve) {

            int year = dDate.getYear();
            int month = dDate.getMonth();
            int day = dDate.getDayOfMonth();

            Calendar currentDate = Calendar.getInstance();
            Calendar datePicker = Calendar.getInstance();

            datePicker.set(year, month, day);

            int iDatePicker = datePicker.get(DAY_OF_YEAR);
            int iCurrentDate = currentDate.get(DAY_OF_YEAR);

            String name = pName.getText().toString();
            String price = pPrice.getText().toString();
            String rating = pRating.getText().toString();
            String note = tNote.getText().toString();

            int iYear = year - currentDate.get(YEAR);

            if (iYear > 0) {
                iDatePicker = iDatePicker + 365;
            } else if (iYear < 0) {
                iDatePicker = iDatePicker - 365;
            }

            if (iDatePicker - iCurrentDate == 0){
                Toast.makeText(this, "Date must be choosen", Toast.LENGTH_SHORT).show();
            }else if (iDatePicker - iCurrentDate < 7) {
                Toast.makeText(this, "Date must be at least one week after current date", Toast.LENGTH_SHORT).show();
            } else {

                if (note.isEmpty()) {
                    note = "-";
                }

                Toast.makeText(this, "Package added to Purchases", Toast.LENGTH_SHORT).show();

                generateID();

                int dpDay = datePicker.get(DAY_OF_MONTH);
                int dpMonth = datePicker.get(MONTH)+1;
                int dpYear = datePicker.get(YEAR);

                String dpDate = dpDay+"-"+dpMonth+"-"+dpYear;

                db.insertPurchases(purchaseID, userID, id, note, dpDate);

                intent = new Intent(this, MainActivity.class);

                intent.putExtra("userID", userID);

                startActivity(intent);
            }
        }
    }
}