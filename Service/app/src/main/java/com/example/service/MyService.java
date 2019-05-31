package com.example.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MyService extends Service {
    boolean flag = true;
    final String LOG_TAG = "myLogs";

    public void onCreate(){
        super.onCreate();
        flag = true;
        Log.d(LOG_TAG,"onCreate");
    }

    public int onStartCommand(Intent intent, int flags, int startId){
        Log.d(LOG_TAG,"onStartCommand");
        if (MainActivity.startSec.getText().toString().equals("Pause")){
            flag=false;
            MainActivity.startSec.setText("Resume");
        }else
        if (MainActivity.startSec.getText().toString().equals("Resume")){
            MainActivity.startSec.setText("Pause");
            flag=true;
        }else
        if (MainActivity.startSec.getText().toString().equals("Start")){
            MainActivity.startSec.setText("Pause");
        }
        someTask();
        return super.onStartCommand(intent,flags,startId);
    }
    TextView Sec = MainActivity.Sec;
    TextView Min = MainActivity.Min;

    public void onDestroy(){
        super.onDestroy();
        MainActivity.startSec.setText("Start");
        Sec.setText("0");
        Min.setText("0");
        flag = false;
        Log.d(LOG_TAG,"onDestroy");
    }
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(LOG_TAG,"onBind");
        return null;
    }

    void someTask(){
        new Thread(new Runnable() {
            @Override
                public void run(){
                int timeSec=Integer.parseInt(Sec.getText().toString()), timeMin=Integer.parseInt(Min.getText().toString());
                    for (int i=0; flag; i++){
                        timeSec ++;
                        if (timeSec==60){
                            timeSec=0;
                            timeMin++;
                            Min.setText(String.valueOf(timeMin));
                        }
                        Sec.setText(String.valueOf(timeSec));
                        Log.d(LOG_TAG,"i = "+i);
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                }
        }).start();
    }
}
