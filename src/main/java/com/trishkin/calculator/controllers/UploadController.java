package com.trishkin.calculator.controllers;

import com.trishkin.calculator.domain.CityDistanceList;
import com.trishkin.calculator.services.CityService;
import com.trishkin.calculator.services.DistanceService;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.List;
import java.util.Map;



@Path("/upload")
public class UploadController {

    @Inject
    private CityService cityService;

    @Inject
    private DistanceService distanceService;

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

    @Path("/xml")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadAndParse(MultipartFormDataInput input) {

        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        List<InputPart> inputParts = uploadForm.get("uploadedFile");

        for (InputPart inputPart : inputParts) {

            try {
                InputStream inputStream = inputPart.getBody(InputStream.class, null);

                parseXML(inputStream);

            } catch (IOException | JAXBException e) {
                e.printStackTrace();
            }

        }

        return Response.status(200).build();
    }

    private void parseXML(InputStream inputStream) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(CityDistanceList.class);

        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        CityDistanceList cityDistanceList = (CityDistanceList) unmarshaller.unmarshal(inputStream);
        cityService.createAll(cityDistanceList.getCityList());
        distanceService.createAll(cityDistanceList.getDistanceList());
    }
}
