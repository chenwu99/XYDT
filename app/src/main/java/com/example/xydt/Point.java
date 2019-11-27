package com.example.xydt;

public class Point {
    public double latitude;
    public double longitude;
    public String name;
    public int index;

    public Point(){

    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

    public Point(double latitude, double longitude, String name, int index) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name=name;
        this.index=index;
    }
}
