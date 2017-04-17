package com.disi.geo.compute;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.disi.geo.model.City;
import com.disi.geo.model.TSPInstance;
import com.disi.geo.utils.RandomUtil;
import com.disi.geo.utils.StringUtil;
import com.disi.geo.utils.TSPUtil;

public class SimulatedAnnealingAlgorithm {

	static double TMAX = 10000;
	static double TMIN = 0.00001;
	static double APLPHA = 0.7755;
	
	public static void compute(TSPInstance tspInstance, int iterations, String outputFile){
		List<City> currentSolution = new ArrayList<>(tspInstance.getCities());
		int delta = 0;
		Double randomProbability = 0.0;
		Double probability = 0.0;
		
		long startTime = System.nanoTime();
		
		// create random solution
		Collections.shuffle(currentSolution);
		
		while(TMAX > TMIN){
			for(int iteration = 0; iteration < iterations; iteration++){
				List<City> neighbour = getNeighbourFor(currentSolution);
				delta = TSPUtil.computeTotalCost(neighbour) - TSPUtil.computeTotalCost(currentSolution);
				
				if(delta < 0)
					currentSolution = new ArrayList<>(neighbour);
				
				randomProbability = RandomUtil.generateRandomNumberBetweenOneAndZero();
				probability = Math.exp((-delta) / TMAX);
				
				if(randomProbability < probability){
					currentSolution = new ArrayList<>(neighbour);
				}
			}
			
			TMAX = APLPHA * TMAX;
			//System.out.println("TMAX = " + TMAX + ", TMIN = " + TMIN );
		}
		
		int cost = TSPUtil.computeTotalCost(currentSolution);
		currentSolution.add(currentSolution.get(0));
		
		// END TIME
		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 1000000; // milliseconds
		
		// write solution to file
		TSPUtil.writeSolutionToFile(currentSolution, cost, duration, outputFile);
		
		System.out.println("SIMULATED ANNEALING FINISHED");
	}
	
	
	private static List<City> getNeighbourFor(List<City> currentSolution){
		int i = 0, j = 0, nrOfCities = currentSolution.size();
		boolean twoOptReady = false;
		
		while (!twoOptReady) {
			i = RandomUtil.generateRandomNumberBetween(1, nrOfCities);
			j = RandomUtil.generateRandomNumberBetween(1, nrOfCities);
			
			if( (j - i) >= 3 ){
				twoOptReady = true;
			}
			
		}
		
		List<City> newSolution = new ArrayList<>(currentSolution);
		reverseBetween(newSolution, i + 1, j - 1);
		
		return newSolution;
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
