package com.disi.geo.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.disi.geo.compute.GreedyAlogorithm;
import com.disi.geo.model.City;

public class GeneticAlgorithmUtil {
	
	/****************************************************************************** ONE MAX *****************************************************************/
	
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
		int candidateSize = population.get(0).size();
		int populationSize = population.size();
		int randomIndex = 0, currentValue = 0;
		
		for(int i = 0; i < populationSize; i++){
			randomIndex = RandomUtil.generateRandomNumberBetween(0, candidateSize);
			currentValue = population.get(i).get(randomIndex);
			
			if(currentValue == 1){
				population.get(i).set(randomIndex, 0);
			} else{
				population.get(i).set(randomIndex, 1);
			}
		}
	}
	
	
	public static void computeSurvivorsForOneMaxProblem(List<List<Integer>> population, List<List<Integer>> children){
		int childrenSize = children.size();
		int indexToBeReplaced = 0;
		List<List<Integer>> tempPopulation = new ArrayList<>(population);
		
		for(int i = 0; i < childrenSize; i++){
			indexToBeReplaced = getIndexOfWorstIndividualFromPopulation(tempPopulation);
			tempPopulation.remove(indexToBeReplaced);
			population.set(indexToBeReplaced, children.get(i));
		}
		
		tempPopulation = null;
		
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
	
	private static int getIndexOfWorstIndividualFromPopulation(List<List<Integer>> population){
		int index, worstFitness = 0, tempFitness = 0;
		int populationSize = population.size();
		
		worstFitness = computeCandidateFitnessForOneMaxProblem(population.get(0));
		index = 0;
		
		for(int i = 1; i < populationSize; i++){
			tempFitness = computeCandidateFitnessForOneMaxProblem(population.get(i));
			if(tempFitness < worstFitness){
				worstFitness = tempFitness;
				index = i;
			}
		}
		
		return index;
	}
	
	public static int getBestFitnessFromPopulation(List<List<Integer>> population){
		int bestFitness, tempFitness = 0;
		int populationSize = population.size();
		
		bestFitness = computeCandidateFitnessForOneMaxProblem(population.get(0));
		
		for(int i = 1; i < populationSize; i++){
			tempFitness = computeCandidateFitnessForOneMaxProblem(population.get(i));
			if(tempFitness > bestFitness){
				bestFitness = tempFitness;
			}
		}
		
		return bestFitness;
	}
	
	
	/****************************************************************************************************************************************************/
	
	
	/************************************************************************ TSP ***********************************************************************/
	public static List<List<City>> createInitialPopulationForTSP(int populationSize, List<City> cities){
		List<City> tempCities = new ArrayList<>(cities);
		List<List<City>> population = new ArrayList<>();
		int nrOfGreedyIndividuals = 0, citiesSize = cities.size();
		boolean greedyIndividualsNotReady = true;
		
		while(greedyIndividualsNotReady){
			nrOfGreedyIndividuals = RandomUtil.generateRandomNumberBetween(1, citiesSize);
			if(nrOfGreedyIndividuals < populationSize){
				greedyIndividualsNotReady = false;
			}
		}
		
		List<List<City>> greedyIndividuals = GreedyAlogorithm.getGreedyCandidates(cities, nrOfGreedyIndividuals);
		for(List<City> greedyIndividual : greedyIndividuals){
			population.add(greedyIndividual);
		}
		
		int remainingSpots = populationSize - nrOfGreedyIndividuals;
		for(int i = 0; i < remainingSpots; i++){
			Collections.shuffle(tempCities);
			population.add(tempCities);
		}
		
		return population;
	}
	
	
	public static List<List<City>> computeParentsSelectionUsingTurnirForTSP(List<List<City>> population, int nrOfParentsNeeded){
		List<List<City>> parents = new ArrayList<>();
		int k = 0, populationSize = population.size(), randomIndex = 0, index = 0;
		int bestCandidateFitness = 0, tempFitness = 0;
		int bestCandidateIndex = 0;
		int[] selected = new int[nrOfParentsNeeded];
		boolean select = true, bestCandidateFitnessNotInitialized = true;
		
		while(nrOfParentsNeeded > 0){
			k = RandomUtil.generateRandomNumberBetween(2, populationSize);
			
			while(k > 0){
				randomIndex = RandomUtil.generateRandomNumberBetween(0, populationSize);
				tempFitness = TSPUtil.computeTotalCost(population.get(randomIndex));
				
				if(bestCandidateFitnessNotInitialized){
					bestCandidateFitness = tempFitness;
					bestCandidateIndex = randomIndex;
					bestCandidateFitnessNotInitialized = false;
				}
				else if(tempFitness < bestCandidateFitness){
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
			bestCandidateFitnessNotInitialized = true;
		}
		
		return parents;
	}
	
	
	public static List<List<City>> computeParentsOXCrossoverForTSP(List<List<City>> parents){
		List<List<City>> children = new ArrayList<>();
		int cutPoint1 = 0, cutPoint2 = 0;
		boolean cutPointsGenerated = false;
		int permutationSize = parents.get(0).size();
		List<City> firstChild = null, firstParent = null;
		List<City> secondChild = null, secondParent = null;
		int i = 0;
		int nrOfParents = parents.size();
		
		while(i < (nrOfParents - 1)){
			// generate cut points
			while(!cutPointsGenerated){
				cutPoint1 = RandomUtil.generateRandomNumberBetween(3, permutationSize);
				cutPoint2 = RandomUtil.generateRandomNumberBetween(3, permutationSize);
				
				if( (cutPoint2 - cutPoint1) >= 3 ){
					cutPointsGenerated = true;
				}
			}
			
			firstParent = parents.get(i);
			secondParent = parents.get(i+1);
			
			firstChild = getEmptyList(cutPoint1);
			secondChild = getEmptyList(cutPoint1);
			
			firstChild.addAll(cutPoint1, firstParent.subList(cutPoint1, cutPoint2));
			secondChild.addAll(cutPoint1, secondParent.subList(cutPoint1, cutPoint2));
			
			// add remaining
			addRemaining(firstChild, firstParent, secondParent, cutPoint1, cutPoint2, permutationSize);
			addRemaining(secondChild, secondParent, firstParent, cutPoint1, cutPoint2, permutationSize);
			
			children.add(firstChild);
			children.add(secondChild);
			
			i = i + 2;
		}
		
		return children;
	}
	
	
	public static void computeMutationForTSP(List<List<City>> population){
		int firstPosition = 0, secondPosition = 0;
		int populationSize = population.size();
		int candidateSize = population.get(0).size();
		boolean positionsNotGenerated = true;
		
		for(int i = 0; i < populationSize; i++){
			while(positionsNotGenerated){
				firstPosition = RandomUtil.generateRandomNumberBetween(0, candidateSize - 1);
				secondPosition = RandomUtil.generateRandomNumberBetween(0, candidateSize - 1);
				
				if(firstPosition != secondPosition){
					positionsNotGenerated = false;
				}
			}
			
			// swap elements at positions
			City city = population.get(i).get(firstPosition);
			population.get(i).set(firstPosition, population.get(i).get(secondPosition));
			population.get(i).set(secondPosition, city);
			positionsNotGenerated = true;
		}
		
	}
	
	
	public static void computeSurvivorsForTSP(List<List<City>> population, List<List<City>> children){
		int childrenSize = children.size();
		int indexToBeReplaced = 0;
		List<List<City>> tempPopulation = new ArrayList<>(population);
		int tempFitness = 0;
		
		for(int i = 0; i < childrenSize; i++){
			indexToBeReplaced = getIndexOfWorstIndividualFromPopulationForTSP(tempPopulation);
			tempFitness = TSPUtil.computeTotalCost(tempPopulation.get(indexToBeReplaced));
			
			if(TSPUtil.computeTotalCost(children.get(i)) < tempFitness ){
				tempPopulation.remove(indexToBeReplaced);
				population.set(indexToBeReplaced, children.get(i));
			}
		}
		
		tempPopulation = null;
		
	}
	
	
	public static int getBestFitnessFromPopulationForTSP(List<List<City>> population){
		int bestFitness, tempFitness = 0;
		int populationSize = population.size();
		
		bestFitness = TSPUtil.computeTotalCost(population.get(0));
		
		for(int i = 1; i < populationSize; i++){
			tempFitness = TSPUtil.computeTotalCost(population.get(i));
			if(tempFitness < bestFitness){
				bestFitness = tempFitness;
			}
		}
		
		return bestFitness;
	}
	
	
	public static List<List<City>> computeSurvivorsForTSP(List<List<City>> population, int nrOfSurvivorsNeeded){
		List<List<City>> newPopulation = new ArrayList<>();
		int k = 0, populationSize = population.size(), randomIndex = 0, index = 0;
		int bestCandidateFitness = 0, tempFitness = 0;
		int bestCandidateIndex = 0;
		int[] selected = new int[nrOfSurvivorsNeeded];
		boolean select = true, bestCandidateFitnessNotInitialized = true;
		
		while(nrOfSurvivorsNeeded > 0){
			k = RandomUtil.generateRandomNumberBetween(2, populationSize);
			
			while(k > 0){
				randomIndex = RandomUtil.generateRandomNumberBetween(0, populationSize);
				tempFitness = TSPUtil.computeTotalCost(population.get(randomIndex));
				
				if(bestCandidateFitnessNotInitialized){
					bestCandidateFitness = tempFitness;
					bestCandidateIndex = randomIndex;
					bestCandidateFitnessNotInitialized = false;
				}
				else if(tempFitness < bestCandidateFitness){
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
				newPopulation.add(population.get(bestCandidateIndex));
				selected[index] = bestCandidateIndex;
				index++;
				nrOfSurvivorsNeeded--;
			}
			
			bestCandidateFitness = 0;
			bestCandidateIndex = 0;
			select = true;
			bestCandidateFitnessNotInitialized = true;
		}
		
		return newPopulation;
	}
	
	
	
	private static List<City> getEmptyList(int limit){
		List<City> list = new ArrayList<>();
		for(int i = 0; i < limit; i++)
			list.add(null);
		
		return list;
	}
	
	
	private static void addRemaining(List<City> child, List<City> firstParent , List<City> secondParent, int cutPoint1, int cutPoint2, int childFinalLength){
		List<City> nonReplaceable = firstParent.subList(cutPoint1, cutPoint2);
		int index = 0;
		
		// initialize with  null values until final length is satisfied
		for(int i = cutPoint2; i < childFinalLength; i++){
			child.add(null);
		}
		
		List<City> remaining = new ArrayList<>();
		for(int j = cutPoint2; j < childFinalLength; j++){
			remaining.add(secondParent.get(j));
		}
		
		for(int k = 0; k < cutPoint2; k++){
			remaining.add(secondParent.get(k));
		}
		
		// add remaining cities from parent that are not present in child
		for(int l = 0; l < remaining.size(); l++){
			if(nonReplaceable.contains(remaining.get(l))) continue;
			else if(childContainsCity(child, childFinalLength, remaining.get(l), cutPoint1, cutPoint2)) continue;
			else{
				if(index == cutPoint1){
					index = cutPoint2;
				}
				
				child.set(index, remaining.get(l));
				index++;
			}
		}
		
	}
	
	private static boolean childContainsCity(List<City> child, int size, City element, int cutPoint1, int cutPoint2){
		for(int i = 0; i < cutPoint1; i ++){
			if( (child.get(i) != null) && (child.get(i).getIndex() == element.getIndex()))
				return true;
		}
		
		for(int i = cutPoint2; i < size; i ++){
			if( (child.get(i) != null) && (child.get(i).getIndex() == element.getIndex()))
				return true;
		}
		
		return false;
	}
	
	
	private static int getIndexOfWorstIndividualFromPopulationForTSP(List<List<City>> population){
		int index, worstFitness = 0, tempFitness = 0;
		int populationSize = population.size();
		
		worstFitness = TSPUtil.computeTotalCost(population.get(0));
		index = 0;
		
		for(int i = 1; i < populationSize; i++){
			tempFitness = TSPUtil.computeTotalCost(population.get(i));
			if(tempFitness > worstFitness){
				worstFitness = tempFitness;
				index = i;
			}
		}
		
		return index;
	}
	/****************************************************************************************************************************************************/
	
	
}
