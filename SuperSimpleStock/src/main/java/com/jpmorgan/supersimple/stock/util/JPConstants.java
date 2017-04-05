package com.jpmorgan.supersimple.stock.util;

public class JPConstants {
	
	public static final String DELIMITER = "/";
	public static final String USA_CURRENCY = " USD";
	
	public static final String TRADE_DATE_FORMAT = "dd MMM yyyy";
	
	public static final String FRIDAY_WEEKEND="FRIDAY";
	public static final String SATURDAY_WEEKEND="SATURDAY";
	public static final String SUNDAY_WEEKEND="SUNDAY";
	
	public static final String BUY = "B";
	public static final String SELL= "S";
	
	  //Set the currency whose weekend is Friday and Saturday assuming currently only 2 countries
	public static final String[] FRI_SAT_WEEKEND_COUNTRY_CCY = new String[]{"AED", "SAR"};
}
