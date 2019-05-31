package com.example.service;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

public class MainActivity extends AppCompatActivity {

    static public TextView Min;
    static public TextView Sec;
    static public EditText TimerMin;
    static public EditText TimerSec;
    static public Button startSec;
    static public Button startTimer;
    TimePicker timePicker;
    TextView textView;
    static public int hourAlarm;
    static public int minAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Min = findViewById(R.id.textView);
        Sec = findViewById(R.id.textView2);
        TimerMin = findViewById(R.id.editText3);
        TimerSec = findViewById(R.id.editText4);
        timePicker = findViewById(R.id.timePicker);
        textView = findViewById(R.id.textView3);
        startSec = findViewById(R.id.button);
        startTimer = findViewById(R.id.button3);
    }

    public void ClickStart(View view){
        startService(new Intent(this,MyService.class));
    }
    public void ClickStop(View view){
        stopService(new Intent(this,MyService.class));
    }
    public void ClickTimer(View view){
        if (TimerSec.getText().toString().equals(""))
            TimerSec.setText("0");
        if (TimerMin.getText().toString().equals(""))
            TimerMin.setText("0");
        if (Integer.parseInt(TimerSec.getText().toString())>59)
            TimerSec.setText("59");
        if (Integer.parseInt(TimerMin.getText().toString())>59)
            TimerMin.setText("59");
        startService(new Intent(this,MyService2.class));
    }
    public void ClickStopTimer(View view){
        stopService(new Intent(this,MyService2.class));
    }
    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void ClickStartAlarm(View view){
        hourAlarm = timePicker.getHour();
        minAlarm =timePicker.getMinute();
        textView.setText("Будильник включен на"+String.valueOf(hourAlarm)+" : "+String.valueOf(minAlarm));
        startService(new Intent(this,MyService3.class));
    }
    public void ClickStopAlarm(View view){
        textView.setText("Будильник выключен");
        stopService(new Intent(this,MyService3.class));
    }
}
