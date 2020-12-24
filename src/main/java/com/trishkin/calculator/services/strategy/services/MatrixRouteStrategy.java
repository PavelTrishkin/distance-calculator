package com.trishkin.calculator.services.strategy.services;

import com.trishkin.calculator.domain.Route;
import com.trishkin.calculator.services.AbstractRouteService;

import javax.enterprise.inject.Default;

public class MatrixRouteStrategy implements RouteStrategy{

    @Default
    private AbstractRouteService abstractRouteService;

    public MatrixRouteStrategy() {
    }

    public MatrixRouteStrategy(AbstractRouteService abstractRouteService) {
        this.abstractRouteService = abstractRouteService;
    }


    @Override
    public Route calcRoute(String fromCity, String toCity) {
        return abstractRouteService.createRoute(fromCity, toCity);
    }
}
