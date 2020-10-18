package com.shristi.smart_traffic_light.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class route_detail {
    @SerializedName("destinationWayPoints")
    @Expose
    private ArrayList<ArrayList<Double>> locations;


    public ArrayList<ArrayList<Double>> getLocations() {
        return locations;
    }

    public void setLocations(ArrayList<ArrayList<Double>> locations) {
        this.locations = locations;
    }
}
