package com.trishkin.calculator.controllers;

import com.trishkin.calculator.domain.City;
import com.trishkin.calculator.dto.CityDto;
import com.trishkin.calculator.services.CityService;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.List;


@Path("/city")
public class CityRest {

    @Inject
    private CityService cityService;

    @Context
    private ServletContext servletContext;

    @GET
    @Path("/")
    @Produces(MediaType.TEXT_HTML)
    public String getAllCities() {
        return cityService.getAll().toString();
    }


    @GET
    @Path("/calc")
    @Produces(MediaType.APPLICATION_JSON)
    public Float calcDist(@QueryParam("fromCity") String fromCity,
                           @QueryParam("toCity") String toCity){

        System.out.println("Calc dist fromCity " + fromCity + " toCity " + toCity);

        return cityService.calculateDist(fromCity, toCity);
    }

}
