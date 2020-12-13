package com.trishkin.calculator.services;

import com.trishkin.calculator.domain.Distance;

import java.util.List;

public interface DistanceService extends EntityService {
    void createDistance(Distance distance);

    void createAll(List<Distance> distances);

    Distance findByName(String fromCity, String toCity);

    List<Distance> findAll();
}
