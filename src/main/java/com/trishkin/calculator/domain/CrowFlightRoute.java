package com.trishkin.calculator.domain;

import com.trishkin.calculator.services.RouteService;

public class CrowFlightRoute implements Route{

    private String fromCity;
    private String toCity;
    private Float distance;

    public CrowFlightRoute(String fromCity, String toCity, Float distance) {
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.distance = distance;
    }

    public String getFromCity() {
        return fromCity;
    }

    public String getToCity() {
        return toCity;
    }

    public Float getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        return "CrowFlightRoute{" +
                "fromCity='" + fromCity + '\'' +
                ", toCity='" + toCity + '\'' +
                ", distance=" + distance +
                '}';
    }
}
