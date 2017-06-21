package com.disi.geo.model;

import java.util.ArrayList;
import java.util.List;

public class TSP {
	private Integer numberOfCities;
	private List<City> cities;

	public TSP(){ cities = new ArrayList<>(); }

	public void addCity(City city) {
		cities.add(city);
	}

	public List<City> getCities() {
		return cities;
	}

	public Integer getNumberOfCities() {
		return numberOfCities;
	}

	public void setNumberOfCities(Integer numberOfCities) {
		this.numberOfCities = numberOfCities;
	}

	public String toString() {
		return "Number of cities " + numberOfCities + "\n" + getStringWithCities();
	}

	private String getStringWithCities() {
		String result = "";
		for (City city : cities) {
			result += city.toString() + "\n";
		}
		return result;
	}
}
