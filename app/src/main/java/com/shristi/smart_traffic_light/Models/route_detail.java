package com.shristi.smart_traffic_light.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class route_detail {
    @SerializedName("destinationDistance")
    @Expose
    private Double destinationDistance;

    public Double getDestinationDistance() {
        return destinationDistance;
    }

    public void setDestinationDistance(Double destinationDistance) {
        this.destinationDistance = destinationDistance;
    }

}
