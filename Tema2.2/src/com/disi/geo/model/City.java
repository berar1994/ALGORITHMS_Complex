package com.disi.geo.model;

public class City {
	
	private Integer index;
	private Integer xCoordinate;
	private Integer yCoordinate;
	
	public City(Integer index, Integer xCoordinate, Integer yCoordinate){
		this.index = index;
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
	}
	
	public City(){}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public Integer getxCoordinate() {
		return xCoordinate;
	}

	public void setxCoordinate(Integer xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	public Integer getyCoordinate() {
		return yCoordinate;
	}

	public void setyCoordinate(Integer yCoordinate) {
		this.yCoordinate = yCoordinate;
	}
	
	public String toString(){
		return this.index + " " + this.xCoordinate + " " + this.yCoordinate;
	}
	
}
