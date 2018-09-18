package com.example.calum.teethtimer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.util.Log;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i(TAG, "Showing alarmed notification");

        Notifications notifications = new Notifications(context);

        notifications.createNotification("Teeth Timer", "Time to brush your teeth.");
    }

}
