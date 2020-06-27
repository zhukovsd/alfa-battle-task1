package com.zhukovsd.alfabattle.task1.atms;

public class Model {

    private String deviceId;
    private String latitude;
    private String longitude;
    private String city;
    private String location;
    private boolean payments;

    public Model() {
    }

    public Model(String deviceId, String latitude, String longitude, String city, String location, boolean payments) {
        this.deviceId = deviceId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
        this.location = location;
        this.payments = payments;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isPayments() {
        return payments;
    }

    public void setPayments(boolean payments) {
        this.payments = payments;
    }

}
