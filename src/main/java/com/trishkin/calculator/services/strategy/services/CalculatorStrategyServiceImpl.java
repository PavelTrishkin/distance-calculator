package com.trishkin.calculator.services.strategy.services;

import com.trishkin.calculator.domain.Route;

import javax.enterprise.inject.Default;

public class CalculatorStrategyServiceImpl implements CalculatorStrategyService {

    @Default
    private RouteStrategy routeStrategy;

    public CalculatorStrategyServiceImpl() {
    }

    public CalculatorStrategyServiceImpl(RouteStrategy routeStrategy) {
        this.routeStrategy = routeStrategy;
    }

    @Override
    public Route createRoute(String fromCity, String toCity) {
        return routeStrategy.calcRoute(fromCity, toCity);
    }
}
