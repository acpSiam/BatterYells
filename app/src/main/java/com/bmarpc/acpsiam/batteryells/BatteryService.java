package com.bmarpc.acpsiam.batteryells;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.io.IOException;

public class BatteryService extends Service {

    private static final String CHANNEL_ID = "ForegroundServiceChannel";
    private static final int NOTIFICATION_ID = 1;
    private int desiredBatteryChargedLevel;
    private int desiredBatteryLowLevel;
    private boolean batteryLowAlarmOn;
    private boolean batteryChargedAlarmOn;
    private BroadcastReceiver batteryReceiver;
    private boolean isCharging = false;
    private boolean isAlarmPlaying = false; // Flag to track alarm status
    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();

        batteryReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                SharedPreferences sharedPreferences = context.getSharedPreferences(getString(R.string.SHARED_PREFERENCE_MAIN), Context.MODE_PRIVATE);
                desiredBatteryChargedLevel = sharedPreferences.getInt(getString(R.string.BATTERY_CHARGED_ALARM_INT), 90);
//                desiredBatteryChargedLevel = 97;
                batteryChargedAlarmOn = sharedPreferences.getBoolean(getString(R.string.BATTERY_CHARGED_ALARM_ON_BOOL), false);
                desiredBatteryLowLevel = sharedPreferences.getInt(getString(R.string.BATTERY_LOW_ALARM_INT), 25);
                batteryLowAlarmOn = sharedPreferences.getBoolean(getString(R.string.BATTERY_LOW_ALARM_ON_BOOL), false);



                int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                int batteryLevel = (int) ((level / (float) scale) * 100);

//                int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
//                isCharging = status == BatteryManager.BATTERY_PLUGGED_USB || status == BatteryManager.BATTERY_STATUS_FULL;

                int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);
                boolean isCharging = (plugged == BatteryManager.BATTERY_PLUGGED_AC || plugged == BatteryManager.BATTERY_PLUGGED_WIRELESS);



                Log.d("aa", batteryLevel + "  " + batteryChargedAlarmOn + "  " + desiredBatteryChargedLevel
                        + "  " + isCharging + "  " + (batteryLevel >= desiredBatteryChargedLevel));

                if (batteryChargedAlarmOn){
                    if (isCharging && batteryLevel >= desiredBatteryChargedLevel) {
                        startAlarm(context);
                        Log.d("aa", "on");
                    } else {
                        stopAlarm();
                        Log.d("aa", "stop");
                    }
                }

                if (batteryLowAlarmOn) {
                    if (!isCharging && batteryLevel <= desiredBatteryLowLevel && plugged != BatteryManager.BATTERY_PLUGGED_USB) {
                        startAlarm(context);
                    } else {
                        stopAlarm();
                    }
                }
            }
        };

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryReceiver, filter);


    }



    @SuppressLint("ForegroundServiceType")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createNotificationChannel();

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Battery Yells")
                .setContentText(getString(R.string.battery_yells_slogun))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {

            notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
            startForeground(NOTIFICATION_ID, notificationBuilder.build());

        }


        return START_STICKY;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Battery Level Alerts",
                    NotificationManager.IMPORTANCE_HIGH
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }



    public void startAlarm(Context context) {
        if (!isAlarmPlaying) {
            Log.d("aa", "play");
            isAlarmPlaying = true;
            Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            if (alarmUri == null) {
                alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            }

            mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(context, alarmUri);
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.setOnCompletionListener(mp -> {
                mediaPlayer.reset();
                mediaPlayer.release();
                mediaPlayer = null;
                isAlarmPlaying = false;
            });
            mediaPlayer.start();
        }
    }






    public void stopAlarm() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
            isAlarmPlaying = false;
        }
    }




    @Override
    public void onDestroy() {
        super.onDestroy();
        if (batteryReceiver != null) {
            unregisterReceiver(batteryReceiver);
        }
        stopAlarm(); // Stop the alarm and release resources
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
