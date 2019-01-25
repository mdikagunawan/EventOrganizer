package com.example.hansmartin.finalproject2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PackageAdapter extends BaseAdapter {

    ArrayList<Package> vPackage;
    private Context ctx;

    public PackageAdapter(ArrayList<Package> vPackage, Context ctx) {

        this.vPackage = vPackage;
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return MainActivity.vPackage.size();
    }

    @Override
    public Object getItem(int position) {
        return MainActivity.vPackage.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;

        v = LayoutInflater.from(ctx).inflate(R.layout.item_list,null);

        TextView pID = v.findViewById(R.id.pID);
        TextView pName = v.findViewById(R.id.pName);
        TextView pPrice = v.findViewById(R.id.pPrice);
        TextView pRating = v.findViewById(R.id.pRating);

        Package p = MainActivity.vPackage.get(position);

        pID.setText("ID: "+p.id);
        pName.setText("Name: " + p.name);
        pPrice.setText("Price: " + p.price);
        pRating.setText("Rating: "+ p.rating);

        return v;
    }
}
