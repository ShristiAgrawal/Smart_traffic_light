package com.shristi.smart_traffic_light.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class location {


    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("lng")
    @Expose
    private Double lng;


    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
