package com.shristi.smart_traffic_light.Models;

import java.util.List;

public class RouteResponse {
    private int destinationTime;
    private int hospitalTime;
    private double destinationDistance;
    private double hospitalDistance;
    private String hospitalName;
    private double hospitalLat;
    private double hospitalLng;
    private long hospitalRouteId;
    private String destinationPolyline;
    private String hospitalPolyline;
    private List<List<Double>> destinationWayPoints;
    private List<List<Double>> hospitalWayPoints;
    private List<Signal> destinationSignals;
    private List<Signal> hospitalSignals;
    private List<Light> destinationLights;
    private List<Light> hospitalLights;

    private class Signal {
        private double lat;
        private double lng;
        private String location;

        @Override
        public String toString() {
            return "Signal{" +
                    "lat=" + lat +
                    ", lng=" + lng +
                    ", location='" + location + '\'' +
                    '}';
        }
    }

    private class Light {
        private double lat;
        private double lng;
        private String location;
        private int direction;

        @Override
        public String toString() {
            return "Light{" +
                    "lat=" + lat +
                    ", lng=" + lng +
                    ", location='" + location + '\'' +
                    ", direction=" + direction +
                    '}';
        }
    }

    public int getDestinationTime() {
        return destinationTime;
    }

    @Override
    public String toString() {
        return "RouteResponse{" +
                "destinationTime=" + destinationTime +
                ", hospitalTime=" + hospitalTime +
                ", destinationDistance=" + destinationDistance +
                ", hospitalDistance=" + hospitalDistance +
                ", hospitalName='" + hospitalName + '\'' +
                ", hospitalLat=" + hospitalLat +
                ", hospitalLng=" + hospitalLng +
                ", hospitalRouteId=" + hospitalRouteId +
                ", destinationPolyline='" + destinationPolyline + '\'' +
                ", hospitalPolyline='" + hospitalPolyline + '\'' +
                ", destinationWayPoints=" + destinationWayPoints +
                ", hospitalWayPoints=" + hospitalWayPoints +
                ", destinationSignals=" + destinationSignals +
                ", hospitalSignals=" + hospitalSignals +
                ", destinationLights=" + destinationLights +
                ", hospitalLights=" + hospitalLights +
                '}';
    }

    public int getHospitalTime() {
        return hospitalTime;
    }

    public double getDestinationDistance() {
        return destinationDistance;
    }

    public double getHospitalDistance() {
        return hospitalDistance;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public double getHospitalLat() {
        return hospitalLat;
    }

    public double getHospitalLng() {
        return hospitalLng;
    }

    public long getHospitalRouteId() {
        return hospitalRouteId;
    }

    public String getDestinationPolyline() {
        return destinationPolyline;
    }

    public String getHospitalPolyline() {
        return hospitalPolyline;
    }

    public List<List<Double>> getDestinationWayPoints() {
        return destinationWayPoints;
    }

    public List<List<Double>> getHospitalWayPoints() {
        return hospitalWayPoints;
    }

    public List<Signal> getDestinationSignals() {
        return destinationSignals;
    }

    public List<Signal> getHospitalSignals() {
        return hospitalSignals;
    }

    public List<Light> getDestinationLights() {
        return destinationLights;
    }

    public List<Light> getHospitalLights() {
        return hospitalLights;
    }
}
