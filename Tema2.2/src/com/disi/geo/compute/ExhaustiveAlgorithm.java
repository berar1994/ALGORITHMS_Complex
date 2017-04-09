package com.disi.geo.compute;

import java.util.ArrayList;
import java.util.List;

import com.disi.geo.model.City;
import com.disi.geo.model.TSPInstance;
import com.disi.geo.utils.DoubleUtil;
import com.disi.geo.utils.FileUtil;
import com.disi.geo.utils.PermutationUtil;
import com.disi.geo.utils.TSPUtil;

public class ExhaustiveAlgorithm {

	public static void compute(TSPInstance tspInstance, String outputFile) {

		long startTime = System.nanoTime();

		// compute all possible cities permutations
		List<City> citiesTemp = new ArrayList<>(tspInstance.getCities());
		List<List<City>> permutations = PermutationUtil.generateAllPossiblePermutations(citiesTemp);

		List<City> bestRoute = null;
		Double bestRouteTotalCost = 0.0;
		// initialy the best route is the first permutation
		int bestRouteIndex = 0;

		List<City> initializingPermutation = permutations.remove(0);

		// initialy the best cost is the cost of first permutation
		bestRouteTotalCost = TSPUtil.computeTotalCost(initializingPermutation);

		int permutationIndex = 0;
		Double calculatedCost = 0.0;

		// find the best route and get it's cost
		for (List<City> permutation : permutations) {
			calculatedCost = TSPUtil.computeTotalCost(permutation);

			if (calculatedCost < bestRouteTotalCost) {
				bestRouteTotalCost = calculatedCost;
				bestRouteIndex = permutationIndex;
			}

			permutationIndex++;
		}

		// END TIME
		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 1000000; // milliseconds

		bestRoute = new ArrayList<>(permutations.get(bestRouteIndex));
		// a complete route is 1 4 5 8 9 1 - so we also need to add at the end
		// of route, the starting node
		bestRoute.add(bestRoute.get(0));


		// write solution to file
		TSPUtil.writeSolutionToFile(bestRoute, bestRouteTotalCost, duration, outputFile);
		System.out.println("EXHAUSTIVE FINISHED");
	}

}
