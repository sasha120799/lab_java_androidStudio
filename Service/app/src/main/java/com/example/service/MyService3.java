package com.example.service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MyService3 extends Service {
    final String LOG_TAG = "myLogs";
    private final int NOTIFICATION_ID = 128;
    private final String NOTIFICATION_CHANNEL_ID = "256";
    NotificationManager nm;

    public void onCreate(){
        super.onCreate();
        Log.d(LOG_TAG,"onCreate");
        nm=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
    }

    public int onStartCommand(Intent intent, int flags, int startId){
        Log.d(LOG_TAG,"onStartCommand");
        someTask();
        return super.onStartCommand(intent,flags,startId);
    }

    public void onDestroy(){
        super.onDestroy();
        Log.d(LOG_TAG,"onDestroy");
    }
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(LOG_TAG,"onBind");
        return null;
    }
    @TargetApi(Build.VERSION_CODES.O)
    void someTask(){
        LocalDate nowDate = LocalDate.now();
        final Calendar calendar = new GregorianCalendar(nowDate.getYear(),nowDate.getMonthValue()-1,nowDate.getDayOfMonth(),MainActivity.hourAlarm,MainActivity.minAlarm);
        LocalTime nowTime = LocalTime.now();
        LocalTime time = LocalTime.of(MainActivity.hourAlarm,MainActivity.minAlarm);
        if (time.getLong(ChronoField.MINUTE_OF_DAY)<nowTime.getLong(ChronoField.MINUTE_OF_DAY))calendar.add(Calendar.DAY_OF_YEAR,1);

        new Thread(new Runnable() {
            @Override
            public void run(){
                try {
                    TimeUnit.MILLISECONDS.sleep(calendar.getTimeInMillis()-System.currentTimeMillis());
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                sendNotif();
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
                .setContentTitle("Бзззззз");

        Log.d(LOG_TAG,"Где уведомление?");

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            NotificationChannel nChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "TheOnlyChannel", NotificationManager.IMPORTANCE_HIGH);
            nm.createNotificationChannel(nChannel);
            builder.setChannelId(NOTIFICATION_CHANNEL_ID);

        }
        Notification notification = builder.build();
        notification.flags |=Notification.DEFAULT_VIBRATE;
        notification.flags |=Notification.FLAG_AUTO_CANCEL;
        notification.flags |=Notification.DEFAULT_SOUND;
        nm.notify(NOTIFICATION_ID,notification);
    }
}
