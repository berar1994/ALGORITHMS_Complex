package com.disi.geo.util;

import java.util.List;

import com.disi.geo.model.City;

public class TSPUtil {

	public static int computeDistance(City city1, City city2) {
		Double resultX = Math.pow(city2.getxCoordinate() - city1.getxCoordinate(), 2);
		Double resultY = Math.pow(city2.getyCoordinate() - city1.getyCoordinate(), 2);
		return (int) Math.round(Math.sqrt(resultX + resultY));
	}
	
	public static int computeTotalCost(List<City> cities){
		int totalCost = 0;
		for(int i = 0; i < cities.size() - 1; i++){
			totalCost += computeDistance(cities.get(i), cities.get(i+1));
		}
		// add the cost for end city to start city
		return totalCost + computeDistance(cities.get(cities.size() - 1), cities.get(0));
	}
	

}
