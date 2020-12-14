package com.trishkin.calculator.services;

import com.trishkin.calculator.domain.City;
import com.trishkin.calculator.domain.CrowFlightRoute;
import com.trishkin.calculator.domain.Route;

import javax.inject.Inject;

public class CrowFlightRouteService extends AbstractRouteService {

    private CityService cityService;

    @Inject
    public CrowFlightRouteService(CityService cityService) {
        super(cityService);
        this.cityService = cityService;
    }

    @Override
    protected Route createSpecificRoute(String from, String to) {
        System.out.println(cityService);
        System.out.println("CREATING CROWFLIGHT ROUTE");
        City fromCity = cityService.findCityByName(from);
        System.out.println(fromCity);
        City toCity = cityService.findCityByName(to);
        System.out.println(toCity);

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
            return new CrowFlightRoute(fromCity.getName(), toCity.getName(), (float) (earthRadius * c));
        } else {
            return null;
        }
    }
}
