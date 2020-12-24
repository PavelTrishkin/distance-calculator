package com.trishkin.calculator.services.strategy.services;

import com.trishkin.calculator.domain.Route;

public interface CalculatorStrategyService {
    Route createRoute(String fromCity, String toCity);
}
