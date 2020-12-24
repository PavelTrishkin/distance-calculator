package com.trishkin.calculator.services.calc;

import com.trishkin.calculator.domain.City;
import com.trishkin.calculator.domain.Distance;
import com.trishkin.calculator.exceptions.CityNotFoundException;
import com.trishkin.calculator.services.CityService;
import com.trishkin.calculator.services.DistanceService;
import com.trishkin.calculator.utils.Graph;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class CalculatorServiceImpl implements CalculatorService {

    @Inject
    private CityService cityService;

    @Inject
    private DistanceService distanceService;

    public CalculatorServiceImpl() {
    }

    @Override
    public Float calcCrowFlight(City fromCity, City toCity) {
        return calcCrowFlight(fromCity.getName(), toCity.getName());
    }

    @Override
    public Float calcCrowFlight(String fromCityName, String toCityName) {

        City fromCity = cityService.findCityByName(fromCityName);
        City toCity = cityService.findCityByName(toCityName);

        if (fromCity != null && toCity != null) {
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
        } else {
            return null;
        }
    }

    @Override
    public Map<Float, Stack<String>> calcMatrixDistance(String fromCity, String toCity) {
        List<Distance> distances = distanceService.findAll();
        try {
            if (!distances.isEmpty()) {
                Graph graph = buildGraph(distances);
                return graph.findShortPathViaBfs(fromCity, toCity);
            }
            return null;
        }catch (CityNotFoundException e){
            throw e;
        }

    }

    private Graph buildGraph(List<Distance> distanceList) {
        Graph graph = new Graph(distanceList.size() * 2);

        for (Distance d: distanceList) {
            graph.addVertex(d.getFromCity());
            graph.addVertex(d.getToCity());
            graph.addEdges(d.getFromCity(), d.getToCity(), d.getDistance());
        }
        return graph;
    }
}
