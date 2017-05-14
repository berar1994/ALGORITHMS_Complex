package com.disi.geo.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	
	public static List<List<City>> computeParentsPMXCrossoverForTSP(List<List<City>> parents){
		List<List<City>> children = new ArrayList<>();
		int cutPoint1 = 0, cutPoint2 = 0;
		boolean cutPointsGenerated = false;
		int permutationSize = parents.get(0).size();
		List<City> firstChild = null, firstParent = null;
		List<City> secondChild = null, secondParent = null;
		int i = 0;
		int size = parents.size();
		Map<City, City> mappings = null;
		
		while(i < (size - 1)){
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
			
			firstChild.addAll(cutPoint1, secondParent.subList(cutPoint1, cutPoint2));
			secondChild.addAll(cutPoint1, firstParent.subList(cutPoint1, cutPoint2));
			
			mappings = getMappings(firstParent.subList(cutPoint1, cutPoint2), secondParent.subList(cutPoint1, cutPoint2));
			
			addRemainingFirstPart(firstParent.subList(0, cutPoint1), firstChild, cutPoint1, mappings);
			addRemainingFirstPart(secondParent.subList(0, cutPoint1), secondChild, cutPoint1, mappings);
			
			addRemainingLastPart(firstParent.subList(cutPoint2, permutationSize), firstChild, mappings);
			addRemainingLastPart(secondParent.subList(cutPoint2, permutationSize), secondChild, mappings);
			
			children.add(firstChild);
			children.add(secondChild);
			
			i = i + 2;
		}	
		
		
		return children;
	}
	
	
	private static List<City> getEmptyList(int limit){
		List<City> list = new ArrayList<>();
		for(int i = 0; i < limit; i++)
			list.add(null);
		
		return list;
	}
	
	private static Map<City, City> getMappings(List<City> first, List<City> second){
		Map<City, City> mappings = new HashMap<>();
		int size = first.size();
		
		for(int i = 0; i < size; i++){
			mappings.put(first.get(i), second.get(i));
		}
		
		return mappings;
	}
	
	private static void addRemainingFirstPart(List<City> part, List<City> child, int endCutPoint, Map<City, City> mappings){
		int size = child.size();
		boolean addCity = true;
		
		for(int i = 0; i < endCutPoint; i++){
			City city = part.get(i);
			for(int j = 0; j < size; j++){
				if( (child.get(j) != null) && (child.get(j).getIndex() == city.getIndex()) ){
					// get from mappings
					for(Map.Entry<City, City> entry : mappings.entrySet()){
						if(entry.getKey().getIndex() == city.getIndex()){
							child.set(i, entry.getValue());
							break;
						}
						else if(entry.getValue().getIndex() == city.getIndex()){
							child.set(i, entry.getKey());
							break;
						}
					}
					
					addCity = false;
					break;
				}
			}
			if(addCity){
				child.set(i, city);
			}
			
			addCity = true;
		}
		
	}
	
	
	private static void addRemainingLastPart(List<City> part, List<City> child, Map<City, City> mappings){
		int partSize = part.size();
		int childSize = child.size();
		boolean addCity = true;
		
		for(int i = 0; i < partSize; i++){
			City city = part.get(i);
			for(int j = 0; j < childSize; j++){
				if( (child.get(j) != null) && (child.get(j).getIndex() == city.getIndex()) ){
					// get from mappings
					for(Map.Entry<City, City> entry : mappings.entrySet()){
						if(entry.getKey().getIndex() == city.getIndex()){
							child.add(entry.getValue());
							break;
						}
						else if(entry.getValue().getIndex() == city.getIndex()){
							child.add(entry.getKey());
							break;
						}
					}
					
					addCity = false;
					break;
				}
			}
			
			if(addCity){
				child.add(city);
			}
			
			addCity = true;
		}
		
	}
	/****************************************************************************************************************************************************/
	
	
}
