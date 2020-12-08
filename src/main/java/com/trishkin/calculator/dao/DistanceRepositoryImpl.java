package com.trishkin.calculator.dao;

import com.trishkin.calculator.domain.Distance;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class DistanceRepositoryImpl implements DistanceRepository {

    @Inject
    private EntityManager entityManager;

    public DistanceRepositoryImpl() {
    }

    @Override
    @Transactional
    public void createAll(List<Distance> distanceList) {
        for (Distance d: distanceList) {
            create(d);
        }
    }

    @Override
    @Transactional
    public void create(Distance distance) {
        if (distance != null){
            if (findByName(distance.getFromCity(), distance.getToCity()) == null){
                entityManager.createNativeQuery("INSERT INTO distances_tbl (id,from_city,to_city,distance) VALUES (?,?, ?, ?)")
                        .setParameter(1, distance.getId())
                        .setParameter(2, distance.getFromCity())
                        .setParameter(3, distance.getToCity())
                        .setParameter(4, distance.getDistance())
                        .executeUpdate();
            }
        }
    }

    @Override
    public Distance findByName(String fromCity, String toCity) {
        return entityManager.createQuery("SELECT d FROM Distance d WHERE d.fromCity = :from_city AND d.toCity = :to_city", Distance.class)
                .setParameter("from_city", fromCity)
                .setParameter("to_city", toCity)
                .getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public List<Distance> findAll() {
        return entityManager.createQuery("SELECT d FROM Distance d").getResultList();
    }
}
