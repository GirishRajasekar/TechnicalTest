package com.jpmorgan.supersimple.stock.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

import com.jpmorgan.supersimple.stock.api.WeekendDateConverter;
import com.jpmorgan.supersimple.stock.bean.Trade;
import com.jpmorgan.supersimple.stock.util.JPConstants;

/*
 * 
 * This class implements WeekendDateConverter interface which helps to check if the given date falls on the
 * weekend based on the currency and settlement date and if so then change the settlement date to the next
 * first weekday in the week
 * 
 * @author Girish Rajasekar
 */
public class WeekendDateConverterImpl implements WeekendDateConverter{

	/*
	 * 
	 * @see com.jpmorgan.api.WeekendDateConverter#incrementDaysExcludingWeekends(com.jpmorgan.bean.Trade)
	 * @param trade
	 * 
	 * @return trade
	 */
	@Override
	public Trade incrementDaysExcludingWeekends(Trade trade) throws DateTimeParseException {
		
		//Creating the formatter based on the settlement date format type in the input file
		DateTimeFormatter formatter=DateTimeFormatter.ofPattern(JPConstants.TRADE_DATE_FORMAT);
		
		//converting the settlement date to localDate object 
        LocalDate date= LocalDate.parse(trade.getSettlementDate(),formatter);
        
        //Calling the method to check if date falls on the weekend or not
        while(!isWorkingDay(date,trade.getCurrency())) {
        	
        	//Increment the date by 1 if it's falls on the weekend and check again
        	date =  date.plusDays(1);
        }
        
        //Converting and setting the settlement date object after the weekend check is done
        trade.setSettlementDate(date.format(formatter));
        
        //Returning trade object
   		return trade;
	}
	
	/*
	 * 
	 * @param settlementDate
	 * @param currency
	 * 
	 * @return boolean
	 */
	private boolean isWorkingDay(LocalDate settlementDate,String currency) {
		
        //Converting arrays to List
    	List<String> friSatWeekEndList = Arrays.asList(JPConstants.FRI_SAT_WEEKEND_COUNTRY_CCY);
    	
    	//Fetch the day of the week from the settlement date object
        String dayOFWeek = settlementDate.getDayOfWeek().name();
        
        //Check if it falls on the Friday and  Saturday weekend country list
        if(friSatWeekEndList.contains(currency)){
        	if ((dayOFWeek.equalsIgnoreCase(JPConstants.FRIDAY_WEEKEND)) || (dayOFWeek.equalsIgnoreCase(JPConstants.SATURDAY_WEEKEND))) {
	            return false;
	        }
        }else{
	        if ((dayOFWeek.equalsIgnoreCase(JPConstants.SATURDAY_WEEKEND)) || (dayOFWeek.equalsIgnoreCase(JPConstants.SUNDAY_WEEKEND))) {
	            return false;
	        }
        }
        return true;
     }

}
