package com.trishkin.calculator.services;

import com.trishkin.calculator.domain.Route;

import javax.enterprise.inject.Default;
import javax.inject.Inject;


public abstract class AbstractRouteService implements RouteService {

    @Default
    private EntityService entityService;

    @Inject
    public AbstractRouteService(EntityService entityService) {
        this.entityService = entityService;
    }

    @Override
    public Route createRoute(String fromCity, String toCity) {

        System.out.println("CREATING ROUTE");
        return createSpecificRoute(fromCity, toCity);
    }

    protected abstract Route createSpecificRoute(String fromCity, String toCity);
}
