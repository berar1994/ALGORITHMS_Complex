package com.disi.geo.utils;

import java.text.DecimalFormat;

public class DoubleUtil {
	
	public static String getStringWithTwoDigits(Double number){
		DecimalFormat df = new DecimalFormat("#.00");
		return df.format(number);
	}
	
}
