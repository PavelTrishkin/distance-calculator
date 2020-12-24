package com.trishkin.calculator.services.strategy.services;

import com.trishkin.calculator.domain.Route;
import com.trishkin.calculator.services.EntityService;

public interface RouteStrategy{
    Route calcRoute(String fromCity, String toCity);
}
