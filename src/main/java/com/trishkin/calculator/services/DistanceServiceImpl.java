package com.trishkin.calculator.services;

import com.trishkin.calculator.domain.Distance;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

public class DistanceServiceImpl implements DistanceService {

    @Override
    @Transactional
    public void createDistance(Distance distance) {
        if (distance != null) {
            System.out.printf("Distance with cityFrom = %s and toCity = %s and distance = %f created into DB %n"
                    , distance.getFromCity(), distance.getToCity(), distance.getDistance());
        }
        else {
            System.out.println("No distance to create into DB");
        }
    }

    @Override
    @Transactional
    public void createAll(List<Distance> distances) {
        if(!distances.isEmpty()){
            for (Distance d: distances) {
                createDistance(d);
            }
        }
    }
}
