package com.qbase.locationability;


import java.io.Serializable;

/**
 * 签到
 */
public class SignPositionBean implements Serializable {
    /**
     * 默认序列化
     */
    private static final long serialVersionUID = 1L;
    private float accuracy;//精确度
    private String address;//地址
    private String city;//所在市
    private String district; //所有区县
    private double Latitude;//纬度
    private double Longitude;//经度
    private int locationType;//签到方式 0：GPS强,1:GPS弱，2：非GPS定位
    private int signrange;//签到范围 ：0：匹配，1：不匹配
    private int satellites;//星数
    private String time;//定位时间

    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public int getLocationType() {
        return locationType;
    }

    public void setLocationType(int locationType) {
        this.locationType = locationType;
    }

    public int getSignrange() {
        return signrange;
    }

    public void setSignrange(int signrange) {
        this.signrange = signrange;
    }

    public int getSatellites() {
        return satellites;
    }

    public void setSatellites(int satellites) {
        this.satellites = satellites;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}