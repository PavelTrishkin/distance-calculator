package com.trishkin.calculator.services;

import com.trishkin.calculator.domain.City;
import com.trishkin.calculator.dto.CityDto;

import java.util.List;

public interface CityService extends EntityService{
    List<CityDto> findAll();

    void createCity(City city);

    void createAll(List<City> cityList);

    City findCityById(Long id);

    City findCityByName(String name);

}
