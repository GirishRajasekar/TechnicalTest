package com.jpmorgan.supersimple.stock.impl;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jpmorgan.supersimple.stock.bean.Trade;
import com.jpmorgan.supersimple.stock.exception.SuperStockExcpetion;
import com.jpmorgan.supersimple.stock.impl.WeekendDateConverterImpl;

public class WeekendDateConverterImplTest {
	
	WeekendDateConverterImpl weekendDateConverter;
	Trade trade;
	
	@Before
	public void setUp() throws Exception {
		weekendDateConverter = new WeekendDateConverterImpl();
		trade = new Trade();
		
		
	}
	
	@Test
	public void testForValidDateWithOutWeekend() throws SuperStockExcpetion{
		trade.setSettlementDate("04 Apr 2017");
		trade.setCurrency("AED");
		Trade tradeAfterChange = weekendDateConverter.incrementDaysExcludingWeekends(trade);
		assertEquals(trade.getSettlementDate(), tradeAfterChange.getSettlementDate());
	}
	
	@Test
	public void testForFridayWeekendCcy() throws SuperStockExcpetion{
		trade.setSettlementDate("14 Apr 2017");
		trade.setCurrency("AED");
		Trade tradeAfterChange = weekendDateConverter.incrementDaysExcludingWeekends(trade);
		assertEquals("16 Apr 2017", tradeAfterChange.getSettlementDate());
	}
	
	@Test
	public void testForSundayWeekendCcy() throws SuperStockExcpetion{
		trade.setSettlementDate("16 Apr 2017");
		trade.setCurrency("GBP");
		Trade tradeAfterChange = weekendDateConverter.incrementDaysExcludingWeekends(trade);
		assertEquals("17 Apr 2017", tradeAfterChange.getSettlementDate());
	}
	
	@Test
	public void testForSundayWeekendEndOfMonthDateCcy() throws SuperStockExcpetion{
		trade.setSettlementDate("30 Apr 2017");
		trade.setCurrency("GBP");
		Trade tradeAfterChange = weekendDateConverter.incrementDaysExcludingWeekends(trade);
		assertEquals("01 May 2017", tradeAfterChange.getSettlementDate());
	}
	
	@Test
	public void testForSundayWeekendEndOfYearDateCcy() throws SuperStockExcpetion{
		trade.setSettlementDate("31 Dec 2016");
		trade.setCurrency("USD");
		Trade tradeAfterChange = weekendDateConverter.incrementDaysExcludingWeekends(trade);
		assertEquals("02 Jan 2017", tradeAfterChange.getSettlementDate());
	}
	
	//Negative test cases
	
	@Test
	public void testForDateFormatExceptin() {
		try{
			trade.setSettlementDate("16 Apr 17");
			trade.setCurrency("GBP");
			weekendDateConverter.incrementDaysExcludingWeekends(trade);
		}catch (SuperStockExcpetion e) {
			assertEquals("Error While Parsing Settlement Date",e.getMessage());
		}
		
	}
	
	@Test
	public void testForInvalidDateExceptin() {
		try{
			trade.setSettlementDate("2017-04-07");
			trade.setCurrency("GBP");
			weekendDateConverter.incrementDaysExcludingWeekends(trade);
		}catch (SuperStockExcpetion e) {
			assertEquals("Error While Parsing Settlement Date",e.getMessage());
		}
		
	}
	
	@Test
	public void testForDateWithoutYear() {
		try{
			trade.setSettlementDate("04 Apr");
			trade.setCurrency("GBP");
			weekendDateConverter.incrementDaysExcludingWeekends(trade);
		}catch (SuperStockExcpetion e) {
			assertEquals("Error While Parsing Settlement Date",e.getMessage());
		}
		
	}
	
	@Test
	public void testForEmptyDate() {
		try{
			trade.setSettlementDate("");
			trade.setCurrency("GBP");
			weekendDateConverter.incrementDaysExcludingWeekends(trade);
		}catch (SuperStockExcpetion e) {
			assertEquals("Error While Parsing Settlement Date",e.getMessage());
		}
		
	}
	
	@After
	public void tearDown() throws Exception {
		trade = null;
		weekendDateConverter = null;
	}

}
