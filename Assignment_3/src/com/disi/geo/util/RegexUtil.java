package com.disi.geo.util;

public enum RegexUtil {
	
	LINE_SPLIT_POINTS_REGEX(":"),
	LINE_SPLIT_SPACE_REGEX(" ");
	
	
	private String value;
	
	private RegexUtil(String value){
		this.value = value;
	}
	
	public String value(){
		return this.value;
	}
	
}
