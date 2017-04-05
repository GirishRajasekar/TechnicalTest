/**
 * 
 */
package com.jpmorgan.supersimple.stock.helper;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jpmorgan.supersimple.stock.bean.Report;
import com.jpmorgan.supersimple.stock.bean.Trade;
import com.jpmorgan.supersimple.stock.helper.GenerateReportHelper;

/**
 * @author Girish
 *
 */
public class GenerateReportHelperTest {
	
	Report reportBean;
	List<Trade> tradeList = null;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		tradeList = new ArrayList<>();
		
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
		

		tradeList.add(sellTrade);
		tradeList.add(sellTradeValid);
		tradeList.add(sellTradeValid1);
		tradeList.add(sellTradeValid2);
		
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
		
		tradeList.add(buyTrade);
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
	}

	@Test
	public void testWithInvalidInstructions() {
		
		reportBean = GenerateReportHelper.generateReport(tradeList);
		
		//Invalid Instruction for Buy
		assertNull(reportBean.getBuySettlementDateMap().get("04 Apr 2017"));
		
		//Invalid Instruction for Sell
		assertNull(reportBean.getSellSettlementDateMap().get("04 Apr 2017"));
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

}
