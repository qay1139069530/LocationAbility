package com.qbase.locationability;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends PermissionActivity implements ILocationListener{

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textResult);
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());

        registerReceiver(mBroadcastReceiver, new IntentFilter(Intent.ACTION_SCREEN_OFF));
        registerReceiver(mBroadcastReceiver, new IntentFilter(Intent.ACTION_SCREEN_ON));
        registerReceiver(mBroadcastReceiver, new IntentFilter(Intent.ACTION_USER_PRESENT));
    }

    public void onStartLocation(View view) {
        LocationHelper.getInstance().startLocation(this.getApplicationContext());
//        LocationHelper.getInstance().setLocationListener(this);
    }

    public void onStopLocation(View view) {

    }

    @Override
    public void onLocationSuccess(SignPositionBean data) {
        if(data==null){
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


    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (!TextUtils.isEmpty(action)) {
                switch (action) {
                    case Intent.ACTION_SCREEN_OFF:
                        LogUtil.saveLog("屏幕关闭，变黑");
                        LocationHelper.getInstance().startLocation(context);
                        break;
                    case Intent.ACTION_SCREEN_ON:
                        LogUtil.saveLog("屏幕开启，变亮");
                        LocationHelper.getInstance().startLocation(context);
                        break;
                    case Intent.ACTION_USER_PRESENT:
                        LogUtil.saveLog("解锁成功");
                        LocationHelper.getInstance().startLocation(context);
                        break;
                    default:
                        break;
                }
            }
        }
    };
}
