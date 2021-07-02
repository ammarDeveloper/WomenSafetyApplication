package com.applocstion.womensafetyapplication;

import android.location.Location;
import java.util.Date;

public class LocationDate {

    private int location_id;
    private String state;
    private String location;
    private String datetime;

    public LocationDate(int location_id, String state,  String location, String datetime) {
        this.location_id = location_id;
        this.state = state;
        this.location = location;
        this.datetime = datetime;
    }

    public int getLocation_id() {
        return location_id;
    }

    public void setLocation_id(int location_id) {
        this.location_id = location_id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDate(String datetime) {
        this.datetime = datetime;
    }
}
