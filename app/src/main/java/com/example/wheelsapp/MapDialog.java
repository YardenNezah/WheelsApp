package com.example.wheelsapp;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

public class MapDialog extends AlertDialog  {

    protected MapDialog(Context context) {
        super(context);
        View v = LayoutInflater.from(context).inflate(R.layout.activity_maps_business,null,false);
        setView(v);
    }
}
