package com.disi.geo.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.disi.geo.model.City;

public class ListUtil {

	public static boolean hasDuplicatesInLists(List<List<City>> listToCheck) {
		int size = listToCheck.size();
		int listSize = 0;

		for (int i = 0; i < size; i++) {
			listSize = listToCheck.get(i).size();
			Set<City> set = new HashSet<City>(listToCheck.get(i));
			if (set.size() < listSize) {
				return true;
			}
		}
		
		return false;
	}

	public static boolean hasDuplicates(List<City> listToCheck) {
		int listSize = listToCheck.size();
		Set<City> set = new HashSet<City>(listToCheck);
		if (set.size() < listSize) {
			return true;
		}

		return false;
	}

}
