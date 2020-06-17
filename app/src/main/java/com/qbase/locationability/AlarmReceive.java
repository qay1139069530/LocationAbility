package com.qbase.locationability;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;


public class AlarmReceive extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //循环启动Service
        try {
            Log.e("----------","--AlarmReceive--");
            LogUtil.saveLog("----------AlarmReceive----------");
            Intent i = new Intent(context, AlarmService.class);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                context.startForegroundService(intent);
//            } else {
                context.startService(i);
//            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.saveLog(e.getMessage());
        }
    }
}
