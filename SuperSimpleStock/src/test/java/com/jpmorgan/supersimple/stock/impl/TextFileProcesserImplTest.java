package com.jpmorgan.supersimple.stock.impl;

import static org.junit.Assert.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jpmorgan.supersimple.stock.api.WeekendDateConverter;
import com.jpmorgan.supersimple.stock.bean.Trade;
import com.jpmorgan.supersimple.stock.impl.TextFileProcesserImpl;
import com.jpmorgan.supersimple.stock.impl.WeekendDateConverterImpl;

public class TextFileProcesserImplTest {

	TextFileProcesserImpl txtFileProcessor;
	WeekendDateConverter weekendDateConverter ;
	
	
	@Before
	public void setUp() throws Exception {
		
		txtFileProcessor = new TextFileProcesserImpl("trade.txt");
		weekendDateConverter = new WeekendDateConverterImpl();
	}

	@Test(expected = NullPointerException.class)
	public void testForNullPointerException() throws Exception{
		txtFileProcessor = new TextFileProcesserImpl(null);
		txtFileProcessor.processInputFile();
		
	}
	
	@Test(expected = IOException.class)
	public void testForIOException() throws Exception{
		txtFileProcessor = new TextFileProcesserImpl("trade1.txt");
		txtFileProcessor.processInputFile();
		
	}
	
	@Test
	public void testForValidInputFile() throws IOException, ParseException {
		List<Trade> tradeList = txtFileProcessor.processInputFile();
		assertNotNull(tradeList);
		
	}
	
	@Test
	public void testForPopulateTradeWithCurrencyEmpty()  {
		String[] tradeArray = {"JP","B","0.50","","04 Apr 2017","04 Apr 2017","100","200"};
		Trade tarde =txtFileProcessor.populateTradeBean(tradeArray, weekendDateConverter);
		assertNull(tarde);
	}
	

	@Test
	public void testForPopulateTradeWithInvalidCurrency()  {
		String[] tradeArray = {"JP","B","0.50","GBPS","04 Apr 2017","04 Apr 2017","100","200"};
		Trade tarde =txtFileProcessor.populateTradeBean(tradeArray, weekendDateConverter);
		assertNull(tarde);
	}
	
	@Test
	public void testForPopulateTradeWithValidCurrency()  {
		String[] tradeArray = {"JP","B","0.50","GBP","04 Apr 2017","04 Apr 2017","100","200"};
		Trade tarde =txtFileProcessor.populateTradeBean(tradeArray, weekendDateConverter);
		assertEquals("GBP", tarde.getCurrency());
	}
	
	
	@Test
	public void testForPopulateTradeTotalAmtInUSD()  {
		String[] tradeArray = {"JP","B","0.50","AED","04 Apr 2017","04 Apr 2017","100","200"};
		Trade tarde =txtFileProcessor.populateTradeBean(tradeArray, weekendDateConverter);
		assertEquals(10000, tarde.getTotalTradeAmount(),.0002);
	}
	
	@Test
	public void testForPopulateTradeTotalAmtInUSDWhenUnitIsZero()  {
		String[] tradeArray = {"JP","B","0.50","AED","04 Apr 2017","04 Apr 2017","0","200"};
		Trade tarde =txtFileProcessor.populateTradeBean(tradeArray, weekendDateConverter);
		assertEquals(0, tarde.getTotalTradeAmount(),.0002);
	}
	
	
	@After
	public void tearDown() throws Exception {
		
		txtFileProcessor = null;
		weekendDateConverter = null;
	}

}
