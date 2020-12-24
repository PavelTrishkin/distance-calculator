package com.trishkin.calculator.dao;

import com.trishkin.calculator.domain.City;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class CityRepositoryImpl implements CityRepository {

    @Inject
    private EntityManager entityManager;

    private Map<String, City> cityMap = new HashMap<>();

    public CityRepositoryImpl() {
    }

    @Override
    public List<City> findAll() {
        return entityManager.createQuery("SELECT c FROM City c").getResultList();
    }

    @Override
    public City getCityByName(String name) {
        City cityInMap = cityMap.get(name);
        if (cityInMap == null){
            List<City> result = entityManager.createQuery("SELECT c FROM City c WHERE c.name = :name", City.class)
                    .setParameter("name", name).getResultList();
            City findCity = result.stream().findFirst().orElse(null);
            cityMap.put(findCity.getName(), findCity);
            return findCity;
        }
        else {
            return cityInMap;
        }

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
