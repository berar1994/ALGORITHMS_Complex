package com.disi.geo.compute;

import java.util.Collections;
import java.util.List;

import com.disi.geo.model.ItemModel;
import com.disi.geo.utils.FileUtil;

public class GreedyAlgorithm {

	public static void computeUsingOptimumRatio(List<ItemModel> items, int knapsackCapacity, int nrOfItems, String outputFile) {
		// calculate the value-weight ratio for each item
		for (ItemModel item : items) {
			float valueToWeightRatio = ((float) item.getValue() / item.getWeight());
			item.setValueToWeightRatio(valueToWeightRatio);
		}

		// sort in non-increasing order by value-to-weight ratio
		Collections.sort(items, ItemModel.compareByRatio());
		Collections.reverse(items);

		// compute using this metric
		compute(items, knapsackCapacity, nrOfItems, outputFile);
	}
	
	
	public static void computeUsingTheMinWeight(List<ItemModel> items, int knapsackCapacity, int nrOfItems, String outputFile){		
		// sort in non-increasing order
		Collections.sort(items, ItemModel.compareByWeight());

		// compute using this metric
		compute(items, knapsackCapacity, nrOfItems, outputFile);
	}

	
	public static void computeUsingTheMaxValue(List<ItemModel> items, int knapsackCapacity, int nrOfItems, String outputFile){		
		// sort in non-increasing order
		Collections.sort(items, ItemModel.compareByValue());
		Collections.reverse(items);
		
		// compute using this metric
		compute(items, knapsackCapacity, nrOfItems, outputFile);
	}
	
	
	/*
	 * @description - compute solution based on a list of items that is sorted by a metric
	 * @param sortedList - the list of items sorted by a metric: max value, min weight or value/weight ratio
	 * @param knapsackCapacity - the maximum allowed capacity for the knapsack
	 * @param nrOfItems - the total number of items
	 * @param outputFile - the output file to write the solutions
	 */
	private static void compute(List<ItemModel> sortedList, int knapsackCapacity, int nrOfItems, String outputFile) {
		int cumulatedWeight = 0;
		int totalValue = 0;
		int[] solution = new int[nrOfItems];
		boolean foundSolution = false;
		int index = 0;
		int nrOfRemainingItems = nrOfItems;
		
		long startTime = System.nanoTime();
		
		while (!foundSolution) {
			ItemModel item = sortedList.get(index);
			if ((item.getWeight() + cumulatedWeight) <= knapsackCapacity) {
				cumulatedWeight += item.getWeight();
				totalValue += item.getValue();
				solution[item.getIndex()] = 1;
				sortedList.remove(item);
				index = 0;
				nrOfRemainingItems = sortedList.size();
			} else {
				index++;
				if (index == nrOfRemainingItems) {
					foundSolution = true;
				}
			}

			if (cumulatedWeight == knapsackCapacity) {
				foundSolution = true;
			}
		}

		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 1000000; // milliseconds
		
		// write solution to file
		FileUtil.writeToFile(outputFile, "***Solution***");
		FileUtil.writeToFile(outputFile, solution, totalValue);
		FileUtil.writeToFile(outputFile, "Weight -- " + cumulatedWeight);
		FileUtil.writeToFile(outputFile, "Total time -- " + duration);
		System.out.println("GREEDY FINISHED !");	
	}

}
