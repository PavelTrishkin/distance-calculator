package com.trishkin.calculator.dao;

import com.trishkin.calculator.domain.City;

import java.util.List;

public interface CityRepository {
    List<City> findAll();

    City getCityByName(String name);

    City getCityById(Long id);

    void createCity(City city);

    void deleteCityByName(String name);

    void deleteById(Long id);
}
