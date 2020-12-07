package com.trishkin.calculator.domain;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CityDistanceList {

    @XmlElement(name = "distance")
    private List<Distance> distanceList;

    @XmlElement(name = "city")
    private List<City> cityList;

    public CityDistanceList() {
    }


    public List<City> getCityList() {
        return cityList;
    }

    public void setCityList(List<City> cityList) {
        this.cityList = cityList;
    }

    public List<Distance> getDistanceList() {
        return distanceList;
    }

    public void setDistanceList(List<Distance> distanceList) {
        this.distanceList = distanceList;
    }
}
