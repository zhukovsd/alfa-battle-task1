package com.zhukovsd.alfabattle.task1.alfabankapi.atm.model;

import java.security.Provider;

public class AtmModel {

    String deviceId;
    Coordinates coordinates;
    Address address;
    Services services;

    public AtmModel() {
    }

    public AtmModel(String deviceId, Coordinates coordinates, Address address, Services services) {
        this.deviceId = deviceId;
        this.coordinates = coordinates;
        this.address = address;
        this.services = services;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Services getServices() {
        return services;
    }

    public void setServices(Services services) {
        this.services = services;
    }

    public static class Coordinates {
        String longitude;
        String latitude;

        public Coordinates() {
        }

        public Coordinates(String longitude, String latitude) {
            this.longitude = longitude;
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }
    }

    public  static class Services {
        String payments;

        public Services() {
        }

        public Services(String payments) {
            this.payments = payments;
        }

        public String getPayments() {
            return payments;
        }

        public void setPayments(String payments) {
            this.payments = payments;
        }
    }

    public  static class Address {
        String city;
        String location;

        public Address() {
        }

        public Address(String city, String location) {
            this.city = city;
            this.location = location;
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
    }

}
