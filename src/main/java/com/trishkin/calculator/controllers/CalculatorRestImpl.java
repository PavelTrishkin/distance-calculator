package com.trishkin.calculator.controllers;

import com.trishkin.calculator.exceptions.CityNotFoundException;
import com.trishkin.calculator.services.CalculatorService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

@Path("/calc")
public class CalculatorRestImpl implements CalculatorRest {

    @Inject
    private CalculatorService calculatorService;

    @GET
    @Path("/crowflight")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response calcCrowFlightDist(@QueryParam("from") String fromCity,
                                       @QueryParam("to") String toCity) {
        try {
            return Response.ok()
                    .entity(calculatorService.calcCrowFlight(fromCity,toCity))
                    .build();
        }
        catch (CityNotFoundException e){
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/matrix")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response calcMatrixDist(@QueryParam("from") String fromCity,
                                   @QueryParam("to") String toCity) {
        try {
            return Response.ok()
                    .entity(calculatorService.calcMatrixDistance(fromCity, toCity))
                    .build();
        } catch (CityNotFoundException e) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response calcAllDist(@QueryParam("from") String fromCity,
                                @QueryParam("to") String toCity) {
        try {
            Map<Float, Stack<String>> route = new LinkedHashMap<>();
            Float distance = calculatorService.calcCrowFlight(fromCity, toCity);
            route.put(distance, null);
            route.putAll(calculatorService.calcMatrixDistance(fromCity, toCity));
            return Response.ok()
                    .entity(route)
                    .build();
        } catch (CityNotFoundException e) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        }
    }
}
