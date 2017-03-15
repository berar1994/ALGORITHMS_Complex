package com.disi.geo.main;

import java.util.ArrayList;
import java.util.List;

import com.disi.geo.compute.ExhaustiveAlgorithm;
import com.disi.geo.compute.GreedyAlgorithm;
import com.disi.geo.compute.RandomSearchAlgorithm;
import com.disi.geo.compute.SAHCAlgorithm;
import com.disi.geo.model.ItemModel;
import com.disi.geo.utils.FileUtil;


public class Main {
	
	final static int 	NR_OF_ITERATIONS = 10;
	final static String INPUT_FILE_PATH = "rucsac-20.txt";
	final static String LINE_SPLIT_REGEX = "\\s+";
	
	final static String RANDOM_SEARCH_SOLUTION_OUTPUT_FILE_NAME = "C:/Users/GEO/Desktop/FACULTATE/An 4- Sem 2/DISI/solutions/random-search/rucsac-solutie-";
	final static int 	RANDOM_SEARCH_OPTIMUM_ITERATIONS = 10000;
	final static int 	RANDOM_SEARCH_RUN_FOR = 10; 
	
	final static String GREEDY_SOLUTION_OUTPUT_FILE_NAME = "C:/Users/GEO/Desktop/FACULTATE/An 4- Sem 2/DISI/solutions/greedy/rucsac-solutie-";
	
	final static String EXHAUSTIVE_SOLUTION_OUTPUT_FILE_NAME = "C:/Users/GEO/Desktop/FACULTATE/An 4- Sem 2/DISI/solutions/exhaustive/rucsac-solutie-";
	
	final static String SAHC_SOLUTION_OUTPUT_FILE_NAME = "C:/Users/GEO/Desktop/FACULTATE/An 4- Sem 2/DISI/solutions/sahc/rucsac-solutie-";
	final static int 	SAHC_OPTIMUM_ITERATIONS = 10000;
	final static int 	SAHC_RUN_FOR = 10;
	
	public static void main(String[] args) {	
		List<ItemModel> items = new ArrayList<>();
		int nrOfItems = 0, knapsackCapacity = 0;
		// read from file
		List<String> fileContent = FileUtil.readDataFromFile(INPUT_FILE_PATH);
		
		if( (fileContent != null) && !(fileContent.isEmpty()) ){
			// read the number of items
			nrOfItems = Integer.parseInt(fileContent.get(0).trim());
			
			// read the list of items
			for(int itemIndex = 1; itemIndex <= nrOfItems; itemIndex++){
				String[] line = fileContent.get(itemIndex).trim().split(LINE_SPLIT_REGEX);
				// add the items to a list
				items.add(new ItemModel(Integer.parseInt(line[0]) - 1, Integer.parseInt(line[1]), Integer.parseInt(line[2])));
			}
			
			// read the knapsack maximum capacity allowed
			knapsackCapacity = Integer.parseInt(fileContent.get(nrOfItems+1).trim());

		}
		else{
			System.out.println("File content is empty.");
		}

		System.out.println("***********************");
		System.out.println("Problem inputs");
		System.out.println("NR. OF ITEMS -- " + nrOfItems);
		System.out.println("KNAPSACK CAPACITY -- " + knapsackCapacity);
		System.out.println("***********************");
		System.out.println("Computing...");
		
		/*************************************Random Search**********************************/
		// run RandomSearch only once
		//runRandomSearch(items, knapsackCapacity, nrOfItems);
		
		//run RandomSearch multiple times with optimum iterations
		//for(int i = 0; i < RANDOM_SEARCH_RUN_FOR; i++){
			//runRandomSearchWithOptimumIterations(i, items, knapsackCapacity, nrOfItems);
		//}
		/***********************************************************************************/
		
		
		/************************************Greedy*****************************************/
		//runGreedy(items, knapsackCapacity, nrOfItems);
		/***********************************************************************************/
		
		
		/**************************************Exhaustive***********************************/
		//runExhaustive(items, knapsackCapacity, nrOfItems);
		/***********************************************************************************/
		
		/*************************************SAHC******************************************/
		// run SAHC only once
		//runSteepestAscentHillClimbing(items, knapsackCapacity, nrOfItems);
		
		// run SAHC multiple times with optimum iterations
//		for(int i = 0; i < SAHC_RUN_FOR; i++){
//			runSteepestAscentHillClimbingWithOptimumIterations(i, items, knapsackCapacity, nrOfItems);
//		}
		/***********************************************************************************/
		
		
		
	}
	
	
	
	/*
	 * @description - run the Random Search algorithm
	 * @param items - the list of items to put in the knapsack
	 * @param knapsackCapacity - the maximum allowed capacity for the knapsack
	 * @param nrOfItems - the total number of items
	 */
	private static void runRandomSearch(List<ItemModel> items, int knapsackCapacity, int nrOfItems){
		String solutionOutputFile = RANDOM_SEARCH_SOLUTION_OUTPUT_FILE_NAME + nrOfItems + "-iter-" + NR_OF_ITERATIONS + ".txt";
		// if the file already exists, delete it
		FileUtil.deleteFileIfExists(solutionOutputFile);
		// compute
		RandomSearchAlgorithm.compute(NR_OF_ITERATIONS, items, knapsackCapacity, nrOfItems, solutionOutputFile);
	}
	
	
	/*
	 * @description - run the Random Search algorithm with an optimum number of iterations
	 * @param items - the list of items to put in the knapsack
	 * @param knapsackCapacity - the maximum allowed capacity for the knapsack
	 * @param nrOfItems - the total number of items
	 */
	private static void runRandomSearchWithOptimumIterations(int solutionIndex, List<ItemModel> items, int knapsackCapacity, int nrOfItems){
		String solutionOutputFile = RANDOM_SEARCH_SOLUTION_OUTPUT_FILE_NAME + nrOfItems + "-iter-" + RANDOM_SEARCH_OPTIMUM_ITERATIONS + "-" + solutionIndex + ".txt";
		// if the file already exists, delete it
		FileUtil.deleteFileIfExists(solutionOutputFile);
		// compute
		RandomSearchAlgorithm.compute(RANDOM_SEARCH_OPTIMUM_ITERATIONS, items, knapsackCapacity, nrOfItems, solutionOutputFile);
	}
	
	
	
	/*
	 * @description - run the Greedy algorithm
	 * @param items - the list of items to put in the knapsack
	 * @param knapsackCapacity - the maximum allowed capacity for the knapsack
	 * @param nrOfItems - the total number of items
	 */
	private static void runGreedy(List<ItemModel> items, int knapsackCapacity, int nrOfItems){
		String solutionOutputFile1 = GREEDY_SOLUTION_OUTPUT_FILE_NAME + nrOfItems + "-ratio" + ".txt";
		String solutionOutputFile2 = GREEDY_SOLUTION_OUTPUT_FILE_NAME + nrOfItems + "-min-weight" + ".txt";
		String solutionOutputFile3 = GREEDY_SOLUTION_OUTPUT_FILE_NAME + nrOfItems + "-max-value" + ".txt";
		
		// if the files already exist, delete them
		FileUtil.deleteFileIfExists(solutionOutputFile1);
		FileUtil.deleteFileIfExists(solutionOutputFile2);
		FileUtil.deleteFileIfExists(solutionOutputFile3);
		
		// compute with optimum ratio
		GreedyAlgorithm.computeUsingOptimumRatio(items, knapsackCapacity, nrOfItems, solutionOutputFile1);
		// compute with min weight
		GreedyAlgorithm.computeUsingTheMinWeight(items, knapsackCapacity, nrOfItems, solutionOutputFile2);
		// compute with max value
		GreedyAlgorithm.computeUsingTheMaxValue(items, knapsackCapacity, nrOfItems, solutionOutputFile3);
	}
	
	
	/*
	 * @description - run the Exhaustive algorithm - brute force
	 * @param items - the list of items to put in the knapsack
	 * @param knapsackCapacity - the maximum allowed capacity for the knapsack
	 * @param nrOfItems - the total number of items
	 */
	private static void runExhaustive(List<ItemModel> items, int knapsackCapacity, int nrOfItems){
		String solutionOutputFile = EXHAUSTIVE_SOLUTION_OUTPUT_FILE_NAME + nrOfItems + ".txt";
		// if the file already exists, delete it
		FileUtil.deleteFileIfExists(solutionOutputFile);
		//compute
		ExhaustiveAlgorithm.compute(items, knapsackCapacity, nrOfItems, solutionOutputFile);
	}
	
	
	/*
	 * @description - run the SAHC algorithm
	 * @param items - the list of items to put in the knapsack
	 * @param knapsackCapacity - the maximum allowed capacity for the knapsack
	 * @param nrOfItems - the total number of items
	 */
	private static void runSteepestAscentHillClimbing(List<ItemModel> items, int knapsackCapacity, int nrOfItems){
		String solutionOutputFile = SAHC_SOLUTION_OUTPUT_FILE_NAME + nrOfItems + "-iter-" + NR_OF_ITERATIONS + ".txt";
		// if the file already exists, delete it
		FileUtil.deleteFileIfExists(solutionOutputFile);
		// compute
		SAHCAlgorithm.compute(NR_OF_ITERATIONS, items, knapsackCapacity, nrOfItems, solutionOutputFile);
	}
	
	
	/*
	 * @description - run the SAHC algorithm with an optimum number of iterations
	 * @param items - the list of items to put in the knapsack
	 * @param knapsackCapacity - the maximum allowed capacity for the knapsack
	 * @param nrOfItems - the total number of items
	 */
	private static void runSteepestAscentHillClimbingWithOptimumIterations(int solutionIndex, List<ItemModel> items, int knapsackCapacity, int nrOfItems){
		String solutionOutputFile = SAHC_SOLUTION_OUTPUT_FILE_NAME + nrOfItems + "-iter-" + SAHC_OPTIMUM_ITERATIONS + "-" + solutionIndex + ".txt";
		// if the file already exists, delete it
		FileUtil.deleteFileIfExists(solutionOutputFile);
		// compute
		SAHCAlgorithm.compute(SAHC_OPTIMUM_ITERATIONS, items, knapsackCapacity, nrOfItems, solutionOutputFile);
	}
	
	
	
	
	

}
