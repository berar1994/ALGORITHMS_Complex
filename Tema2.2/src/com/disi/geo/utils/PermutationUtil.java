package com.disi.geo.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.disi.geo.model.City;

public class PermutationUtil {
	
	public static List<List<City>> generateAllPossiblePermutations(List<City> cities) {
		if (cities.size() == 0) {
			List<List<City>> result = new ArrayList<List<City>>();
			result.add(new ArrayList<City>());
			return result;
		}

		List<List<City>> permutations = new ArrayList<List<City>>();

		City firstElement = cities.remove(0);

		List<List<City>> recursiveReturn = generateAllPossiblePermutations(cities);
		for (List<City> li : recursiveReturn) {

			for (int index = 0; index <= li.size(); index++) {
				List<City> temp = new ArrayList<City>(li);
				temp.add(index, firstElement);
				permutations.add(temp);
			}

		}
		return permutations;
	}
	


	
}
