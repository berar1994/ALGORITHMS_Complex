package com.disi.geo.util;

import java.util.ArrayList;
import java.util.List;

public class GeneticAlgorithmUtil {
	
	public static List<List<Integer>> createInitialPopulationForOneMaxProblem(int populationSize, int candidateLength){
		return RandomUtil.generateListOfRandomLists(populationSize, candidateLength);
	}
	
	
	public static List<List<Integer>> computeParentsSelectionUsingTurnirForOneMaxProblem(List<List<Integer>> population, int nrOfParentsNeeded){
		int k  = 0, populationSize = population.size(), randomIndex = 0, index = 0;
		Integer bestCandidateFitness = 0, tempFitness = 0;
		int bestCandidateIndex = 0;
		List<List<Integer>> parents = new ArrayList<>();
		int[] selected = new int[nrOfParentsNeeded];
		boolean select = true;
		
		while(nrOfParentsNeeded > 0){
			k = RandomUtil.generateRandomNumberBetween(2, populationSize);
			
			while(k > 0){
				randomIndex = RandomUtil.generateRandomNumberBetween(0, populationSize);
				tempFitness = computeCandidateFitnessForOneMaxProblem(population.get(randomIndex));
				
				if(tempFitness > bestCandidateFitness){
					bestCandidateFitness = tempFitness;
					bestCandidateIndex = randomIndex;
				}
				
				k--;
			}
			
			
			// check if the candidate was already selected before
			for(int j = 0; j < selected.length; j++){
				if(bestCandidateIndex == selected[j]){
					select = false;
					break;
				}
			}
			
			if(select){
				parents.add(population.get(bestCandidateIndex));
				selected[index] = bestCandidateIndex;
				index++;	
				nrOfParentsNeeded--;
			}
			
			
			bestCandidateFitness = 0;
			bestCandidateIndex = 0;
			select = true;
		}
		
		return parents;
	}
	
	public static List<List<Integer>> computeParentsCrossoverForOneMaxProblem(List<List<Integer>> parents){
		List<List<Integer>> children = new ArrayList<>();
		int size = parents.size();
		int i = 0;
		
		while(i < (size - 1)){
			List<Integer> firstParent = parents.get(i);
			List<Integer> secondParent = parents.get(i+1);
			int parentSize = firstParent.size();
			
			int k = getCrossoverCutPoint(parentSize);
			
			List<Integer> firstChild = new ArrayList<>(firstParent.subList(0, k));
			firstChild.addAll(secondParent.subList(k, parentSize));
			
			List<Integer> secondChild = new ArrayList<>(secondParent.subList(0, k));
			secondChild.addAll(firstParent.subList(k, parentSize));
			
			children.add(firstChild);
			children.add(secondChild);
			
			i = i + 2;
		}
		
		return children;
	}
	
	public static void computeMutationForOneMaxProblem(List<List<Integer>> population){
		
	}
	
	
	private static Integer computeCandidateFitnessForOneMaxProblem(List<Integer> candidate){
		int fitness = 0;
		int candidateSize = candidate.size();
		
		for(int i = 0; i < candidateSize; i++){
			Integer val = candidate.get(i);
			if(val == 1)
				fitness += val;
		}
		
		return fitness;
	}
	
	private static int getCrossoverCutPoint(int limit){
		return RandomUtil.generateRandomNumberBetween(2, limit);
	}
	
}
