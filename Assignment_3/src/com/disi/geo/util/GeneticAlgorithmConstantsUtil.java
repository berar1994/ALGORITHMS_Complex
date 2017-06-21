package com.disi.geo.util;

public enum GeneticAlgorithmConstantsUtil {

	POPULATION_SIZE_12(12),
	POPULATION_SIZE_32(32),
	POPULATION_SIZE_102(102),
	POPULATION_SIZE_162(162),
	POPULATION_SIZE_192(192),
	REQUIRED_NR_OF_CHILDREN_40(40),
	REQUIRED_NR_OF_CHILDREN_30(30),
	REQUIRED_NR_OF_CHILDREN_20(20),
	REQUIRED_NR_OF_CHILDREN_10(10);
	
	private Integer size;

	GeneticAlgorithmConstantsUtil(Integer size) {
        this.size = size;
    }

    public Integer value() {
        return size;
    }
	
}
