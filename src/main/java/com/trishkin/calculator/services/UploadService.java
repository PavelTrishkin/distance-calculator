package com.trishkin.calculator.services;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.xml.bind.JAXBException;
import java.io.InputStream;

public interface UploadService {

    void uploadAndParseXml(MultipartFormDataInput input) throws Exception;

    void parseXml(InputStream inputStream) throws JAXBException;
}
