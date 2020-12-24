package com.trishkin.calculator.services.calc;

import com.trishkin.calculator.domain.City;

import java.util.Map;
import java.util.Stack;

public interface CalculatorService{
    Float calcCrowFlight(City fromCity, City toCity);

    Float calcCrowFlight(String fromCity, String toCity);

    Map<Float, Stack<String>> calcMatrixDistance(String fromCity, String toCity);
}
