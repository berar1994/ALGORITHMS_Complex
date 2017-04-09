package com.disi.geo.utils;

import java.util.Random;

public class RandomUtil {

	static Random randomGenerator = new Random();

	public static int generateRandomNumberBetween(int minBound, int highBound) {
		return randomGenerator.nextInt(highBound - minBound) + minBound;
	}

}
