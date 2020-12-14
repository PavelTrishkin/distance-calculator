package com.trishkin.calculator.services;

import com.trishkin.calculator.dao.CityRepository;
import com.trishkin.calculator.domain.City;
import com.trishkin.calculator.dto.CityDto;
import com.trishkin.calculator.exceptions.CityNotFoundException;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


public class CityServiceImpl implements CityService {

    @Inject
    private CityRepository cityRepository;

    public CityServiceImpl() {
    }

    @Override
    public List<CityDto> findAll() {
        return toDtoList(cityRepository.findAll());
    }

    @Override
    @Transactional
    public void createCity(City city) {
        cityRepository.createCity(city);
    }

    @Override
    public City findCityById(Long id) {
        return cityRepository.getCityById(id);
    }

    @Override
    public City findCityByName(String name) {
        City city = cityRepository.getCityByName(name);
        if (city == null){
            throw new CityNotFoundException("Can't found city with name = " + name);
        }
        return city;
    }

    private CityDto toDto(City city) {
        return new CityDto(city.getId(), city.getName());
    }

    private List<CityDto> toDtoList(List<City> cityList) {
        List<CityDto> cityDtoList = new ArrayList<>();

        for (City city : cityList) {
            cityDtoList.add(toDto(city));
        }

        return cityDtoList;
    }

    @Override
    @Transactional
    public void createAll(List<City> cityList) {
        if (!cityList.isEmpty()){
            for (City c : cityList) {
                createCity(c);
            }
        }
    }

}
