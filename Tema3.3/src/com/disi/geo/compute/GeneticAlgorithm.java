package com.disi.geo.compute;

import java.util.List;

import com.disi.geo.model.City;
import com.disi.geo.model.TSP;
import com.disi.geo.util.GeneticAlgorithmConstantsUtil;
import com.disi.geo.util.GeneticAlgorithmUtil;

public class GeneticAlgorithm {

	static int populationSize = 0;
	static int nrOfParents = 0;

	static {
		populationSize = GeneticAlgorithmConstantsUtil.POPULATION_SIZE_32.value();
		nrOfParents = GeneticAlgorithmConstantsUtil.REQUIRED_NR_OF_CHILDREN_20.value();
	}

	public static void computeOneMaxProblem(int nrOfBits, int nrOfGenerations) {
		int bestFitness = 0, tempBestFitness, bestGenerationIndex = 1;
		List<List<Integer>> parents = null;
		List<List<Integer>> children = null;
		
		// initialize population
		List<List<Integer>> population = GeneticAlgorithmUtil.createInitialPopulationForOneMaxProblem(populationSize, nrOfBits);
		
		for(int generationIndex = 0; generationIndex < nrOfGenerations; generationIndex++){
			// select parents
			parents = GeneticAlgorithmUtil.computeParentsSelectionUsingTurnirForOneMaxProblem(population, nrOfParents);
			
			// compute parents crossover in order to obtain children
			children = GeneticAlgorithmUtil.computeParentsCrossoverForOneMaxProblem(parents);
			
			// compute mutations
			GeneticAlgorithmUtil.computeMutationForOneMaxProblem(population);
			GeneticAlgorithmUtil.computeMutationForOneMaxProblem(children);
			
			// select survivors
			GeneticAlgorithmUtil.computeSurvivorsForOneMaxProblem(population, children);
			
			tempBestFitness = GeneticAlgorithmUtil.getBestFitnessFromPopulation(population);
			if(tempBestFitness > bestFitness){
				bestFitness = tempBestFitness;
				bestGenerationIndex = generationIndex + 1;
			}
			
			
			if(tempBestFitness == nrOfBits)
				break;
		}
		
		
		System.out.println();
		System.out.println("BEST GENERATION: " + bestGenerationIndex);
		System.out.println("FITNESS: " + bestFitness);
		
		System.out.println("ONE MAX PROBLEM FINISHED.");
	}
	
	
	
	public static void computeTSPProblem(TSP tspInstance, int nrOfGenerations){
		// initialize population
		List<List<City>> population = GeneticAlgorithmUtil.createInitialPopulationForTSP(populationSize, tspInstance.getCities());
		List<List<City>> parents = null;
		List<List<City>> children = null;
		
		for(int generationIndex = 0; generationIndex < nrOfGenerations; generationIndex++){
			// select parents
			parents = GeneticAlgorithmUtil.computeParentsSelectionUsingTurnirForTSP(population, nrOfParents);
			
			// compute parents crossover in order to obtain children
			children = GeneticAlgorithmUtil.computeParentsPMXCrossoverForTSP(parents);
		}
		
		System.out.println("TSP PROBLEM FINISHED.");
	}
	
	
	

}
