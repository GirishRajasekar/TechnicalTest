/**
 * 
 */
package com.jpmorgan.supersimple.stock.helper;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

import com.jpmorgan.supersimple.stock.bean.Report;
import com.jpmorgan.supersimple.stock.bean.Trade;
import com.jpmorgan.supersimple.stock.helper.GenerateReportHelper;
import com.jpmorgan.supersimple.stock.util.DateComparator;

/**
 * @author Girish
 *
 */
public class GenerateReportHelperTest {
	
	Report reportBean;
	List<Trade> tradeList = null;
	List<Trade> invalidTradeList = null;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		tradeList = new ArrayList<>();
		invalidTradeList = new ArrayList<>();
		
		//Invalid Instruction for sell
		Trade sellTrade = new Trade();
		sellTrade.setEntity("JP");
		sellTrade.setInstruction("SE");
		sellTrade.setCurrency("GBP");
		sellTrade.setAgreedFx(0.50);
		sellTrade.setInstructionDate("04 Apr 2017");
		sellTrade.setSettlementDate("04 Apr 2017");
		sellTrade.setUnits(100);
		sellTrade.setPricePerUnit(250);
		
		invalidTradeList.add(sellTrade);
		
		//valid input for sell
		Trade sellTradeValid = new Trade();
		sellTradeValid.setEntity("MS");
		sellTradeValid.setInstruction("S");
		sellTradeValid.setCurrency("AED");
		sellTradeValid.setAgreedFx(0.50);
		sellTradeValid.setInstructionDate("07 Apr 2017");
		sellTradeValid.setSettlementDate("07 Apr 2017");
		sellTradeValid.setUnits(100);
		sellTradeValid.setPricePerUnit(185.25);
		sellTradeValid.setTotalTradeAmount(9262.5);
		
		Trade sellTradeValid1 = new Trade();
		sellTradeValid1.setEntity("MS");
		sellTradeValid1.setInstruction("S");
		sellTradeValid1.setCurrency("INR");
		sellTradeValid1.setAgreedFx(0.25);
		sellTradeValid1.setInstructionDate("09 Apr 2017");
		sellTradeValid1.setSettlementDate("10 Apr 2017");
		sellTradeValid1.setUnits(100);
		sellTradeValid1.setPricePerUnit(350);
		sellTradeValid1.setTotalTradeAmount(8750);
		
		Trade sellTradeValid2 = new Trade();
		sellTradeValid2.setEntity("Infosys");
		sellTradeValid2.setInstruction("S");
		sellTradeValid2.setCurrency("INR");
		sellTradeValid2.setAgreedFx(0.25);
		sellTradeValid2.setInstructionDate("10 Apr 2017");
		sellTradeValid2.setSettlementDate("10 Apr 2017");
		sellTradeValid2.setUnits(100);
		sellTradeValid2.setPricePerUnit(2500);
		sellTradeValid2.setTotalTradeAmount(62500);
		
		Trade sellTradeValid3 = new Trade();
		sellTradeValid3.setEntity("WS");
		sellTradeValid3.setInstruction("S");
		sellTradeValid3.setCurrency("USD");
		sellTradeValid3.setAgreedFx(1);
		sellTradeValid3.setInstructionDate("25 Dec 2016");
		sellTradeValid3.setSettlementDate("25 Dec 2016");
		sellTradeValid3.setUnits(10);
		sellTradeValid3.setPricePerUnit(400);
		sellTradeValid3.setTotalTradeAmount(4000);
		
		tradeList.add(sellTradeValid2);
		tradeList.add(sellTradeValid);
		tradeList.add(sellTradeValid1);
		tradeList.add(sellTradeValid3);
		
		
		//Invalid Instruction for Buy
		Trade buyTrade = new Trade();
		buyTrade.setEntity("MP");
		buyTrade.setInstruction("BUYS");
		buyTrade.setCurrency("GBP");
		buyTrade.setAgreedFx(0.50);
		buyTrade.setInstructionDate("04 Apr 2017");
		buyTrade.setSettlementDate("04 Apr 2017");
		buyTrade.setUnits(100);
		buyTrade.setPricePerUnit(250);
		
		invalidTradeList.add(buyTrade);
		
		//valid input for sell
		Trade buyTradeValid = new Trade();
		buyTradeValid.setEntity("MS");
		buyTradeValid.setInstruction("B");
		buyTradeValid.setCurrency("AED");
		buyTradeValid.setAgreedFx(0.50);
		buyTradeValid.setInstructionDate("06 Apr 2017");
		buyTradeValid.setSettlementDate("06 Apr 2017");
		buyTradeValid.setUnits(100);
		buyTradeValid.setPricePerUnit(200.50);
		buyTradeValid.setTotalTradeAmount(10025);	
		
		
		Trade buyTradeValid1 = new Trade();
		buyTradeValid1.setEntity("EM");
		buyTradeValid1.setInstruction("B");
		buyTradeValid1.setCurrency("USD");
		buyTradeValid1.setAgreedFx(1);
		buyTradeValid1.setInstructionDate("06 Apr 2017");
		buyTradeValid1.setSettlementDate("07 Apr 2017");
		buyTradeValid1.setUnits(100);
		buyTradeValid1.setPricePerUnit(800.50);
		buyTradeValid1.setTotalTradeAmount(80050);
		
		Trade buyTradeValid2 = new Trade();
		buyTradeValid2.setEntity("MTR");
		buyTradeValid2.setInstruction("B");
		buyTradeValid2.setCurrency("USD");
		buyTradeValid2.setAgreedFx(1);
		buyTradeValid2.setInstructionDate("26 Jun 2017");
		buyTradeValid2.setSettlementDate("26 Jun 2017");
		buyTradeValid2.setUnits(50);
		buyTradeValid2.setPricePerUnit(100);
		buyTradeValid2.setTotalTradeAmount(5000);
		
		tradeList.add(buyTradeValid2);
		tradeList.add(buyTradeValid);
		tradeList.add(buyTradeValid1);	
		
		
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		reportBean = null;
		tradeList = null;
		invalidTradeList = null;
	}

	@Test
	public void testWithInvalidInstructions() {
		
		reportBean = GenerateReportHelper.generateReport(invalidTradeList);
		
		//Invalid Instruction for Buy
		assertNull(reportBean.getBuySettlementDateMap().get("04 Apr 2017"));
		
		//Invalid Date for Buy
		assertNull(reportBean.getBuySettlementDateMap().get("25 Apr 2017"));
		
		//Invalid Date for Sell
		assertNull(reportBean.getSellSettlementDateMap().get("25 Apr 2017"));
		
		//Invalid Instruction for Sell
		assertNull(reportBean.getSellSettlementDateMap().get("04 Apr 2017"));
		
		//Invalid Entity for Buy Entity
		assertNull(reportBean.getBuyEntityMap().get("Fidelity"));
				
		//Invalid Entity for Sell Entity
		assertNull(reportBean.getSellEntityMap().get("Emirates"));	
		
		//Checking for null key
		assertNull(reportBean.getSellEntityMap().get(null));	
	}
	
	@Test
	public void testForValidInput() {
		
		reportBean = GenerateReportHelper.generateReport(tradeList);
		//Testing for  Buy(Outing) total amount based on settlement date
		assertEquals(10025, reportBean.getBuySettlementDateMap().get("06 Apr 2017"),.0005);
		
		//Testing for  Sell(Incoming) total amount based on settlement date
		assertEquals(71250, reportBean.getSellSettlementDateMap().get("10 Apr 2017"),.0005);
		
		//Testing for  Buy(Incoming) total amount based on Entity
		assertEquals(80050, reportBean.getBuyEntityMap().get("EM"),.0005);
		
		//Testing for  Sell(Outgoing) total amount based on Entity
		assertEquals(18012.5, reportBean.getSellEntityMap().get("MS"),.0005);
		
	}
	
	@Test
	public void testForSortedOrder(){
		
		reportBean = GenerateReportHelper.generateReport(tradeList);
		
		
		//Should be sorted based on the key which is date
		Map<String, Double> expectBuySettlementDateMap = new TreeMap<>();
		
		expectBuySettlementDateMap.put("06 Apr 2017", 10025.0);
		expectBuySettlementDateMap.put("07 Apr 2017", 80050.0);
		expectBuySettlementDateMap.put("26 Jun 2017", 5000.0);
		
		assertThat(reportBean.getBuySettlementDateMap(),is(expectBuySettlementDateMap));
		
		Map<String, Double> expectSellSettlementDateMap = new TreeMap<>(new DateComparator());
		expectSellSettlementDateMap.put("25 Dec 2016", 4000.0);
		expectSellSettlementDateMap.put("07 Apr 2017", 9262.5);
		expectSellSettlementDateMap.put("10 Apr 2017", 71250.0);
		
		assertThat(reportBean.getSellSettlementDateMap(),is(expectSellSettlementDateMap));
		
		//Sorting based on the value in the descending order
		Map<String, Double>  expectedBuyEntityMap = new HashMap<>();
		expectedBuyEntityMap.put("EM", 80050.0);
		expectedBuyEntityMap.put("MS", 10025.0);
		expectedBuyEntityMap.put("MTR", 5000.0);
		
		assertThat(GenerateReportHelper.sortByValue(reportBean.getBuyEntityMap()),is(expectedBuyEntityMap));
		
		Map<String, Double>  expectedSellEntityMap = new HashMap<>();
		
		expectedSellEntityMap.put("Infosys", 62500.0);
		expectedSellEntityMap.put("MS", 18012.5);
		expectedSellEntityMap.put("WS", 4000.0);
		
		assertThat(GenerateReportHelper.sortByValue(reportBean.getSellEntityMap()),is(expectedSellEntityMap));
		
	}

}
