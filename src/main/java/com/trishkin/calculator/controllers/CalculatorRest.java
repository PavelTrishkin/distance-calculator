package com.trishkin.calculator.controllers;



import javax.ws.rs.core.Response;

public interface CalculatorRest {
   Response calcCrowFlightDist(String fromCity, String toCity);

   Response calcMatrixDist(String fromCity, String toCity);

   Response calcAllDist(String fromCity, String toCity);
}
