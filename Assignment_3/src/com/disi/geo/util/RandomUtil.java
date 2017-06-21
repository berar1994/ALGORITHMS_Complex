package com.disi.geo.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomUtil {
	
	static Random randomGenerator = new Random();

	public static int generateRandomNumberBetween(int minBound, int highBound) {
		return randomGenerator.nextInt(highBound - minBound) + minBound;
	}

	public static double generateRandomNumberBetweenOneAndZero() {
		return randomGenerator.nextDouble();
	}
	
	public static int[] generateRandomArray(int length){
		int[] randomArray = new int[length];
		for(int i = 0; i < length; i++){
			randomArray[i] = generateRandomNumberBetween(0, 2);
		}
		return randomArray;
	}
	
	public static List<Integer> generateRandomList(int length){
		List<Integer> randomList = new ArrayList<>(length);
		for(int i = 0; i < length; i++){
			randomList.add(generateRandomNumberBetween(0, 2));
		}
		return randomList;
	}
	
	public static List<List<Integer>> generateListOfRandomLists(int nrOfLists, int length){
		List<List<Integer>> randomLists = new ArrayList<>();
		for(int i = 0; i < nrOfLists; i++){
			List<Integer> randomList = new ArrayList<>(length);
			for(int j = 0; j < length; j++){
				randomList.add(generateRandomNumberBetween(0, 2));
			}
			
			randomLists.add(randomList);
		}
		return randomLists;
	}
	
}
