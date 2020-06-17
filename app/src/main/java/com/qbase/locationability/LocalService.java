package com.qbase.locationability;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationManagerCompat;

import androidx.core.app.NotificationCompat;

import java.util.Timer;
import java.util.TimerTask;


public class LocalService extends Service {
    private int taskTime = 30 * 1000;// 毫秒数 8*60*60*1000;// 这是8小时的毫秒数
    private Context context;
    private Timer mTimer = null;
    private TimerTask mTimerTask = null;
    private boolean isAddNotification = false;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            try {
                startService(new Intent(LocalService.this, RemoteService.class));
                bindService(new Intent(LocalService.this, RemoteService.class), connection, Context.BIND_IMPORTANT);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public LocalService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            startService(new Intent(LocalService.this, RemoteService.class));
            bindService(new Intent(LocalService.this, RemoteService.class), connection, Context.BIND_IMPORTANT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        onTimerTask();
        buildNotification();
        return START_STICKY;
    }

    private void onTimerTask() {
        try {
            if (context == null) {
                context = getApplicationContext();
            }
            taskTime = 5 * 1000;
            if (mTimer == null) {
                mTimer = new Timer();
            } else {
                mTimer.cancel();
            }
            if (mTimerTask == null) {
                mTimerTask = new TimerTask() {
                    @Override
                    public void run() {
                        onReportData(); //要做的事情的一个方法
                    }
                };
            } else {
                mTimerTask.cancel();
            }

            mTimer.schedule(mTimerTask, 1000, taskTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onReportData() {
        String text = "LocalService onReportData";
        LogUtil.saveLog(text);
        if (!LocationHelper.getInstance().isStart()) {
            LocationHelper.getInstance().startLocation(context);
        }
        LocationHelper.getInstance().setLocationListener(new ILocationListener() {
            @Override
            public void onLocationSuccess(SignPositionBean data) {
                String text = "onLocationSuccess" + LogUtil.getLocationString(data);
                LogUtil.saveLog(text);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            LogUtil.saveLog("----------LocalService onDestroy----------");
            stopForeground(true);
            if (connection != null) {
                unbindService(connection);
            }
            if (mTimerTask != null) {
                mTimerTask = null;
            }
            if (mTimer != null) {
                mTimer.cancel();
                mTimer = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    private static class MyBinder extends MyAidlInterface.Stub {
        @Override
        public String getServiceName() {
            return LocalService.class.getName();
        }
    }

    private void buildNotification() {
        try {
            Log.e("-----------","buildNotification");
            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel("com.qbase.locationability", "Ability", NotificationManager.IMPORTANCE_HIGH);
                channel.setSound(null, null);
                channel.enableVibration(false);
                channel.setVibrationPattern(null);
                channel.setImportance(NotificationManager.IMPORTANCE_LOW);
                managerCompat.createNotificationChannel(channel);
            }
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "com.qbase.locationability")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentText("正在前台运行")
                    .setAutoCancel(true)
                    .setDefaults(Notification.FLAG_ONLY_ALERT_ONCE)
                    .setOngoing(false);
            Notification notification = builder.build();
            LocationHelper.getInstance().setBackGround(getApplicationContext(),notification);
//            startForeground(2001, notification);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
