package com.example.hansmartin.finalproject2;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Database db = new Database(this);
    String userId;

    public static ArrayList<Package> vPackage = new ArrayList<>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle bUserID = getIntent().getExtras();
        userId = (String) bUserID.getSerializable("userID");

        listView = findViewById(R.id.lPackage);
        String url = "https://api.myjson.com/bins/byvt2";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        if (vPackage.size()<10){
                            vPackage.add(new Package((String) jsonObject.get("PackageID"),
                                    (String) jsonObject.get("PackageName"),
                                    (Integer) jsonObject.get("Price"),
                                    (Integer) jsonObject.get("Rating")));
                        }


                        Cursor cPackageData = db.getPackageData();

                        if(cPackageData.getCount()<10){
                            db.insertPackage((String) jsonObject.get("PackageID"),
                                    (String) jsonObject.get("PackageName"),
                                    (Integer) jsonObject.get("Price"),
                                    (Integer) jsonObject.get("Rating"));
                        }else if (cPackageData.getCount()==10){
                            //final delete
                            while(cPackageData.moveToNext()){
                                Log.i("id",cPackageData.getString(0));
                                Log.i("name",cPackageData.getString(1));
                                Log.i("price",cPackageData.getString(2));
                                Log.i("rating",cPackageData.getString(3));
                            }
                            //final delete
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                listView.setAdapter(new PackageAdapter(vPackage, getApplicationContext()));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                Intent intent;

                Package selected = (Package) adapter.getItemAtPosition(position);

                intent = new Intent(MainActivity.this, PackageDetail.class);

                intent.putExtra("id", selected.id);
                intent.putExtra("name", selected.name);
                intent.putExtra("price", selected.price);
                intent.putExtra("rating", selected.rating);

                intent.putExtra("userID", userId);
                startActivity(intent);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_package_list, menu);

        return true;
    }

    /**
     * @return
     */
    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = null;
        if (item.getItemId() == R.id.menu1) {
            //kalau menu 1 di click
            intent = new Intent(this, MapsActivity.class);
        }else if (item.getItemId() == R.id.menu2){
            //kalau menu 2 di click

            intent = new Intent (this, PurchaseActivity.class);
            intent.putExtra("userID", userId);
        }
        startActivity(intent);

        return super.onOptionsItemSelected(item);
    }
}
