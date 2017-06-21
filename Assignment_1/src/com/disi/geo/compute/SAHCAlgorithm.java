package com.disi.geo.compute;

import java.util.List;

import com.disi.geo.model.ItemModel;
import com.disi.geo.utils.FileUtil;

public class SAHCAlgorithm {

	public static void compute(int nrOfIterations, List<ItemModel> items, int knapsackCapacity, int nrOfItems,
			String outputFile) {

		int[] currentHilltop = new int[nrOfItems];
		int currentHilltopValue = 0;
		int nrOfNeighbours = 0;
		int[] bestHilltop = new int[nrOfItems];
		int bestValue = 0;
		boolean skipStepOne = false;

		long startTime = System.nanoTime();
		
		for (int iteration = 0; iteration < nrOfIterations; iteration++) {
			if (!skipStepOne) {
				// select a random point from searching space
				currentHilltop = RandomSearchAlgorithm.generateRandomSolution(knapsackCapacity, items);
			}

			// evaluate value for currentHilltop
			currentHilltopValue = evaluate(currentHilltop, items);
			// get number of neighbours
			nrOfNeighbours = getNumberOfNeighbours(currentHilltop);
			// generate all neighbours
			int[][] neighbours = generateAllNeighbours(currentHilltop, nrOfNeighbours, nrOfItems);
			// get the best neighbour and his value
			int[] bestNeighbourAndHisValue = getBestNeighbourAndHisValue(neighbours, items, nrOfNeighbours, nrOfItems,
					knapsackCapacity);

			if (bestNeighbourAndHisValue[1] > currentHilltopValue) {
				currentHilltopValue = bestNeighbourAndHisValue[1];
				for (int i = 0; i < nrOfItems; i++) {
					currentHilltop[i] = neighbours[bestNeighbourAndHisValue[0]][i];
				}

				skipStepOne = true;

			} else {
				// none of neighbours is better than the current hilltop, save it and start over
				FileUtil.writeToFile(outputFile, currentHilltop, currentHilltopValue);
				skipStepOne = false;
			}
			
			if (currentHilltopValue > bestValue) {
				bestValue = currentHilltopValue;
				System.arraycopy(currentHilltop, 0, bestHilltop, 0, nrOfItems);
			}
		}

		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 1000000; // milliseconds
		
		// write best solution to file
		FileUtil.writeToFile(outputFile, "***Best solution***");
		FileUtil.writeToFile(outputFile, bestHilltop, bestValue);
		FileUtil.writeToFile(outputFile, "Total time -- " + duration);
		System.out.println("SAHC ALGORITHM FINISHED.");

	}

	private static int getNumberOfNeighbours(int[] currentHilltop) {
		int nrOfNeighbours = 0, size = currentHilltop.length;

		for (int i = 0; i < size; i++) {
			if (currentHilltop[i] == 0)
				nrOfNeighbours++;
		}
		return nrOfNeighbours;
	}

	private static int[][] generateAllNeighbours(int[] currentHilltop, int nrOfNeighbours, int nrOfItems) {
		int[][] neighbours = new int[nrOfNeighbours][nrOfItems];
		int index = 0;

		for (int i = 0; i < nrOfNeighbours; i++) {
			for (int j = 0; j < nrOfItems; j++) {
				neighbours[i][j] = currentHilltop[index];
				index++;
			}
			index = 0;
		}

		index = 0;
		for (int i = 0; i < nrOfItems; i++) {
			if (currentHilltop[i] == 0) {
				neighbours[index][i] = 1;
				index++;
			}
		}

		return neighbours;
	}

	private static int[] getBestNeighbourAndHisValue(int[][] neighbours, List<ItemModel> items, int nrOfNeighbours,
			int nrOfItems, int knapsackCapacity) {
		int[] result = new int[2]; // 0 - index of neighbour, 1 - the value
		int bestValue = 0, cumulatedValue = 0, cumulatedWeight = 0;

		for (int i = 0; i < nrOfNeighbours; i++) {
			for (int j = 0; j < nrOfItems; j++) {
				if (neighbours[i][j] == 1) {
					if( (cumulatedWeight + items.get(j).getWeight()) <= knapsackCapacity ){
						cumulatedWeight += items.get(j).getWeight();
						cumulatedValue += items.get(j).getValue();
					}
				}
			}

			if (cumulatedValue > bestValue) {
				bestValue = cumulatedValue;
				result[0] = i;
				result[1] = bestValue;
			}

			cumulatedValue = 0;
			cumulatedWeight = 0;
		}

		return result;
	}

	private static int evaluate(int[] chosenItems, List<ItemModel> items) {
		int totalValue = 0, length = chosenItems.length;
		for (int i = 0; i < length; i++) {
			if (chosenItems[i] == 1)
				totalValue += items.get(i).getValue();
		}
		return totalValue;
	}

}
