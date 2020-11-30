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
    @SerializedName("destinationSignals")
    @Expose
    private ArrayList<location> destinationSignals;
    @SerializedName("hospitalSignals")
    @Expose
    private ArrayList<location> hospitalSignals;


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

    public ArrayList<location> getDestinationSignals() {
        return destinationSignals;
    }

    public void setDestinationSignals(ArrayList<location> destinationSignals) {
        this.destinationSignals = destinationSignals;
    }

    public ArrayList<location> getHospitalSignals() {
        return hospitalSignals;
    }

    public void setHospitalSignals(ArrayList<location> hospitalSignals) {
        this.hospitalSignals = hospitalSignals;
    }
}
