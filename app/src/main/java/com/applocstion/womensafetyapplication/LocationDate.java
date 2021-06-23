package com.applocstion.womensafetyapplication;

import android.location.Location;
import java.util.Date;

public class LocationDate {

    private int location_id;
    private String location;
    private String datetime;

    public LocationDate(int location_id, String location, String datetime) {
        this.location_id = location_id;
        this.location = location;
        this.datetime = datetime;
    }

    public int getLocation_id() {
        return location_id;
    }

    public void setLocation_id(int location_id) {
        this.location_id = location_id;
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
