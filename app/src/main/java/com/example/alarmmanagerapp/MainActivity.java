package com.example.alarmmanagerapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Button hour_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hour_btn=(Button)findViewById(R.id.hour_btn);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Long parseDateLong(String myDate){

        long millisSinceEpoch = LocalDateTime.parse(myDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                .atOffset(ZoneOffset.UTC)
                .toInstant()
                .toEpochMilli();
        return millisSinceEpoch;

    }

    public static Long parseLong(String fecha)
    {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date fechaDate = null;
        long milisegs = 0;
        try {
            fechaDate = formato.parse(fecha);
            milisegs = fechaDate.getTime();
            System.out.println(milisegs);
        }
        catch (ParseException ex)
        {
            System.out.println(ex);
        }
        return milisegs;
    }

    public void popTimePicker(View view) {


        Calendar mcurrentTime = Calendar.getInstance(); //para obtener la fecha y hora actual Calendar
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);

        Log.i("selectedHour", "mcurrentTime " + mcurrentTime);
        Log.i("selectedHour", "hour " + hour);
        Log.i("selectedHour", "minute " + minute);

        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @SuppressLint("StringFormatInvalid")
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String finalHour, finalMinute;

                Log.i("selectedHour", "selectedHour " + selectedHour);

                String[] date = new String[3];

                date[0] = "2022-01-11 13:46";
                date[1] = "2022-01-11 13:47";
                date[2] = "2022-01-12 13:48";

                AlarmManager[] alarmManager=new AlarmManager[date.length];
                ArrayList intentArray = new ArrayList<PendingIntent>();
                for(int i=0; i<date.length; i++){
                    Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
                    PendingIntent pi=PendingIntent.getBroadcast(MainActivity.this, i ,intent, 0);

                    Log.i("dates", "dates"+date[i]);

                    alarmManager[i] = (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager[i].set(AlarmManager.RTC_WAKEUP, parseDateLong(date[i]),pi);

                    intentArray.add(pi);

                    Utils.setAlarm(i+1, parseDateLong(date[i]), MainActivity.this);

                }


                  /*  finalHour = "" + selectedHour;
                    finalMinute = "" + selectedMinute;
                    if (selectedHour < 10) finalHour = "0" + selectedHour;
                    if (selectedMinute < 10) finalMinute = "0" + selectedMinute;
                    hour_fragment.setText(finalHour + ":" + finalMinute);
                    Log.i("finalHour ", ": " + finalHour);
                    Log.i("finalMinute " ,": " + finalMinute);

                    Calendar today = Calendar.getInstance();

                    today.set(Calendar.HOUR_OF_DAY, selectedHour);
                    today.set(Calendar.MINUTE, selectedMinute);
                    today.set(Calendar.SECOND, 0);

                    Log.i("TODAY " ,"TODAY_CALENDAR " + today);

                    SharedPreferences.Editor edit = settings.edit();
                    edit.putString("hour", finalHour);
                    edit.putString("minute", finalMinute);

                    //SAVE ALARM TIME TO USE IT IN CASE OF REBOOT
                    edit.putInt("alarmID", alarmID);
                    edit.putLong("alarmTime", today.getTimeInMillis());

                    edit.commit();*/

                Toast.makeText(MainActivity.this, getString(R.string.changed_to), Toast.LENGTH_LONG).show();

                //Toast.makeText(AddMedication.this, getString(R.string.changed_to, finalHour + ":" + finalMinute), Toast.LENGTH_LONG).show();

                //Utils.setAlarm(alarmID, today.getTimeInMillis(), AddMedication.this);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle(getString(R.string.select_time));
        mTimePicker.show();
    }


}