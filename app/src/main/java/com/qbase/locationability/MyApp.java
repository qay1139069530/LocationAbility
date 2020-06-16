package com.qbase.locationability;

import androidx.multidex.MultiDexApplication;

public class MyApp extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        PathUtil.getInstance().initDirs("", "data", this);
    }
}
