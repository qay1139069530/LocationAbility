package com.qbase.locationability;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;

public class AlarmService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("----------","--AlarmService onCreate--");
        LogUtil.saveLog("---------- AlarmService onCreate----------");
    }

    /**
     * 调用Service都会执行到该方法
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("----------"," AlarmService onStartCommand----------");
        LogUtil.saveLog("---------- AlarmService onStartCommand----------");
        LocationHelper.getInstance().startLocation(getApplicationContext());
        //taskTime = 10 * 1000;
        //通过AlarmManager定时启动广播
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        //从开机到现在的毫秒书（手机睡眠(sleep)的时间也包括在内
        long triggerAtTime = SystemClock.elapsedRealtime() + 10;
        Intent i = new Intent(this, AlarmReceive.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(this, 0, i, 0);
        assert alarmManager != null;
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pIntent);
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 服务销毁时的回调
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
