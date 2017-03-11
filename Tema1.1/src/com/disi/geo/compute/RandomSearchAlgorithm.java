package com.disi.geo.compute;

import java.util.List;

import com.disi.geo.model.ItemModel;
import com.disi.geo.utils.FileUtil;
import com.disi.geo.utils.RandomUtil;

public class RandomSearchAlgorithm {
	
	
	/*
	 * @description - main method to compute the random search
	 * @param nrOfIterations - nr of iterations that algorithm has to execute
	 * @param objects - the list of objects to put in the knapsack
	 * @param knapsackCapacity - the maximum capacity of knapsack
	 * @param nrOfItems - the total nr of given items
	 * @param outputFile - the output file to write the solutions
	 */
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
	
	
	
	/*
	 * @description - generate a random solution
	 * @param knapsackCapacity - the maximum allowed capacity that random solution must not overcome
	 * @param items - the list of items for generating the random solution
	 * @return - an array with 1s and 0s having the given length 
	 */
	private static int[] generateRandomSolution(int knapsackCapacity, List<ItemModel> items){
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
	
	
	/*
	 * @description - verify if a random generated solution is a valid one
	 * @param solution - random generated array of 1s and 0s
	 * @param knapsackCapacity - the maximum allowed capacity that random solution must not overcome
	 * @param items - the list of items to verify
	 * @return true if the solution is valid and doesn't overcome the capacity
	 * 		   false otherwise
	 */
	private static boolean isSolutionValid(int[] solution, int knapsackCapacity, List<ItemModel> items){
		int solutionLength = solution.length;
		int solutionAccumulatedCapacity = 0;
		
		for(int i = 0; i < solutionLength; i++){
			if(solution[i] == 1) solutionAccumulatedCapacity += items.get(i).getWeight();
		}

		return solutionAccumulatedCapacity == knapsackCapacity ? true : false;
	}
	
	
	/*
	 * @description - get the total value of solution
	 * @param solution - an array of 1s and 0s representing the selected items
	 * @param items - the list of items
	 * @return the total value of valid solution
	 */
	private static int evaluateValueOfRandomSolution(int[] solution, List<ItemModel> items){
		int totalValue = 0;
		int solutionLength = solution.length;
		
		for(int i = 0; i < solutionLength; i++){
			if(solution[i] == 1) totalValue += items.get(i).getValue();
		}
		
		return totalValue;
	}
	
}
