package com.disi.geo.compute;

import java.util.List;

import com.disi.geo.model.ItemModel;
import com.disi.geo.utils.FileUtil;

public class ExhaustiveAlgorithm {
	
	
	public static void compute(List<ItemModel> items, int knapsackCapacity, int nrOfItems, String outputFile){
		int solutionSpace = (int)Math.pow(2, nrOfItems);
		int j = 0, tempWeight = 0, tempValue = 0, bestValue = 0, bestWeight = 0;
		int[] generatedSolution = new int[nrOfItems];
		int[] bestSolution = new int[nrOfItems];
		
		long startTime = System.nanoTime();
		
		for(int i = 0; i < solutionSpace; i++ ){
			j = nrOfItems - 1;
			tempWeight = 0;
			tempValue = 0;
			
			while( (generatedSolution[j] != 0) && (j > 0) ){
				generatedSolution[j] = 0;
				j--;
			}
			
			generatedSolution[j] = 1;
			
			for(int k = 0; k < nrOfItems; k++){
				if(generatedSolution[k] == 1){
					tempWeight += items.get(k).getWeight();
					tempValue += items.get(k).getValue();
				}
			}
			
			
			if( (tempValue > bestValue) && (tempWeight <= knapsackCapacity) ){
				bestValue = tempValue;
				bestWeight = tempWeight;
				System.arraycopy( generatedSolution, 0, bestSolution, 0, nrOfItems );
				FileUtil.writeToFile(outputFile, bestSolution, bestValue);
			}
			
		}
		
		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 1000000; // milliseconds
		
		// write solution to file
		FileUtil.writeToFile(outputFile, "***Solution***");
		FileUtil.writeToFile(outputFile, bestSolution, bestValue);
		FileUtil.writeToFile(outputFile, "Weight -- " + bestWeight);
		FileUtil.writeToFile(outputFile, "Total time -- " + duration);
		System.out.println("EXHAUSTIVE FINISHED !");	
		
	}
	
	
	
	
	
	
}
