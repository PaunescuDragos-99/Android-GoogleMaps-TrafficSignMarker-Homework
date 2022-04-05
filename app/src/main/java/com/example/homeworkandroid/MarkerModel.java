package com.example.homeworkandroid;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class MarkerModel implements Serializable {
private String MarkerName;
private String MarkerInfo;
private String MarkerLatitude;
private String MarkerLongitude;

private String MarkerLocation;
@Exclude
private String MarkerKey;



    public MarkerModel(){

    }

    public String getMarkerName() {
        return MarkerName;
    }

    public void setMarkerName(String markerName) {
        MarkerName = markerName;
    }

    public String getMarkerInfo() {
        return MarkerInfo;
    }



    public void setMarkerInfo(String markerInfo) {
        MarkerInfo = markerInfo;
    }

    public String getMarkerLatitude() {
        return MarkerLatitude;
    }

    public void setMarkerLatitude(String markerLatitude) {
        MarkerLatitude = markerLatitude;
    }

    public String getMarkerLongitude() {
        return MarkerLongitude;
    }

    public void setMarkerLongitude(String markerLongitude) {
        MarkerLongitude = markerLongitude;
    }

    public String getMarkerLocation() {
        return MarkerLocation;
    }

    public void setMarkerLocation(String markerLocation) {
        MarkerLocation = markerLocation;
    }

    public String getMarkerKey() {
        return MarkerKey;
    }

    public void setMarkerKey(String markerKey) { MarkerKey = markerKey; }




}
