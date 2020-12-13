package com.trishkin.calculator.controllers;

import com.trishkin.calculator.domain.Route;
import com.trishkin.calculator.exceptions.CityNotFoundException;
import com.trishkin.calculator.services.*;
//import com.trishkin.calculator.services.CalculatorService;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.inject.Qualifier;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

@Path("/calc")
public class CalculatorRestImpl implements CalculatorRest {

    @Default
    private AbstractRouteService abstractRouteService;

    @Inject
    private CityService cityService;

    @Inject
    private DistanceService distanceService;

//    @Inject
//    private CalculatorService calculatorService;

    @GET
    @Path("/crowflight")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response calcCrowFlightDist(@QueryParam("from") String fromCity,
                                       @QueryParam("to") String toCity) {

        abstractRouteService = new CrowFlightRouteService(cityService);
        try {
            return Response.ok()
                    .entity(abstractRouteService.createRoute(fromCity,toCity))
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

        abstractRouteService = new MatrixRouteService(distanceService);
        Route route = abstractRouteService.createRoute(fromCity, toCity);
        System.out.println(route);

        try {
            return Response.ok()
                    .entity(route)
//                    .entity(calculatorService.calcMatrixDistance(fromCity,toCity))
                    .build();
        } catch (CityNotFoundException e) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        }
    }

//    @GET
//    @Path("/all")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Override
//    public Response calcAllDist(@QueryParam("from") String fromCity,
//                                @QueryParam("to") String toCity) {
//        try {
//            List<Route> route = new ArrayList<>();
//            route.add(new CrowFlightRouteService().createRoute(fromCity,toCity));
//            route.add(new MatrixRouteService().createRoute(fromCity, toCity));
//            return Response.ok()
//                    .entity(route)
//                    .build();
//        } catch (CityNotFoundException e) {
//            return Response
//                    .status(Response.Status.NOT_FOUND)
//                    .entity(e.getMessage())
//                    .build();
//        }
//    }
}
