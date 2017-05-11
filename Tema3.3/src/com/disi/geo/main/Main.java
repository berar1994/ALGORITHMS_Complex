package com.disi.geo.main;

import com.disi.geo.compute.GeneticAlgorithm;

public class Main {
	
	static final Integer NR_OF_BITS = 10;
	
	public static void main(String[] args){
		GeneticAlgorithm.computeOneMaxProblem(NR_OF_BITS);
	}
	
}
