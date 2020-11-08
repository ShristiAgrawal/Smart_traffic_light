package com.shristi.smart_traffic_light.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class route_detail {
    @SerializedName("destinationWayPoints")
    @Expose
    private ArrayList<ArrayList<Double>> locations;
    @SerializedName("hospitalWayPoints")
    @Expose
    private ArrayList<ArrayList<Double>> hospitalWayPoints;
  //  "hospitalWayPoints"


    public ArrayList<ArrayList<Double>> getLocations() {
        return locations;
    }

    public void setLocations(ArrayList<ArrayList<Double>> locations) {
        this.locations = locations;
    }

    public ArrayList<ArrayList<Double>> getHospitalWayPoints() {
        return hospitalWayPoints;
    }

    public void setHospitalWayPoints(ArrayList<ArrayList<Double>> hospitalWayPoints) {
        this.hospitalWayPoints = hospitalWayPoints;
    }
}
