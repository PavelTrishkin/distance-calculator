package com.trishkin.calculator.services;

import com.trishkin.calculator.dao.CityRepository;
import com.trishkin.calculator.domain.City;
import com.trishkin.calculator.dto.CityDto;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


public class CityServiceImpl implements CityService {

    @Inject
    private CityRepository cityRepository;

    public CityServiceImpl() {
    }

    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }


    @Override
    public List<CityDto> getAll() {
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
        return cityRepository.getCityByName(name);
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

    @Override
    public Float calculateDist(String fromCityName, String toCityName) {

        System.out.println("CALC DIST METHOD");

        City fromCity = findCityByName(fromCityName);
        City toCity = findCityByName(toCityName);

        double lon = fromCity.getLongitude();
        double lat = fromCity.getLatitude();
        double lon2 = toCity.getLongitude();
        double lat2 = toCity.getLatitude();

        double earthRadius = 6371; //kilometers
        double dLat = Math.toRadians(lat2 - lat);
        double dLng = Math.toRadians(lon2 - lon);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return (float) (earthRadius * c);

    }
}
