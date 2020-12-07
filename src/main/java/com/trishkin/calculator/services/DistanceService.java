package com.trishkin.calculator.services;

import com.trishkin.calculator.domain.Distance;

import java.util.List;

public interface DistanceService {
    void createDistance(Distance distance);

    void createAll(List<Distance> distances);
}
