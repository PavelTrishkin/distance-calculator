package com.trishkin.calculator.services;

import com.trishkin.calculator.dao.DistanceRepository;
import com.trishkin.calculator.domain.Distance;
import com.trishkin.calculator.exceptions.CityNotFoundException;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

public class DistanceServiceImpl implements DistanceService {

    @Inject
    private DistanceRepository distanceRepository;

    public DistanceServiceImpl() {
    }

    @Override
    @Transactional
    public void createDistance(Distance distance) {
        distanceRepository.create(distance);
    }

    @Override
    @Transactional
    public void createAll(List<Distance> distances) {
        distanceRepository.createAll(distances);
    }

    @Override
    public Distance findByName(String fromCity, String toCity) {
        Distance result = distanceRepository.findByName(fromCity, toCity);
        if (result == null) {
            throw new CityNotFoundException(String.format("Can't found distance with parameters from city = %s and to city = %s", fromCity, toCity));
        }
        return result;
    }

    @Override
    public List<Distance> findAll() {
        return distanceRepository.findAll();
    }
}
