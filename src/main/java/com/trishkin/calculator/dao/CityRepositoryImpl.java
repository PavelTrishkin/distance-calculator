package com.trishkin.calculator.dao;

import com.trishkin.calculator.domain.City;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class CityRepositoryImpl implements CityRepository {

    @Inject
    private EntityManager entityManager;

    public CityRepositoryImpl() {
    }

    @Override
    public List<City> findAll() {
        return entityManager.createQuery("SELECT c FROM City c").getResultList();
    }

    @Override
    public City getCityByName(String name) {
        List<City> result = entityManager.createQuery("SELECT c FROM City c WHERE c.name = :name", City.class)
                .setParameter("name", name).getResultList();
        if(result.isEmpty()){
            return null;
        }
        return result.stream().findFirst().orElse(null);
    }

    @Override
    public City getCityById(Long id) {
        return entityManager.find(City.class, id);
    }

    @Override
    @Transactional
    public void createCity(City city) {
        if (city != null){
            if (getCityByName(city.getName()) == null){
                entityManager.createNativeQuery("INSERT INTO cities_tbl (id,name, latitude, longitude) VALUES (?,?, ?, ?)")
                        .setParameter(1, city.getId())
                        .setParameter(2, city.getName())
                        .setParameter(3, city.getLatitude())
                        .setParameter(4, city.getLongitude())
                        .executeUpdate();
            }
        }
    }

    @Override
    @Transactional
    public void deleteCityByName(String name) {
        entityManager.createQuery("DELETE FROM City c WHERE c.name = :name").setParameter("name", name);
    }

    @Override
    public void deleteById(Long id) {
        entityManager.createQuery("DELETE FROM City c WHERE c.id = :id").setParameter("id", id);
    }
}
