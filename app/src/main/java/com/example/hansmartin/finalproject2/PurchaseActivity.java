package com.example.hansmartin.finalproject2;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.DAY_OF_YEAR;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

public class PurchaseActivity extends AppCompatActivity {

    MainActivity mainActivity;

    Database db = new Database(this);

    Button bDelete;
    Button bUpdate;

    DatePicker dDate;
    String name;
    Calendar date;
    String note;

    String selectedPurchaseID;

    String userID;
    //final delete
    TextView test;

    public static ArrayList<Purchase> vPurchases = new ArrayList<>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);

        dDate = findViewById(R.id.dDate);

        listView = findViewById(R.id.lPurchases);
        PurchaseAdapter purchasesAdapter = new PurchaseAdapter(this);

        listView.setAdapter(purchasesAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {

                Purchase selected = (Purchase) adapter.getItemAtPosition(position);
                selectedPurchaseID = selected.purchaseID;

            }
        });

        Bundle bUserID = getIntent().getExtras();

        userID = (String) bUserID.getSerializable("userID");

        Cursor cPurchasesDataUser = db.getPurchasesDataUser(userID);


            while (cPurchasesDataUser.moveToNext()) {

                String sDate = cPurchasesDataUser.getString(4);

                int day = Integer.parseInt(sDate.substring(0,sDate.indexOf("-")));
                int dbMonth = Integer.parseInt(sDate.substring(sDate.indexOf("-"),sDate.lastIndexOf("-")));
                int month = dbMonth +1;
                int year = Integer.parseInt(sDate.substring(sDate.lastIndexOf("-")+1,sDate.length()));

                Calendar dbDate = Calendar.getInstance();
                dbDate.set(year,month,day);

                Cursor cPackageName = db.getPackageName(cPurchasesDataUser.getString(2));

                while(cPackageName.moveToNext()){
                    vPurchases.add(new Purchase(cPackageName.getString(0),
                            dbDate,
                            cPurchasesDataUser.getString(3),
                            cPurchasesDataUser.getString(0),
                            cPurchasesDataUser.getString(1),
                            cPurchasesDataUser.getString(2)));
                }
                //final delete
                Log.i("purchaseID",cPurchasesDataUser.getString(0));
                Log.i("userID",cPurchasesDataUser.getString(1));
                Log.i("packageID",cPurchasesDataUser.getString(2));
                Log.i("notes",cPurchasesDataUser.getString(3));
                Log.i("date",cPurchasesDataUser.getString(4));
                //final delete
            }
    }
}