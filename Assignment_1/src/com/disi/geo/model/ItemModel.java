package com.disi.geo.model;

import java.util.Comparator;

public class ItemModel {

	private int index;
	private int value;
	private int weight;
	private float valueToWeightRatio;

	public ItemModel(int index, int value, int weight) {
		this.value = value;
		this.weight = weight;
		this.index = index;
	}

	// default constructor
	public ItemModel() {
	}

	// getters & setters
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public float getValueToWeightRatio() {
		return valueToWeightRatio;
	}

	public void setValueToWeightRatio(float valueToWeightRatio) {
		this.valueToWeightRatio = valueToWeightRatio;
	}

	@SuppressWarnings("unchecked")
	public static Comparator<ItemModel> compareByRatio() {
		@SuppressWarnings("rawtypes")
		Comparator comp = new Comparator<ItemModel>() {
			@Override
			public int compare(ItemModel o1, ItemModel o2) {
				return Float.compare(o1.getValueToWeightRatio(), o2.getValueToWeightRatio());
			}
		};
		return comp;
	}
	
	
	@SuppressWarnings("unchecked")
	public static Comparator<ItemModel> compareByWeight() {
		@SuppressWarnings("rawtypes")
		Comparator comp = new Comparator<ItemModel>() {
			@Override
			public int compare(ItemModel o1, ItemModel o2) {
				return Integer.compare(o1.getWeight(), o2.getWeight());
			}
		};
		return comp;
	}
	
	
	@SuppressWarnings("unchecked")
	public static Comparator<ItemModel> compareByValue() {
		@SuppressWarnings("rawtypes")
		Comparator comp = new Comparator<ItemModel>() {
			@Override
			public int compare(ItemModel o1, ItemModel o2) {
				return Integer.compare(o1.getValue(), o2.getValue());
			}
		};
		return comp;
	}

}
