package com.example.hansmartin.finalproject2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class PurchaseAdapter extends BaseAdapter {

    private Context ctx;

    public PurchaseAdapter(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return PurchaseActivity.vPurchases.size();
    }

    @Override
    public Object getItem(int position) {
        return PurchaseActivity.vPurchases.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;

        v = LayoutInflater.from(ctx).inflate(R.layout.item_purchase_list,null);

        TextView name = v.findViewById(R.id.name);
        TextView date = v.findViewById(R.id.date);
        TextView note = v.findViewById(R.id.note);

        Purchase p = PurchaseActivity.vPurchases.get(position);

        int day = p.date.get(Calendar.DAY_OF_MONTH);
        int month = p.date.get(Calendar.MONTH)+1;
        int year = p.date.get(Calendar.YEAR);

        String sDate = day+"-"+month+"-"+year;

        name.setText("Package Name: " + p.name);
        date.setText("Reservation Date: " + sDate);
        note.setText("Additional Notes: " + p.note);

        return v;
    }
}
