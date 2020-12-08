package com.trishkin.calculator.dao;

import com.trishkin.calculator.domain.Distance;

import java.util.List;

public interface DistanceRepository {
    void createAll(List<Distance> distanceList);

    void create(Distance distance);

    Distance findByName(String fromCity, String toCity);

    List<Distance> findAll();
}
