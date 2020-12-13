package com.trishkin.calculator.services;

import com.trishkin.calculator.domain.City;
import com.trishkin.calculator.domain.Distance;
import com.trishkin.calculator.domain.MatrixRoute;
import com.trishkin.calculator.domain.Route;
import com.trishkin.calculator.exceptions.CityNotFoundException;
import com.trishkin.calculator.utils.Graph;

import javax.inject.Inject;
import java.util.List;

public class MatrixRouteService extends AbstractRouteService {

    @Inject
    private DistanceService distanceService;

    @Inject
    public MatrixRouteService(DistanceService distanceService) {
        super(distanceService);
        this.distanceService = distanceService;
    }

    @Override
    protected Route createSpecificRoute(String fromCity, String toCity) {
        System.out.println("CREATING MATRIX ROUTE");
        System.out.println(distanceService);
        List<Distance> distances = distanceService.findAll();
        try {
            if (!distances.isEmpty()) {
                Graph graph = buildGraph(distances);
                return new MatrixRoute(fromCity, toCity, graph.findShortPathViaBfs(fromCity, toCity));
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
