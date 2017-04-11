package com.disi.geo.utils;

import java.util.List;

import com.disi.geo.model.City;

public class TSPUtil {

	public static Double computeDistance(City city1, City city2) {
		Double resultX = Math.pow(city2.getxCoordinate() - city1.getxCoordinate(), 2);
		Double resultY = Math.pow(city2.getyCoordinate() - city1.getyCoordinate(), 2);
		return Math.sqrt(resultX + resultY);
	}
	
	public static Double computeTotalCost(List<City> cities){
		Double totalCost = 0.0;
		for(int i = 0; i < cities.size() - 1; i++){
			totalCost += computeDistance(cities.get(i), cities.get(i+1));
		}
		// add the cost for end city to start city
		return totalCost + computeDistance(cities.get(cities.size() - 1), cities.get(0));
	}
	
	public static void writeSolutionToFile(List<City> route, Double cost, Long time, String outputFile){
		String solution = "";
		for(City city : route)
			solution += city.getIndex() + " ";
		
		FileUtil.deleteFileIfExists(outputFile);
		FileUtil.writeToFile(outputFile, "***Solution***");
		FileUtil.writeToFile(outputFile, solution);
		FileUtil.writeToFile(outputFile, StringUtil.getStringWithTwoDigits(cost));
		FileUtil.writeToFile(outputFile, "Total time -- " + time);
	}

}
