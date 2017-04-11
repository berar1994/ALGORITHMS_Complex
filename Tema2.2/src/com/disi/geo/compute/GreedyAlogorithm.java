package com.disi.geo.compute;

import java.util.ArrayList;
import java.util.List;

import com.disi.geo.model.City;
import com.disi.geo.model.TSPInstance;
import com.disi.geo.utils.TSPUtil;

public class GreedyAlogorithm {

	public static void compute(TSPInstance tspInstance, String outputFile) {
		List<City> cities = new ArrayList<>(tspInstance.getCities());
		int numberOfCities = cities.size(), index = 0;
		List<City> route = new ArrayList<>(numberOfCities);
		int nextNeighbourIndex = 0;

		long startTime = System.nanoTime();

		City startingCity = cities.get(0);
		route.add(startingCity);
		cities.get(0).setVisited(true);
		index = 1;

		while (index < numberOfCities) {
			nextNeighbourIndex = getNearestNeighbour(cities, startingCity);
			City nextNeighbour = cities.get(nextNeighbourIndex);
			route.add(nextNeighbour);
			cities.get(nextNeighbourIndex).setVisited(true);
			startingCity = nextNeighbour;
			index++;
		}

		Double cost = TSPUtil.computeTotalCost(route);
		route.add(route.get(0));

		// END TIME
		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 1000000; // milliseconds

		// write solution to file
		TSPUtil.writeSolutionToFile(route, cost, duration, outputFile);

		System.out.println("GREEDY FINISHED");
	}

	private static int getNearestNeighbour(List<City> neighbours, City city) {
		int nextNeighbourIndex = 0;

		int numberOfNeighbours = neighbours.size(), index = -1;
		Double calculatedDistance = 0.0, tempDistance = 0.0;
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
