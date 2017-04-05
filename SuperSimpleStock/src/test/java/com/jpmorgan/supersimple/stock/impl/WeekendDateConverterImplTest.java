package com.jpmorgan.supersimple.stock.impl;

import static org.junit.Assert.*;

import java.time.format.DateTimeParseException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jpmorgan.supersimple.stock.bean.Trade;
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
	public void testForValidDateWithOutWeekend() {
		trade.setSettlementDate("04 Apr 2017");
		trade.setCurrency("AED");
		Trade tradeAfterChange = weekendDateConverter.incrementDaysExcludingWeekends(trade);
		assertEquals(trade.getSettlementDate(), tradeAfterChange.getSettlementDate());
	}
	
	@Test
	public void testForFridayWeekendCcy() {
		trade.setSettlementDate("14 Apr 2017");
		trade.setCurrency("AED");
		Trade tradeAfterChange = weekendDateConverter.incrementDaysExcludingWeekends(trade);
		assertEquals("16 Apr 2017", tradeAfterChange.getSettlementDate());
	}
	
	@Test
	public void testForSundayWeekendCcy() {
		trade.setSettlementDate("16 Apr 2017");
		trade.setCurrency("GBP");
		Trade tradeAfterChange = weekendDateConverter.incrementDaysExcludingWeekends(trade);
		assertEquals("17 Apr 2017", tradeAfterChange.getSettlementDate());
	}
	
	@Test
	public void testForSundayWeekendEndOfMonthDateCcy() {
		trade.setSettlementDate("30 Apr 2017");
		trade.setCurrency("GBP");
		Trade tradeAfterChange = weekendDateConverter.incrementDaysExcludingWeekends(trade);
		assertEquals("01 May 2017", tradeAfterChange.getSettlementDate());
	}
	
	@Test
	public void testForSundayWeekendEndOfYearDateCcy() {
		trade.setSettlementDate("31 Dec 2016");
		trade.setCurrency("USD");
		Trade tradeAfterChange = weekendDateConverter.incrementDaysExcludingWeekends(trade);
		assertEquals("02 Jan 2017", tradeAfterChange.getSettlementDate());
	}
	
	
	@Test(expected=DateTimeParseException.class)
	public void testForDateFormatExceptin() {
		trade.setSettlementDate("16 Apr 17");
		trade.setCurrency("GBP");
		weekendDateConverter.incrementDaysExcludingWeekends(trade);
		
	}
	
	@After
	public void tearDown() throws Exception {
		trade = null;
		weekendDateConverter = null;
	}

}
