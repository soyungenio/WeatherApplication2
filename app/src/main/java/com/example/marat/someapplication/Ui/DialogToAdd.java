package com.example.marat.someapplication.Ui;


import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;

public class DialogToAdd implements DialogInterface.OnClickListener {

    final String LOG_TAG = "myLogs";
    AlertDialog.Builder adb;

    AppCompatActivity activity;
    EditText cityName;


    DialogToAdd(AppCompatActivity activity){
        adb = new android.support.v7.app.AlertDialog.Builder(activity)
                .setTitle("City name").setPositiveButton("ok", this)
                .setNegativeButton("cancel", this);
        cityName = new EditText(activity);

        LinearLayout ll=new LinearLayout(activity);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.addView(cityName);
        adb.setView(ll);
        adb.create();

        this.activity = activity;
    }
    public void show(){
        adb.show();
    }
    @Override
    public void onClick(DialogInterface dialog, int which) {
        int i = 0;
        switch (which) {
            case Dialog.BUTTON_POSITIVE:
                break;
            case Dialog.BUTTON_NEGATIVE:
                break;
        }
        if (i > 0)
            Log.d(LOG_TAG, "Dialog 2: " + "i > 0");
    }

}
