package com.example.alarmmanagerapp;

import static android.content.Context.ALARM_SERVICE;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public class Utils {
    public static void setAlarm(int i, Long timestamp, Context ctx) {
        AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(ALARM_SERVICE);
        Intent alarmIntent = new Intent(ctx, AlarmReceiver.class);
        PendingIntent pendingIntent;
        final int id = (int) System.currentTimeMillis();
        pendingIntent = PendingIntent.getBroadcast(ctx, id, alarmIntent, PendingIntent.FLAG_ONE_SHOT); //solonse dispara una vez

        alarmIntent.setData((Uri.parse("custom://" +  System.currentTimeMillis())));
        alarmManager.set(AlarmManager.RTC_WAKEUP, timestamp, pendingIntent); // RTC_WAKEUP, activará la alarma segun la hora del reloj


        Log.i("ENTRA_UTILS", "timestamp"+ timestamp); //Current Time Stamp: 2021-03-06 17:24:57.107
        Log.i("ENTRA_UTILS", "timestamp_s"+ timestamp.toString());

    }
}
