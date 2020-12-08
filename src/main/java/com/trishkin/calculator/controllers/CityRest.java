package com.trishkin.calculator.controllers;

import com.trishkin.calculator.domain.City;
import com.trishkin.calculator.dto.CityDto;
import com.trishkin.calculator.exceptions.CityNotFoundException;
import com.trishkin.calculator.services.CityService;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/city")
public class CityRest {

    @Inject
    private CityService cityService;

    @Context
    private ServletContext servletContext;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CityDto> getAllCities() {
        return cityService.getAll();
    }

    @GET
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByName(@PathParam("name") String name){
        try{
            return Response.ok().entity(cityService.findCityByName(name)).build();
        }
        catch (CityNotFoundException e){
            return Response.status(Response.Status.NOT_FOUND).entity("Can't found city with name = " + name).build();
        }
    }


    @GET
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public String createCity(@QueryParam("name") String name,
                             @QueryParam("latitude") Double latitude,
                             @QueryParam("longitude") Double longitude){
        City city = new City(name,latitude,longitude);
        cityService.createCity(city);
        return city.toString();
    }
}
