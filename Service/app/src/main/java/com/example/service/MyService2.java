package com.example.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class MyService2 extends Service {
    NotificationManager nm;
    private final int NOTIFICATION_ID = 127;
    private final String NOTIFICATION_CHANNEL_ID = "255";
    boolean flag = true;
    final String LOG_TAG = "myLogs";
    EditText Sec = MainActivity.TimerSec;
    EditText Min = MainActivity.TimerMin;

    public void onCreate(){
        super.onCreate();
        Log.d(LOG_TAG,"onCreate");

        nm=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
    }

    public int onStartCommand(Intent intent, int flags, int startId){
        Log.d(LOG_TAG,"onStartCommand");
        if (MainActivity.startTimer.getText().toString().equals("Pause")){
            Sec.setEnabled(true);
            Min.setEnabled(true);
            flag=false;
            MainActivity.startTimer.setText("Resume");
        }else
        if (MainActivity.startTimer.getText().toString().equals("Resume")){
            MainActivity.startTimer.setText("Pause");
            Sec.setEnabled(false);
            Min.setEnabled(false);
            flag=true;
        }else
        if (MainActivity.startTimer.getText().toString().equals("Timer")){
            Sec.setEnabled(false);
            Min.setEnabled(false);
            MainActivity.startTimer.setText("Pause");
        }
        someTask();
        return super.onStartCommand(intent,flags,startId);
    }

    public void onDestroy(){
        super.onDestroy();
        Sec.setEnabled(true);
        Min.setEnabled(true);
        flag = false;
        Log.d(LOG_TAG,"onDestroy");
        MainActivity.startTimer.setText("Timer");
        Sec.setText("");
        Min.setText("");
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
                for (int i=Integer.parseInt(MainActivity.TimerMin.getText().toString())*60+Integer.parseInt(MainActivity.TimerSec.getText().toString());i>0&&flag; i--){
                    timeSec--;
                    if (timeSec==0&&timeMin!=0){
                        timeSec=59;
                        timeMin--;
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
                if (flag){
                    sendNotif();
                    stopSelf();
                }
            }
        }).start();
    }

    void sendNotif(){
        Intent intent = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);
        Notification.Builder builder = new Notification.Builder(this)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.racing)
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(),R.drawable.racing))
                .setTicker("Время")
                .setWhen(System.currentTimeMillis())
                .setContentTitle("Таааааймер");


        Log.d(LOG_TAG,"Где уведомление?");

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            NotificationChannel nChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "TheOnlyChannel", NotificationManager.IMPORTANCE_HIGH);
            nm.createNotificationChannel(nChannel);
            builder.setChannelId(NOTIFICATION_CHANNEL_ID);

        }
        Notification notification = builder.build();
        notification.flags |=Notification.FLAG_AUTO_CANCEL;
        notification.flags |=Notification.DEFAULT_SOUND;
        nm.notify(NOTIFICATION_ID,notification);
    }
}
