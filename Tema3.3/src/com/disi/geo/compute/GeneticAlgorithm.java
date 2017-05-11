package com.disi.geo.compute;

import java.util.List;

import com.disi.geo.util.GeneticAlgorithmConstantsUtil;
import com.disi.geo.util.GeneticAlgorithmUtil;

public class GeneticAlgorithm {

	static int populationSize = 0;
	static int nrOfParents = 0;

	static {
		populationSize = GeneticAlgorithmConstantsUtil.POPULATION_SIZE_32.value();
		nrOfParents = GeneticAlgorithmConstantsUtil.REQUIRED_NR_OF_CHILDREN_20.value();
	}

	public static void computeOneMaxProblem(int nrOfBits) {
		// initialize population
		List<List<Integer>> population = GeneticAlgorithmUtil.createInitialPopulationForOneMaxProblem(populationSize, nrOfBits);
		
		// select parents
		List<List<Integer>> parents = GeneticAlgorithmUtil.computeParentsSelectionUsingTurnirForOneMaxProblem(population, nrOfParents);
		
		// compute parents crossover in order to obtain children
		List<List<Integer>> children = GeneticAlgorithmUtil.computeParentsCrossoverForOneMaxProblem(parents);
		for(List<Integer> child : children){
			population.add(child);
		}
		
		// compute population mutations
		
		
		System.out.println("ONE MAX PROBLEM FINISHED.");
	}

}
