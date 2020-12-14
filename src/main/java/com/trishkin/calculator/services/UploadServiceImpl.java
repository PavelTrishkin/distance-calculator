package com.trishkin.calculator.services;

import com.trishkin.calculator.domain.CityDistanceList;
import com.trishkin.calculator.exceptions.FileNotSupportedException;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class UploadServiceImpl implements UploadService {

    @Inject
    private CityService cityService;

    @Inject
    private DistanceService distanceService;

    public UploadServiceImpl() {
    }

    @Override
    public void uploadAndParseXml(MultipartFormDataInput input) throws IOException, JAXBException {
        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        List<InputPart> inputParts = uploadForm.get("uploadedFile");

        for (InputPart inputPart : inputParts) {
            if (inputPart.getMediaType().getSubtype().contains("xml")) {
                InputStream inputStream = inputPart.getBody(InputStream.class, null);

                parseXml(inputStream);

            } else {
                throw new FileNotSupportedException("Not supported file format");
            }

        }
    }

    @Override
    public void parseXml(InputStream inputStream) throws JAXBException{
        JAXBContext jaxbContext = JAXBContext.newInstance(CityDistanceList.class);

        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        CityDistanceList cityDistanceList = (CityDistanceList) unmarshaller.unmarshal(inputStream);

        cityService.createAll(cityDistanceList.getCityList());
        distanceService.createAll(cityDistanceList.getDistanceList());
    }
}
