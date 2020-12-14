package com.trishkin.calculator.controllers;

import com.trishkin.calculator.domain.Route;
import com.trishkin.calculator.exceptions.CityNotFoundException;
import com.trishkin.calculator.services.*;
import com.trishkin.calculator.services.strategy.services.*;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

@Path("/calc")
public class CalculatorRestImpl implements CalculatorRest {

    @Default
    private AbstractRouteService abstractRouteService;

    @Default
    private RouteStrategy routeStrategy;

    @Inject
    private CityService cityService;

    @Inject
    private DistanceService distanceService;

    @GET
    @Path("/strategy")
    @Produces(MediaType.APPLICATION_JSON)
    public Response calcRouteByStrategy(@QueryParam("from") String fromCity,
                                        @QueryParam("to") String toCity,
                                        @QueryParam("str") String strategy){
        switch (strategy){
            case ("crow"):
                try {
                    routeStrategy = new CrowFlightRouteStrategy(new CrowFlightRouteService(cityService));
                    System.out.println("CREATE STRATEGY " + routeStrategy.toString());
                    CalculatorStrategyService calculatorStrategyService = new CalculatorStrategyServiceImpl(routeStrategy);

                    return Response.ok()
                            .entity(calculatorStrategyService.createRoute(fromCity,toCity))
                            .build();
                }
                catch (CityNotFoundException e){
                    return Response
                            .status(Response.Status.NOT_FOUND)
                            .entity(e.getMessage())
                            .build();
                }
            case ("matrix"):
                try {
                    routeStrategy = new MatrixRouteStrategy(new MatrixRouteService(distanceService));
                    CalculatorStrategyService calculatorStrategyService = new CalculatorStrategyServiceImpl(routeStrategy);
                    return Response.ok()
                            .entity(calculatorStrategyService.createRoute(fromCity,toCity))
                            .build();
                }
                catch (CityNotFoundException e){
                    return Response
                            .status(Response.Status.NOT_FOUND)
                            .entity(e.getMessage())
                            .build();
                }
            default: return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("Wrong parameters")
                    .build();
        }
    }

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

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response calcAllDist(@QueryParam("from") String fromCity,
                                @QueryParam("to") String toCity) {
        try {
            List<Route> route = new ArrayList<>();
            route.add(new CrowFlightRouteService(cityService).createRoute(fromCity,toCity));
            route.add(new MatrixRouteService(distanceService).createRoute(fromCity, toCity));
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
