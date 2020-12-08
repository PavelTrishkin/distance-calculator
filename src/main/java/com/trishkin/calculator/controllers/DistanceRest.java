package com.trishkin.calculator.controllers;

import com.trishkin.calculator.domain.Distance;
import com.trishkin.calculator.exceptions.CityNotFoundException;
import com.trishkin.calculator.services.CalculatorService;
import com.trishkin.calculator.services.DistanceService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/distance")
public class DistanceRest {

    @Inject
    private DistanceService distanceService;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Distance> getAll() {
        return distanceService.findAll();
    }


    @GET
    @Path("/create")
    public Response createDistance(@QueryParam("from") String fromCity,
                                   @QueryParam("to") String toCity,
                                   @QueryParam("dist") Double dist) {
        Distance distance = new Distance(fromCity, toCity, dist);
        System.out.println(distance.toString());
        distanceService.createDistance(distance);
        return Response.ok().build();
    }

    @GET
    @Path("/find")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDistanceByName(@QueryParam("from") String fromCity,
                                      @QueryParam("to") String toCity) {
        try {
           return Response.ok().entity(distanceService.findByName(fromCity, toCity)).build();
        }
        catch (CityNotFoundException e) {
           return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

}
