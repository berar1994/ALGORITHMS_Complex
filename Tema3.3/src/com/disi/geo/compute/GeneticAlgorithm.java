package com.disi.geo.compute;

import java.util.List;

import com.disi.geo.model.City;
import com.disi.geo.model.TSP;
import com.disi.geo.util.GeneticAlgorithmConstantsUtil;
import com.disi.geo.util.GeneticAlgorithmUtil;
import com.disi.geo.util.ListUtil;

public class GeneticAlgorithm {

	static int populationSize = 0;
	static int nrOfParents = 0;

	static {
		populationSize = GeneticAlgorithmConstantsUtil.POPULATION_SIZE_102.value();
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
		int bestFitness = 0, tempBestFitness, bestGenerationIndex = 1;
		boolean firstEvaluation = true;
		List<List<City>> parents = null;
		List<List<City>> children = null;
		
		// initialize population
		List<List<City>> population = GeneticAlgorithmUtil.createInitialPopulationForTSP(populationSize, tspInstance.getCities());
		if(ListUtil.hasDuplicatesInLists(population)){
			System.out.println("Duplicates in initial population");
		}
		
		for(int generationIndex = 0; generationIndex < nrOfGenerations; generationIndex++){
			// select parents
			parents = GeneticAlgorithmUtil.computeParentsSelectionUsingTurnirForTSP(population, nrOfParents);
			
			if(ListUtil.hasDuplicatesInLists(parents)){
				System.out.println("Duplicates in parents selection -- generation = " + (generationIndex + 1));
			}
			
			// compute parents crossover in order to obtain children
			children = GeneticAlgorithmUtil.computeParentsOXCrossoverForTSP(parents);
			
			if(ListUtil.hasDuplicatesInLists(children)){
				System.out.println("Duplicates in children -- generation = " + (generationIndex + 1));
			}
			
			// compute mutations
			GeneticAlgorithmUtil.computeMutationForTSP(population);
			GeneticAlgorithmUtil.computeMutationForTSP(children);
			
			// select survivors
			GeneticAlgorithmUtil.computeSurvivorsForTSP(population, children);
			
			tempBestFitness = GeneticAlgorithmUtil.getBestFitnessFromPopulationForTSP(population);
			if(firstEvaluation){
				bestFitness = tempBestFitness;
				bestGenerationIndex = generationIndex + 1;
				firstEvaluation = false;
			}
			else if(tempBestFitness < bestFitness){
				bestFitness = tempBestFitness;
				bestGenerationIndex = generationIndex + 1;
			}
			
			System.out.println("GENERATION: " + (generationIndex + 1));
			System.out.println("COST: " + tempBestFitness);
			System.out.println("-------------------------------------");
			
			
		}
		
		System.out.println();
		System.out.println("BEST GENERATION: " + bestGenerationIndex);
		System.out.println("FITNESS: " + bestFitness);
		
		System.out.println("TSP PROBLEM FINISHED.");
	}
	
	
	

}
