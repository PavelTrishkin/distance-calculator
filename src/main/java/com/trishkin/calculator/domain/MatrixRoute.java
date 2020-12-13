package com.trishkin.calculator.domain;

import com.trishkin.calculator.services.RouteService;

import java.util.Map;
import java.util.Stack;

public class MatrixRoute implements Route {

    private String fromCity;
    private String toCity;
    private Map<Float, Stack<String>> routeMap;


    public MatrixRoute(String fromCity, String toCity, Map<Float, Stack<String>> routeMap) {
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.routeMap = routeMap;
    }


    public String getFromCity() {
        return fromCity;
    }

    public String getToCity() {
        return toCity;
    }

    public Map<Float, Stack<String>> getRouteMap() {
        return routeMap;
    }

    @Override
    public String toString() {
        return "MatrixRoute{" +
                "fromCity='" + fromCity + '\'' +
                ", toCity='" + toCity + '\'' +
                ", routeMap=" + routeMap +
                '}';
    }
}
