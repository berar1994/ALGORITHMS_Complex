package com.disi.geo.main;

import java.util.List;
import java.util.Scanner;

import com.disi.geo.compute.GeneticAlgorithm;
import com.disi.geo.model.City;
import com.disi.geo.model.TSP;
import com.disi.geo.util.FileUtil;
import com.disi.geo.util.RegexUtil;
import com.disi.geo.util.ResourcesUtil;


public class Main {

	static final int NR_OF_BITS = 1000;
	static final int NR_OF_GENERATIONS = 10;

	public static void main(String[] args) {
		printMenu();

		Scanner sc = new Scanner(System.in);
		int option = sc.nextInt();
		switch (option) {
		case 1:
			runOneMaxProblemWithGeneticAlgorithm();
			break;
		case 2:
			runTSPWithGeneticAlgorithm();
			break;
		default:
			System.out.println("NO OPTION SELECTED !");
			break;
		}
		
		sc.close();
	}

	
	
	private static void runOneMaxProblemWithGeneticAlgorithm() {
		GeneticAlgorithm.computeOneMaxProblem(NR_OF_BITS, NR_OF_GENERATIONS);
	}

	
	
	private static void runTSPWithGeneticAlgorithm() {
		// read from file
		List<String> fileContent = FileUtil.readDataFromFile(ResourcesUtil.INPUT_FILE_EIL51.value());

		TSP tspInstance = new TSP();
		// get number of input cities
		String numberOfCitiesLine = fileContent.get(3).trim();
		Integer numberOfCities = Integer.parseInt(numberOfCitiesLine.split(RegexUtil.LINE_SPLIT_POINTS_REGEX.value())[1].trim());
		tspInstance.setNumberOfCities(numberOfCities);

		for (int i = 6; i < fileContent.size() - 1; i++) {
			String[] line = fileContent.get(i).trim().split(RegexUtil.LINE_SPLIT_SPACE_REGEX.value());
			tspInstance
					.addCity(new City(Integer.parseInt(line[0]), Integer.parseInt(line[1]), Integer.parseInt(line[2])));
		}
		
		GeneticAlgorithm.computeTSPProblem(tspInstance, NR_OF_GENERATIONS);
	}

	
	
	private static void printMenu() {
		System.out.println("1. OneMax");
		System.out.println("2. TSP");
	}

}
