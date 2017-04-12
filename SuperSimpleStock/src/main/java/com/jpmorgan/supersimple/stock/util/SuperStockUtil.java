package com.jpmorgan.supersimple.stock.util;

public class SuperStockUtil {

	public static boolean isNullOrEmpty(String str){
		
		if(str != null && str.trim().length()!=0 && !str.equalsIgnoreCase("null")){
			return false;
		}
		return true;
	}
}
