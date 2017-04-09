package com.disi.geo.compute;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.disi.geo.model.City;
import com.disi.geo.model.TSPInstance;
import com.disi.geo.utils.RandomUtil;
import com.disi.geo.utils.TSPUtil;

public class LocalSearchAlgorithm {

	public static void compute(TSPInstance tspInstance, int iterations, int distance, String outputFile) {
		List<City> currentSolution = new ArrayList<>(tspInstance.getCities());
		Double currentRouteTotalCost = 0.0;
		Double newRouteTotalCost = 0.0;

		long startTime = System.nanoTime();

		// get a random permutation
		Collections.shuffle(currentSolution);

		// evaluate the random permutation
		currentRouteTotalCost = TSPUtil.computeTotalCost(currentSolution);

		// apply 2-opt
		int i = 0, j = 0;
		boolean twoOptReady = false;

		for (int iteration = 0; iteration < iterations; iteration++) {
			while (!twoOptReady) {
				i = RandomUtil.generateRandomNumberBetween(1, tspInstance.getNumberOfCities());
				if ((i + distance) < tspInstance.getNumberOfCities()) {
					j = i + distance;
					twoOptReady = true;
				}
			}

			List<City> newSolution = new ArrayList<>(currentSolution);
			reverseBetween(newSolution, i + 1, j - 1);
			newRouteTotalCost = TSPUtil.computeTotalCost(newSolution);

			if (newRouteTotalCost < currentRouteTotalCost) {
				currentRouteTotalCost = newRouteTotalCost;
				currentSolution = new ArrayList<>(newSolution);
				newSolution = null;
			}

			twoOptReady = false;
		}

		// END TIME
		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 1000000; // milliseconds

		// a complete route is 1 4 5 8 9 1 - so we also need to add at the end
		// of route, the starting node
		currentSolution.add(currentSolution.get(0));

		// write solution to file
		TSPUtil.writeSolutionToFile(currentSolution, currentRouteTotalCost, duration, outputFile);
		System.out.println("LOCAL SEARCH FINISHED");

	}

	private static void reverseBetween(List<City> cities, int i, int j) {
		if (i >= j) {
			return;
		} else {
			// swap two elements
			City temp = cities.get(i);
			cities.set(i, cities.get(j));
			cities.set(j, temp);
		}
		reverseBetween(cities, i + 1, j - 1);
	}

}
