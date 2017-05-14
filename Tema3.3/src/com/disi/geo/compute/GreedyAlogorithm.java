package com.disi.geo.compute;

import java.util.ArrayList;
import java.util.List;

import com.disi.geo.model.City;
import com.disi.geo.util.TSPUtil;


public class GreedyAlogorithm {


	public static List<List<City>> getGreedyCandidates(List<City> tspCities, int nrOfCandidatesNeeded) {
		List<City> cities = new ArrayList<>(tspCities);
		int numberOfCities = cities.size();
		List<List<City>> routes = new ArrayList<>();
		List<City> route = new ArrayList<>(numberOfCities);
		int nextNeighbourIndex = 0;
		boolean stop = false;


		for (int i = 0; i < numberOfCities; i++) {
			if(stop){ break; }
			
			City startingCity = cities.get(i);
			route.add(startingCity);
			cities.get(i).setVisited(true);

			for (int j = 0; j < numberOfCities; j++) {
				if (j == i) { continue; }
				
				nextNeighbourIndex = getNearestNeighbour(cities, startingCity);
				City nextNeighbour = cities.get(nextNeighbourIndex);
				route.add(nextNeighbour);
				cities.get(nextNeighbourIndex).setVisited(true);
				startingCity = nextNeighbour;
			}
			
			if(nrOfCandidatesNeeded > 0){
				//route.add(route.get(0));
				routes.add(route);
				nrOfCandidatesNeeded--;
			} else{
				stop = true;
			}

			route = new ArrayList<>(numberOfCities);
			for (int k = 0; k < numberOfCities; k++) {
				cities.get(k).setVisited(false);
			}
		}

		
		System.out.println("GREEDY FINISHED");
		return routes;
	}

	private static int getNearestNeighbour(List<City> neighbours, City city) {
		int nextNeighbourIndex = 0;

		int numberOfNeighbours = neighbours.size(), index = -1;
		int calculatedDistance = 0, tempDistance = 0;
		boolean foundFirst = false;

		City tempNeighbour = null;
		while (!foundFirst) {
			index++;

			tempNeighbour = neighbours.get(index);
			if (!tempNeighbour.isVisited()) {
				tempDistance = TSPUtil.computeDistance(city, tempNeighbour);
				foundFirst = true;
				nextNeighbourIndex = index;
			}
		}

		for (int i = 0; i < numberOfNeighbours; i++) {
			if (i == index)
				continue;

			tempNeighbour = neighbours.get(i);
			if (!tempNeighbour.isVisited()) {
				calculatedDistance = TSPUtil.computeDistance(city, tempNeighbour);
				if (calculatedDistance < tempDistance) {
					nextNeighbourIndex = i;
					tempDistance = calculatedDistance;
				}
			}
		}

		// return the neighbour index
		return nextNeighbourIndex;
	}

}
