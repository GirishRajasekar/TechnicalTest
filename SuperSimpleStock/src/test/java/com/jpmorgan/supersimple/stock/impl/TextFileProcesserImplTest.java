package com.jpmorgan.supersimple.stock.impl;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jpmorgan.supersimple.stock.api.WeekendDateConverter;
import com.jpmorgan.supersimple.stock.bean.Trade;
import com.jpmorgan.supersimple.stock.exception.SuperStockExcpetion;
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
	
	@Test(expected = SuperStockExcpetion.class)
	public void testForInvalidFileName() throws SuperStockExcpetion{
		txtFileProcessor = new TextFileProcesserImpl("trade1.txt");
		txtFileProcessor.processInputFile();
		
	}
	
	@Test
	public void testForValidInputFile() throws SuperStockExcpetion{
		List<Trade> tradeList = txtFileProcessor.processInputFile();
		assertNotNull(tradeList);
		
	}
	
	@Test(expected=SuperStockExcpetion.class)
	public void testForSuperStockException() throws SuperStockExcpetion{
		String[] tradeArray = {"JP","","0.50","","04 Apr 2017","04 Apr 2017","100","200"};
		txtFileProcessor.populateTradeBean(tradeArray, weekendDateConverter);
	}
	
	@Test
	public void testForEmptyInput(){
		String[] tradeArray = {};
		try{
			txtFileProcessor.populateTradeBean(tradeArray, weekendDateConverter);
		}catch (SuperStockExcpetion e) {
			assertEquals("Invalid Trade Input Line",e.getMessage());
		}
	}
	
	@Test
	public void testForInvalidInputLengthGraterThan8(){
		String[] tradeArray = {"JP","B","0.50","GBP","04 Apr 2017","04 Apr 2017","100","200","45535","sscc"};
		try{
			txtFileProcessor.populateTradeBean(tradeArray, weekendDateConverter);
		}catch (SuperStockExcpetion e) {
			assertEquals("Invalid Trade Input Line",e.getMessage());
		}
	}
	
	@Test
	public void testForInvalidInputLengthGraterLessThan8(){
		String[] tradeArray = {"JP","B","0.50","GBP","04 Apr 2017","04 Apr 2017"};
		try{
			txtFileProcessor.populateTradeBean(tradeArray, weekendDateConverter);
		}catch (SuperStockExcpetion e) {
			assertEquals("Invalid Trade Input Line",e.getMessage());
		}
	}
	
	@Test
	public void testForCurrencyEmptyErrorMessage(){
		String[] tradeArray = {"JP","B","0.50","","04 Apr 2017","04 Apr 2017","100","200"};
		try{
			txtFileProcessor.populateTradeBean(tradeArray, weekendDateConverter);
		}catch (SuperStockExcpetion e) {
			assertEquals("Currency Field is Invalid",e.getMessage());
		}
	}
	
	@Test
	public void testForEmptyEntityField()  {
		String[] tradeArray = {"","B","0.50","GBP","04 Apr 2017","04 Apr 2017","100","200"};
		try {
			txtFileProcessor.populateTradeBean(tradeArray, weekendDateConverter);
		} catch (SuperStockExcpetion e) {
			assertEquals("Entity Field is invalid",e.getMessage());
		}
	}
	
	@Test
	public void testForEmptyInstrutionField()  {
		String[] tradeArray = {"JP","","0.50","GBP","04 Apr 2017","04 Apr 2017","100","200"};
		try {
			txtFileProcessor.populateTradeBean(tradeArray, weekendDateConverter);
		} catch (SuperStockExcpetion e) {
			assertEquals("Instruction Field is invalid",e.getMessage());
		}
	}
	
	@Test
	public void testForEmptyAgreedFxField()  {
		String[] tradeArray = {"JP","B","","GBP","04 Apr 2017","04 Apr 2017","100","200"};
		try {
			txtFileProcessor.populateTradeBean(tradeArray, weekendDateConverter);
		} catch (SuperStockExcpetion e) {
			assertEquals("AgreedFX Field is invalid",e.getMessage());
		}
	}
	
	@Test
	public void testForEmptyInstrutionDateField()  {
		String[] tradeArray = {"JP","B","0.50","GBP","","04 Apr 2017","100","200"};
		try {
			txtFileProcessor.populateTradeBean(tradeArray, weekendDateConverter);
		} catch (SuperStockExcpetion e) {
			assertEquals("InstructionDate Field is invalid",e.getMessage());
		}
	}
	
	@Test
	public void testForEmptySettlementDateField()  {
		String[] tradeArray = {"JP","B","0.50","GBP","04 Apr 2017","","100","200"};
		try {
			txtFileProcessor.populateTradeBean(tradeArray, weekendDateConverter);
		} catch (SuperStockExcpetion e) {
			assertEquals("SettlementDate Field is invalid",e.getMessage());
		}
	}
	
	@Test
	public void testForEmptyUnitsField()  {
		String[] tradeArray = {"JP","B","0.50","GBP","04 Apr 2017","04 Apr 2017","","200"};
		try {
			txtFileProcessor.populateTradeBean(tradeArray, weekendDateConverter);
		} catch (SuperStockExcpetion e) {
			assertEquals("Units Field is invalid",e.getMessage());
		}
	}
	
	@Test
	public void testForEmptyPricePerUnitField()  {
		String[] tradeArray = {"JP","B","0.50","GBP","04 Apr 2017","04 Apr 2017","100",""};
		try {
			txtFileProcessor.populateTradeBean(tradeArray, weekendDateConverter);
		} catch (SuperStockExcpetion e) {
			assertEquals("PricePerUnit Field is Invalid",e.getMessage());
		}
	}
	
	@Test
	public void testForPopulateTradeWithInvalidCurrency()  {
		String[] tradeArray = {"JP","B","0.50","GBPS","04 Apr 2017","04 Apr 2017","100","200"};
		try {
			txtFileProcessor.populateTradeBean(tradeArray, weekendDateConverter);
		} catch (SuperStockExcpetion e) {
			assertEquals("Currency Field is Invalid",e.getMessage());
		}
	}
	
	@Test
	public void testForPopulateTradeWithValidCurrency() throws SuperStockExcpetion {
		String[] tradeArray = {"JP","B","0.50","GBP","04 Apr 2017","04 Apr 2017","100","200"};
		Trade tarde =txtFileProcessor.populateTradeBean(tradeArray, weekendDateConverter);
		assertEquals("GBP", tarde.getCurrency());
	}
	
	
	@Test
	public void testForPopulateTradeTotalAmtInUSD() throws SuperStockExcpetion {
		String[] tradeArray = {"JP","B","0.50","AED","04 Apr 2017","04 Apr 2017","100","200"};
		Trade tarde =txtFileProcessor.populateTradeBean(tradeArray, weekendDateConverter);
		assertEquals(10000, tarde.getTotalTradeAmount(),.0002);
	}
	
	@Test
	public void testWhenAgreedFxIsZero() throws SuperStockExcpetion {
		String[] tradeArray = {"JP","B","0","AED","04 Apr 2017","04 Apr 2017","100","200"};
		Trade tarde =txtFileProcessor.populateTradeBean(tradeArray, weekendDateConverter);
		assertEquals(0, tarde.getTotalTradeAmount(),.0002);
	}
	
	
	@Test
	public void testWhenUnitIsZero() throws SuperStockExcpetion {
		String[] tradeArray = {"JP","B","0.50","AED","04 Apr 2017","04 Apr 2017","0","200"};
		Trade tarde =txtFileProcessor.populateTradeBean(tradeArray, weekendDateConverter);
		assertEquals(0, tarde.getTotalTradeAmount(),.0002);
	}
	
	@Test
	public void testWhenPriceIsZero() throws SuperStockExcpetion {
		String[] tradeArray = {"JP","B","0.50","AED","04 Apr 2017","04 Apr 2017","100","0"};
		Trade tarde =txtFileProcessor.populateTradeBean(tradeArray, weekendDateConverter);
		assertEquals(0, tarde.getTotalTradeAmount(),.0002);
	}
	
	@Test
	public void testWhenAgreedFxIsNegative() throws SuperStockExcpetion {
		String[] tradeArray = {"JP","B","-0.50","AED","04 Apr 2017","04 Apr 2017","100","200"};
		Trade tarde =txtFileProcessor.populateTradeBean(tradeArray, weekendDateConverter);
		assertEquals(0, tarde.getTotalTradeAmount(),.0002);
	}
	
	@Test
	public void testWhenUnitIsNegative() throws SuperStockExcpetion {
		String[] tradeArray = {"JP","B","0.50","AED","04 Apr 2017","04 Apr 2017","-100","200"};
		Trade tarde =txtFileProcessor.populateTradeBean(tradeArray, weekendDateConverter);
		assertEquals(0, tarde.getTotalTradeAmount(),.0002);
	}
	
	@Test
	public void testWhenPriceIsNegative() throws SuperStockExcpetion {
		String[] tradeArray = {"JP","B","0.50","AED","04 Apr 2017","04 Apr 2017","100","-200"};
		Trade tarde =txtFileProcessor.populateTradeBean(tradeArray, weekendDateConverter);
		assertEquals(0, tarde.getTotalTradeAmount(),.0002);
	}
	
	@Test
	public void testWhenAgreeFxAndUnitIsNegative() throws SuperStockExcpetion {
		String[] tradeArray = {"JP","B","-0.50","AED","04 Apr 2017","04 Apr 2017","-100","200"};
		Trade tarde =txtFileProcessor.populateTradeBean(tradeArray, weekendDateConverter);
		assertEquals(0, tarde.getTotalTradeAmount(),.0002);
	}
	
	@Test
	public void testWhenAgreeFxAndPriceIsNegative() throws SuperStockExcpetion {
		String[] tradeArray = {"JP","B","-0.50","AED","04 Apr 2017","04 Apr 2017","100","-200"};
		Trade tarde =txtFileProcessor.populateTradeBean(tradeArray, weekendDateConverter);
		assertEquals(0, tarde.getTotalTradeAmount(),.0002);
	}
	
	@Test
	public void testWhenUnitAndPriceIsNegative() throws SuperStockExcpetion {
		String[] tradeArray = {"JP","B","0.50","AED","04 Apr 2017","04 Apr 2017","-100","-200"};
		Trade tarde =txtFileProcessor.populateTradeBean(tradeArray, weekendDateConverter);
		assertEquals(0, tarde.getTotalTradeAmount(),.0002);
	}
	
	@Test
	public void testWhenAgreeFxUnitAndPriceIsNegative() throws SuperStockExcpetion {
		String[] tradeArray = {"JP","B","-1","AED","04 Apr 2017","04 Apr 2017","-100","-200"};
		Trade tarde =txtFileProcessor.populateTradeBean(tradeArray, weekendDateConverter);
		assertEquals(0, tarde.getTotalTradeAmount(),.0002);
	}
	
	@After
	public void tearDown() throws Exception {
		
		txtFileProcessor = null;
		weekendDateConverter = null;
	}

}
