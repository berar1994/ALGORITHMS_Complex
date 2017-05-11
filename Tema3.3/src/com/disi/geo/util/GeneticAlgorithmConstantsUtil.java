package com.disi.geo.util;

public enum GeneticAlgorithmConstantsUtil {

	POPULATION_SIZE_12(12),
	POPULATION_SIZE_32(32),
	POPULATION_SIZE_102(102),
	REQUIRED_NR_OF_CHILDREN_20(20);
	
	private Integer size;

	GeneticAlgorithmConstantsUtil(Integer size) {
        this.size = size;
    }

    public Integer value() {
        return size;
    }
	
}
