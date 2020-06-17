package com.qbase.locationability;

import android.app.Notification;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.AMapLocationQualityReport;


/**
 * Created by dell on 2016/8/12.
 */
public class LocationHelper {

    private static final String TAG = "--LocationHelper--";

    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;

    private AMapLocation mAMapLocation;

    private ILocationListener listener;

    private int indexCount = 1;
    private boolean isStartLocation = false;

    private LocationHelper() {
    }

    public static LocationHelper getInstance() {
        return LocationHelperHolder.sInstance;
    }

    private static class LocationHelperHolder {
        private static final LocationHelper sInstance = new LocationHelper();
    }

    public void initLocation(Context context) {
        if (locationClient == null) {
            locationClient = new AMapLocationClient(context.getApplicationContext());
            locationOption = getDefaultOption();
            //设置定位参数
            locationClient.setLocationOption(locationOption);
            // 设置定位监听
            locationClient.setLocationListener(locationListener);
        }
        mAMapLocation = null;
        onRefreshLocation(context);
    }

    /**
     * 刷新定位,定位前要判断是否已经将GPS打开
     */
    public void onRefreshLocation(Context context) {
        if (locationClient == null) {
            initLocation(context);
        }
        // 启动定位
        startLocation(context);
    }

    /**
     * 启动定位
     */
    public void startLocation(Context context) {
        if (isStartLocation) {
            return;
        }
        isStartLocation = true;
        if (locationClient != null) {
            if (!locationClient.isStarted()) {
                LogUtil.saveLog("---------- startLocation ----------");
                locationClient.startLocation();
            }
            isStartLocation = false;
        } else {
            initLocation(context);
        }
    }

    public void setBackGround(Context context, Notification notification) {
        if (locationClient == null) {
            locationClient = new AMapLocationClient(context.getApplicationContext());
            if (locationOption == null) {
                locationOption = getDefaultOption();
            }
        }
        locationClient.enableBackgroundLocation(2001, notification);
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        // 设置定位监听
        locationClient.setLocationListener(locationListener);
        mAMapLocation = null;
        onRefreshLocation(context);
    }

    /**
     * 停止定位
     */
    public void stopLocation() {
        if (locationClient != null) {
            locationClient.stopLocation();
        }
    }

    public boolean isStart() {
        return locationClient != null && locationClient.isStarted();
    }

    public void setLocationListener(ILocationListener locationListener) {
        this.listener = locationListener;
    }

    /**
     * 默认的定位参数
     */
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
//        mOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.Transport);
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(false); //可选，设置是否使用缓存定位，默认为true
        mOption.setGpsFirst(true);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        return mOption;
    }

    /**
     * 获取定位数据
     */
    public synchronized SignPositionBean getLocationData() {
        if (mAMapLocation == null) {
            return null;
        }
        SignPositionBean mSignPosition = new SignPositionBean();
        //纬度
        mSignPosition.setLatitude(mAMapLocation.getLatitude());
        //经度
        mSignPosition.setLongitude(mAMapLocation.getLongitude());
        //市
        mSignPosition.setCity(mAMapLocation.getCity());
        // 定位方式
        mSignPosition.setLocationType(mAMapLocation.getLocationType());
        //精度-米
        mSignPosition.setAccuracy(mAMapLocation.getAccuracy());
        //星数
        mSignPosition.setSatellites(mAMapLocation.getSatellites());
        //定位时间
        mSignPosition.setTime(TimeUtil.getCurrentTime());
        //地址
        String address_str = mAMapLocation.getAddress();
        mSignPosition.setAddress(address_str);
        if (!TextUtils.isEmpty(address_str)) {
            if (TextUtils.isEmpty(mAMapLocation.getProvince()) && address_str.contains(mAMapLocation.getProvince())) {
                address_str = address_str.replace(mAMapLocation.getProvince(), "");
            }
            if (TextUtils.isEmpty(mAMapLocation.getCity()) && address_str.contains(mAMapLocation.getCity())) {
                address_str = address_str.replace(mAMapLocation.getCity(), "");
            }
            if (address_str.contains("江苏省南京市")) {
                address_str = address_str.replace("江苏省南京市", "");
            }
            //定位完成的时间
            mSignPosition.setAddress(address_str);
        }
        return mSignPosition;
    }

    private AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation location) {
            if (null != location && location.getErrorCode() == 0) {
                try {
//                    int locationType = location.getLocationType();
//                    String text = "定位成功 ： " +
//                            "  type : " + locationType +
//                            "  -- address : " + location.getAddress();
//                    Log.e("---------", text);
//                    LogUtil.saveLog(text);
//                    if (gpsFirst) {
//                        if (locationType == AMapLocation.LOCATION_TYPE_GPS) {
                    mAMapLocation = location;
                    if (listener != null) {
                        listener.onLocationSuccess(getLocationData());
                    }
//                            listener = null;
//                            gpsFirst = false;
//                            indexCount = 1;
//                        } else {
//                            if (indexCount % 4 == 0) {
//                                mAMapLocation = location;
//                                if (listener != null) {
//                                    listener.onLocationSuccess(getLocationData());
//                                }
//                                listener = null;
//                                gpsFirst = false;
//                                indexCount = 1;
//                            }
//                            indexCount = indexCount + 1;
//                        }
//                    } else {
//                        if (mAMapLocation == null) {
//                            mAMapLocation = location;
//                        }
//                        if (locationType == AMapLocation.LOCATION_TYPE_GPS) {
//                            mAMapLocation = location;
//                        }
//                        if (listener != null) {
//                            listener.onLocationSuccess(getLocationData());
//                            if (disposable) {
//                                listener = null;
//                            }
//                        }
//                    }
////                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
//            if (null != location) {
//                StringBuffer sb = new StringBuffer();
//                //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
//                if(location.getErrorCode() == 0){
//                    sb.append("定位成功" + "\n");
//                    sb.append("定位类型: " + location.getLocationType() + "\n");
//                    sb.append("经    度    : " + location.getLongitude() + "\n");
//                    sb.append("纬    度    : " + location.getLatitude() + "\n");
//                    sb.append("精    度    : " + location.getAccuracy() + "米" + "\n");
//                    sb.append("提供者    : " + location.getProvider() + "\n");
//
//                    sb.append("速    度    : " + location.getSpeed() + "米/秒" + "\n");
//                    sb.append("角    度    : " + location.getBearing() + "\n");
//                    // 获取当前提供定位服务的卫星个数
//                    sb.append("星    数    : " + location.getSatellites() + "\n");
//                    sb.append("国    家    : " + location.getCountry() + "\n");
//                    sb.append("省            : " + location.getProvince() + "\n");
//                    sb.append("市            : " + location.getCity() + "\n");
//                    sb.append("城市编码 : " + location.getCityCode() + "\n");
//                    sb.append("区            : " + location.getDistrict() + "\n");
//                    sb.append("区域 码   : " + location.getAdCode() + "\n");
//                    sb.append("地    址    : " + location.getAddress() + "\n");
//                    sb.append("兴趣点    : " + location.getPoiName() + "\n");
//                    //定位完成的时间
//                    sb.append("定位时间: " + Utils.formatUTC(location.getTime(), "yyyy-MM-dd HH:mm:ss") + "\n");
//                } else {
//                    //定位失败
//                    sb.append("定位失败" + "\n");
//                    sb.append("错误码:" + location.getErrorCode() + "\n");
//                    sb.append("错误信息:" + location.getErrorInfo() + "\n");
//                    sb.append("错误描述:" + location.getLocationDetail() + "\n");
//                }
//                sb.append("***定位质量报告***").append("\n");
//                sb.append("* WIFI开关：").append(location.getLocationQualityReport().isWifiAble() ? "开启":"关闭").append("\n");
//                sb.append("* GPS状态：").append(getGPSStatusString(location.getLocationQualityReport().getGPSStatus())).append("\n");
//                sb.append("* GPS星数：").append(location.getLocationQualityReport().getGPSSatellites()).append("\n");
//                sb.append("* 网络类型：" + location.getLocationQualityReport().getNetworkType()).append("\n");
//                sb.append("* 网络耗时：" + location.getLocationQualityReport().getNetUseTime()).append("\n");
//                sb.append("****************").append("\n");
//                //定位之后的回调时间
//                sb.append("回调时间: " + Utils.formatUTC(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss") + "\n");
//
//                //解析定位结果，
//                String result = sb.toString();
//                tvResult.setText(result);
//            } else {
//                tvResult.setText("定位失败，loc is null");
//            }
        }
    };
}
