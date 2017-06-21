package com.disi.geo.compute;

import java.util.List;

import com.disi.geo.model.ItemModel;
import com.disi.geo.utils.FileUtil;
import com.disi.geo.utils.RandomUtil;

public class RandomSearchAlgorithm {
	
	
	public static void compute(int nrOfIterations, List<ItemModel> items, int knapsackCapacity, int nrOfItems, String outputFile){
		int randomSolutionValue = 0;
		int bestSolutionValue = 0;
		int[] bestSolution = new int[nrOfItems];
		int[] generatedRandomArray = new int[nrOfItems];
		
		long startTime = System.nanoTime();
		
		for(int iteration = 0; iteration < nrOfIterations; iteration++){
			generatedRandomArray = generateRandomSolution(knapsackCapacity, items);
			randomSolutionValue = evaluateValueOfRandomSolution(generatedRandomArray, items);
			if(randomSolutionValue > bestSolutionValue){
				bestSolutionValue = randomSolutionValue;
				bestSolution = generatedRandomArray;
			}
			// write solution to file
			FileUtil.writeToFile(outputFile, generatedRandomArray, randomSolutionValue);
		}
		
		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 1000000; // milliseconds
		
		// write best solution to file
		FileUtil.writeToFile(outputFile, "***Best solution***");
		FileUtil.writeToFile(outputFile, bestSolution, bestSolutionValue);
		FileUtil.writeToFile(outputFile, "Total time -- " + duration);
		System.out.println("RANDOM SEARCH FINISHED !");		
	}
	
	
	
	public static int[] generateRandomSolution(int knapsackCapacity, List<ItemModel> items){
		int arrayLength = items.size();
		int[] generatedRandomArray =  new int[arrayLength];
		boolean foundSolution = false;
		
		// while a generated random solution is not valid, keep generate others
		while(!foundSolution){
			for(int i = 0; i < arrayLength; i++){
				generatedRandomArray[i] = RandomUtil.generateRandomNumberBetween(0, 2); // generates 0 or 1
			}
			// check if the generated array is a valid solution
			if(isSolutionValid(generatedRandomArray, knapsackCapacity, items)){
				foundSolution = true;
			}
		}
		
		return generatedRandomArray;
	}
	

	public static boolean isSolutionValid(int[] solution, int knapsackCapacity, List<ItemModel> items){
		int solutionLength = solution.length;
		int solutionAccumulatedCapacity = 0;
		
		for(int i = 0; i < solutionLength; i++){
			if(solution[i] == 1) solutionAccumulatedCapacity += items.get(i).getWeight();
		}

		return solutionAccumulatedCapacity == knapsackCapacity ? true : false;
	}
	
	
	public static int evaluateValueOfRandomSolution(int[] solution, List<ItemModel> items){
		int totalValue = 0;
		int solutionLength = solution.length;
		
		for(int i = 0; i < solutionLength; i++){
			if(solution[i] == 1) totalValue += items.get(i).getValue();
		}
		
		return totalValue;
	}
	
}
