package com.disi.geo.main;

import java.util.List;

import com.disi.geo.compute.ExhaustiveAlgorithm;
import com.disi.geo.compute.GreedyAlogorithm;
import com.disi.geo.compute.LocalSearchAlgorithm;
import com.disi.geo.compute.SimulatedAnnealingAlgorithm;
import com.disi.geo.model.City;
import com.disi.geo.model.TSPInstance;
import com.disi.geo.utils.FileUtil;

public class Main {
	
	// iterations
	final static Integer ITERATIONS = 1000000;
	final static Integer LOCAL_SEARCH_OPTIMUM_ITERATIONS = 100000;
	final static Integer SIMULATED_ANNEALING_OPTIMUM_ITERATIONS = 10000;
	
	
	// input files
	final static String INPUT_FILE_EIL51 = "eil51.tsp";
	final static String INPUT_FILE_EIL76 = "eil76.tsp";
	final static String INPUT_FILE_EIL101 = "eil101.tsp";
	final static String INPUT_FILE_GIL262 = "gil262.tsp";
	final static String INPUT_FILE_EXHAUSTIVE = "exhaustive.txt";
	
	// output files
	final static String OUTPUT_FOLDER_EXHAUSTIVE = "C:/Users/GEO/Desktop/FACULTATE/An 4- Sem 2/DISI/solutions/exhaustive_tsp/";
	final static String OUTPUT_FILE_EXHAUSTIVE = "exhaustive_<nrOfCities>_<iterations>_<iteration>.txt";
	final static String OUTPUT_FOLDER_LOCAL_SEARCH = "C:/Users/GEO/Desktop/FACULTATE/An 4- Sem 2/DISI/solutions/localsearch_tsp/";
	final static String OUTPUT_FILE_LOCAL_SEARCH = "localsearch_cities<nrOfCities>_iterations<iterations>_iter<iteration>.txt";
	final static String OUTPUT_FOLDER_GREEDY = "C:/Users/GEO/Desktop/FACULTATE/An 4- Sem 2/DISI/solutions/greedy_tsp/";
	final static String OUTPUT_FILE_GREEDY = "greedy_cities<nrOfCities>.txt";
	final static String OUTPUT_FILE_OPTIMIZED_GREEDY = "optimized_greedy_cities<nrOfCities>.txt";
	final static String OUTPUT_FOLDER_SIMULATED_ANNEALING = "C:/Users/GEO/Desktop/FACULTATE/An 4- Sem 2/DISI/solutions/simulated_annealing_tsp/";
	final static String OUTPUT_FILE_SIMULATED_ANNEALING = "simulated_annealing<nrOfCities>_iterations<iterations>_iter<iteration>.txt";
	
	final static String LINE_SPLIT_SPACE_REGEX = " ";
	final static String LINE_SPLIT_POINTS_REGEX = ":";

	
	
	
	
	public static void main(String[] args) {
		// read from file
		List<String> fileContent = FileUtil.readDataFromFile(INPUT_FILE_EIL51);
		
		TSPInstance tspInstance = new TSPInstance();
		// get number of input cities
		String numberOfCitiesLine = fileContent.get(3).trim();
		Integer numberOfCities = Integer.parseInt(numberOfCitiesLine.split(LINE_SPLIT_POINTS_REGEX)[1].trim());
		tspInstance.setNumberOfCities(numberOfCities);
		
		for(int i = 6; i < fileContent.size() - 1; i++){
			String[] line = fileContent.get(i).trim().split(LINE_SPLIT_SPACE_REGEX);
			tspInstance.addCity(new City(Integer.parseInt(line[0]), Integer.parseInt(line[1]), Integer.parseInt(line[2])));
		}
		
		
		
		
		/******************************************************************************************************************/
		//runExhaustive(tspInstance);
		/******************************************************************************************************************/
		
		
		/******************************************************************************************************************/
		//runLocalSearch(tspInstance, ITERATIONS);
		//runLocalSearchWithOptimumIterations(tspInstance, LOCAL_SEARCH_OPTIMUM_ITERATIONS);
		/******************************************************************************************************************/
		
		/******************************************************************************************************************/
		//runGreedy(tspInstance);
		//runOptimizedGreedy(tspInstance);
		/******************************************************************************************************************/
		
		/******************************************************************************************************************/
		//runSimulatedAnnealing(tspInstance, ITERATIONS);
		runSimulatedAnnealingWithOptimumIterations(tspInstance, SIMULATED_ANNEALING_OPTIMUM_ITERATIONS);
		/******************************************************************************************************************/
		
	}
	
	
	private static void runExhaustive(TSPInstance tspInstance){
		String exhaustiveOutputFile = OUTPUT_FOLDER_EXHAUSTIVE + OUTPUT_FILE_EXHAUSTIVE;
		exhaustiveOutputFile = exhaustiveOutputFile.replaceFirst("<nrOfCities>", tspInstance.getNumberOfCities().toString());
		exhaustiveOutputFile = exhaustiveOutputFile.replaceFirst("<iterations>", "1");
		exhaustiveOutputFile = exhaustiveOutputFile.replaceFirst("<iteration>", "1");
		ExhaustiveAlgorithm.compute(tspInstance, exhaustiveOutputFile);
	}
	

	private static void runLocalSearch(TSPInstance tspInstance, Integer iterations){
		String localSearchOutputFile = OUTPUT_FOLDER_LOCAL_SEARCH + OUTPUT_FILE_LOCAL_SEARCH;
		localSearchOutputFile = localSearchOutputFile.replaceFirst("<nrOfCities>", tspInstance.getNumberOfCities().toString());
		localSearchOutputFile = localSearchOutputFile.replaceFirst("<iterations>", iterations.toString());
		localSearchOutputFile = localSearchOutputFile.replaceFirst("<iteration>", "normal");
		LocalSearchAlgorithm.compute(tspInstance, iterations, localSearchOutputFile);
	}
	
	
	private static void runLocalSearchWithOptimumIterations(TSPInstance tspInstance, Integer iterations){
		for(int i = 0; i < 10; i++){
			String localSearchOutputFile = OUTPUT_FOLDER_LOCAL_SEARCH + OUTPUT_FILE_LOCAL_SEARCH;
			localSearchOutputFile = localSearchOutputFile.replaceFirst("<nrOfCities>", tspInstance.getNumberOfCities().toString());
			localSearchOutputFile = localSearchOutputFile.replaceFirst("<iterations>", iterations.toString());
			localSearchOutputFile = localSearchOutputFile.replaceFirst("<iteration>", String.valueOf(i));
			LocalSearchAlgorithm.compute(tspInstance, iterations, localSearchOutputFile);	
		}
	}
	
	
	private static void runGreedy(TSPInstance tspInstance){
		String greedyOutputFile = OUTPUT_FOLDER_GREEDY + OUTPUT_FILE_GREEDY;
		greedyOutputFile = greedyOutputFile.replaceFirst("<nrOfCities>", tspInstance.getNumberOfCities().toString());
		GreedyAlogorithm.compute(tspInstance, greedyOutputFile);
	}
	
	private static void runOptimizedGreedy(TSPInstance tspInstance){
		String greedyOutputFile = OUTPUT_FOLDER_GREEDY + OUTPUT_FILE_OPTIMIZED_GREEDY;
		greedyOutputFile = greedyOutputFile.replaceFirst("<nrOfCities>", tspInstance.getNumberOfCities().toString());
		GreedyAlogorithm.optimizedCompute(tspInstance, greedyOutputFile);
	}
	
	private static void runSimulatedAnnealing(TSPInstance tspInstance, Integer iterations){
		String simulatedOutputFile = OUTPUT_FOLDER_SIMULATED_ANNEALING + OUTPUT_FILE_SIMULATED_ANNEALING;
		simulatedOutputFile = simulatedOutputFile.replaceFirst("<nrOfCities>", tspInstance.getNumberOfCities().toString());
		simulatedOutputFile = simulatedOutputFile.replaceFirst("<iterations>", iterations.toString());
		simulatedOutputFile = simulatedOutputFile.replaceFirst("<iteration>", "normal");
		SimulatedAnnealingAlgorithm.compute(tspInstance, iterations, simulatedOutputFile);
	}
	
	private static void runSimulatedAnnealingWithOptimumIterations(TSPInstance tspInstance, Integer iterations){
		for(int i = 0; i < 10; i++){
			String simulatedOutputFile = OUTPUT_FOLDER_SIMULATED_ANNEALING + OUTPUT_FILE_SIMULATED_ANNEALING;
			simulatedOutputFile = simulatedOutputFile.replaceFirst("<nrOfCities>", tspInstance.getNumberOfCities().toString());
			simulatedOutputFile = simulatedOutputFile.replaceFirst("<iterations>", iterations.toString());
			simulatedOutputFile = simulatedOutputFile.replaceFirst("<iteration>", String.valueOf(i));
			SimulatedAnnealingAlgorithm.compute(tspInstance, iterations, simulatedOutputFile);
		}
	}

}
