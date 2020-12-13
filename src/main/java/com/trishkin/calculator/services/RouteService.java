package com.trishkin.calculator.services;

import com.trishkin.calculator.domain.City;
import com.trishkin.calculator.domain.Route;

public interface RouteService {

    Route createRoute(String fromCity, String toCity);
}
