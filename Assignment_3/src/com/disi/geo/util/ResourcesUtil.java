package com.disi.geo.util;

public enum ResourcesUtil {
	
	INPUT_FILE_EIL51("eil51.tsp"),
	INPUT_FILE_EIL76("eil76.tsp"),
	INPUT_FILE_EIL101("eil101.tsp");
	
	
	private String value;
	
	private ResourcesUtil(String value){
		this.value = value;
	}
	
	public String value(){
		return this.value;
	}
	
}
