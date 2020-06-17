package com.qbase.locationability;


import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.SystemClock;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends PermissionActivity implements ILocationListener {

    TextView textView;
    PowerManager.WakeLock wakeLock;
    PendingIntent pIntent;
    AlarmManager alarmManager;

    @SuppressLint("InvalidWakeLockTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.saveLog("---------- MainActivity onCreate ----------");
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textResult);
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());

        startService(new Intent(this, LocalService.class));
        registerReceiver(mBroadcastReceiver, new IntentFilter(Intent.ACTION_SCREEN_OFF));
        registerReceiver(mBroadcastReceiver, new IntentFilter(Intent.ACTION_SCREEN_ON));
        registerReceiver(mBroadcastReceiver, new IntentFilter(Intent.ACTION_USER_PRESENT));

        PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "mywakrlock");

        //通过AlarmManager定时启动广播
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//        //从开机到现在的毫秒书（手机睡眠(sleep)的时间也包括在内
//        long triggerAtTime = SystemClock.elapsedRealtime() + 5000;
        Intent i = new Intent(this, AlarmReceive.class);
        pIntent = PendingIntent.getBroadcast(this, 0, i, PendingIntent.FLAG_CANCEL_CURRENT );
    }

    public void onStartLocation(View view) {
        LocationHelper.getInstance().startLocation(this.getApplicationContext());
//        LocationHelper.getInstance().setLocationListener(this);
    }

    public void onStopLocation(View view) {

    }

    @Override
    public void onLocationSuccess(SignPositionBean data) {
        if (data == null) {
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("******************************");
        stringBuffer.append("\n");
        stringBuffer.append("LocationType : ");
        stringBuffer.append(data.getLocationType());
        stringBuffer.append("Address : ");
        stringBuffer.append(data.getAddress());
        stringBuffer.append("\n");
        textView.append(stringBuffer.toString());
    }

    private void workReceiver() {
        wakeLock.acquire(10*60*1000L /*10 minutes*/);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 5000, pIntent);
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (!TextUtils.isEmpty(action)) {
                switch (action) {
                    case Intent.ACTION_SCREEN_OFF:
                        Log.e("----------","屏幕关闭，变黑");
                        LogUtil.saveLog("屏幕关闭，变黑");
                        workReceiver();
//                        startService(new Intent(getApplicationContext(), AlarmService.class));
                        break;
                    case Intent.ACTION_SCREEN_ON:
                        Log.e("----------","屏幕开启，变亮");
                        LogUtil.saveLog("屏幕开启，变亮");
//                        LocationHelper.getInstance().startLocation(context);
                        if(alarmManager!=null){
                            alarmManager.cancel(pIntent);
                        }
                        break;
                    case Intent.ACTION_USER_PRESENT:
                        LogUtil.saveLog("解锁成功");
                        Log.e("----------","解锁成功");
//                        LocationHelper.getInstance().startLocation(context);
                        if(alarmManager!=null){
                            alarmManager.cancel(pIntent);
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    };

    public void onSecondActivity(View view) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.saveLog("----------MainActivity onDestroy----------");

    }
}
