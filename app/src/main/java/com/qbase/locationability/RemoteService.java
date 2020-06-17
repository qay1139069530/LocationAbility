package com.qbase.locationability;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Process;

import androidx.annotation.Nullable;

public class RemoteService extends Service {

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            try {
                startService(new Intent(RemoteService.this, LocalService.class));
                bindService(new Intent(RemoteService.this, LocalService.class), connection, Context.BIND_IMPORTANT);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public RemoteService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
            try {
                bindService(new Intent(RemoteService.this, LocalService.class), connection, Context.BIND_IMPORTANT);
            } catch (Exception e) {
                e.printStackTrace();
            }
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    @Override
    public void onDestroy() {
        try {
            LogUtil.saveLog("----------RemoteService onDestroy----------");

            if (connection != null) {
                unbindService(connection);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    private static class MyBinder extends MyAidlInterface.Stub {
        @Override
        public String getServiceName() {
            return RemoteService.class.getName();
        }
    }
}
