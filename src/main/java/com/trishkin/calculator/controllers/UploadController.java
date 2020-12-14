package com.trishkin.calculator.controllers;

import com.trishkin.calculator.services.UploadService;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;



@Path("/upload")
public class UploadController {

    @Inject
    private UploadService uploadService;

    @Context
    private ServletContext servletContext;

    @GET
    @Path("/")
    public InputStream getUploadForm() {
        String page = servletContext.getRealPath("/WEB-INF/classes/templates");
        File file = new File(String.format("%s/uploadXML.html", page));
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Path("/")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadAndParse(MultipartFormDataInput input) {
        try {
            uploadService.uploadAndParseXml(input);
            return Response.status(200).entity("XML file add to DB successfully").build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}
