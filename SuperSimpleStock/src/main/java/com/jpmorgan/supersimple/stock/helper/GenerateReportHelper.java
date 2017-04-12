package com.jpmorgan.supersimple.stock.helper;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import com.jpmorgan.supersimple.stock.bean.Report;
import com.jpmorgan.supersimple.stock.bean.Trade;
import com.jpmorgan.supersimple.stock.util.DateComparator;
import com.jpmorgan.supersimple.stock.util.JPConstants;

/*
 * This class helps in generating the reports for the trade details we get 
 * everyday total buy and sell amount based on settlement date and also  
 * company ranking for buy and sell amount.
 * 
 * @author Girish Rajasekar
 */

public class GenerateReportHelper {

	private static Logger logger=Logger.getLogger("GenerateReportHelper");
	
	/*
	 * This method is used for generating user report 
	 * 
	 * @param tradeList
	 * @return Report
	 */
	
	public static Report generateReport(List<Trade> tradeList) {
		
		
		Report reportBean = new Report();
		
		//Using treemap sort settlement date which is the key.
		Map<String, Double> buySettlementDateMap = new TreeMap<>(new DateComparator());
		Map<String, Double> sellSettlementDateMap = new TreeMap<>(new DateComparator());
		
		//Using hashmap and then we can sort based on value in the map using comparator
		Map<String, Double> buyEntityMap = new HashMap<>();
		Map<String, Double> sellEntityMap = new HashMap<>();

		//Loop through tradeList
		for (Trade trade : tradeList) {

			String entity = trade.getEntity();
			String settlDate = trade.getSettlementDate();
			Double tradeAmount = trade.getTotalTradeAmount();
			Double entityBasedAmt = trade.getTotalTradeAmount();
			
			//if total trade amount is 0 or negative value then skipping that particular trade
			if(tradeAmount <= 0){
				continue;
			}
			
			//Check if its outgoing(Buy) or Incoming(sell) trade
			if (trade.getInstruction().equalsIgnoreCase(JPConstants.BUY)) {
				
				//Populate outgoing totalAmount for each settlement date
				buySettlementDateMap = populateMapDetails(settlDate,tradeAmount, buySettlementDateMap);
				
				//Populate outgoing totalAmount for each entity
				buyEntityMap = populateMapDetails(entity, entityBasedAmt, buyEntityMap);
				
			}else if (trade.getInstruction().equalsIgnoreCase(JPConstants.SELL)) {
				
				//Populate incoming totalAmount for each settlement date
				sellSettlementDateMap = populateMapDetails(settlDate,tradeAmount, sellSettlementDateMap);
				
				//Populate incoming totalAmount for each entity
				sellEntityMap = populateMapDetails(entity, entityBasedAmt, sellEntityMap);
			}else{
				logger.info("Instruction is not in proper order");
				logger.info(trade.getInstruction());
			}
		}
		
		//Setting the values to report bean
		reportBean.setBuySettlementDateMap(buySettlementDateMap);
		reportBean.setSellSettlementDateMap(sellSettlementDateMap);
		reportBean.setBuyEntityMap(buyEntityMap);
		reportBean.setSellEntityMap(sellEntityMap);
		
		//Returning the report bean object
		return reportBean;
	}
	
	

	/*
	 * This method helps to load the map based on the given
	 * Parameters
	 *  
	 * @param key
	 * @param tradeAmount
	 * @param keyValMap
	 * @return map
	 */
	private static Map<String, Double> populateMapDetails(String key,
			double tradeAmount, Map<String, Double> keyValMap) {
		if (keyValMap.size() > 0) {
			if (keyValMap.get(key) != null) {
				tradeAmount = tradeAmount + keyValMap.get(key);
			}
		}
		keyValMap.put(key, tradeAmount);
		return keyValMap;
	}
	
	/*
	 * This method helps to display the report to the user
	 *  
	 * @param buySettlementDateMap
	 * @param sellSettlementDateMap
	 * @param buyEntityMap
	 * @param sellEntityMap
	 * @return void
	 */
	public static void displayReport(Report reportBean) {
		
		
		Map<String, Double> buySettlementDateMap = reportBean.getBuySettlementDateMap();
		Map<String, Double> sellSettlementDateMap = reportBean.getSellSettlementDateMap();
		Map<String, Double> buyEntityMap = reportBean.getBuyEntityMap();
		Map<String, Double> sellEntityMap = reportBean.getSellEntityMap();
		
		
		if(buySettlementDateMap!=null && buySettlementDateMap.size() > 0){
			
			System.out.println("******Everyday Total Settlement Amount for Outgoing(Buy) Trades based on settlement date********");
			
			//Looping map to display the value
			buySettlementDateMap.forEach((k, v) -> System.out
					.println("Settlement Date: " + k + ", TotalAMount: " + v
							+ JPConstants.USA_CURRENCY));
		}
		if(sellSettlementDateMap!=null && sellSettlementDateMap.size() > 0){
			
			System.out.println("******Everyday Settlement Amount for Incoming(Sell) Trades based on settlement date********");
			
			//Looping map to display the value
			sellSettlementDateMap.forEach((k, v) -> System.out
					.println("Settlement Date: " + k + ", TotalAMount: " + v
							+ JPConstants.USA_CURRENCY));
		}
		if(buyEntityMap!=null && buyEntityMap.size() > 0){
			//calling sort method for outgoing entities based on the totaltradeAmount in descending
			buyEntityMap = GenerateReportHelper.sortByValue(buyEntityMap);
			
			System.out
			.println("******EveryDay Total Settlement Amount for Buy(Outgoing) Trades Based On Entity In Descending Order********");
			
			//Looping map to display the value
			buyEntityMap.forEach((k, v) -> System.out.println("Entity Name: " + k
			+ ", TotalAMount: " + v + JPConstants.USA_CURRENCY));
		}
		
		if(sellEntityMap!=null && sellEntityMap.size() > 0){
			//calling sort method for incoming entities based on the totaltradeAmount in descending
			sellEntityMap = GenerateReportHelper.sortByValue(sellEntityMap);
			
			System.out
					.println("******EveryDay Total Settlement Amount for Sell(Incoming) Trades Based On Entity In Descending Order********");
			
			//Looping map to display the value
			sellEntityMap.forEach((k, v) -> System.out.println("Entity Name: " + k
					+ ", TotalAMount: " + v + JPConstants.USA_CURRENCY));
		}
	}
	
	/*
	 * This method helps to sort the map based on the given value 
	 * descending order
	 *  
	 * @param map
	 * @return map
	 */
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(
			Map<K, V> map) {
		
		map = map
				.entrySet()
				.stream()
				.sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
				.collect(
						Collectors.toMap(Map.Entry::getKey,
								Map.Entry::getValue, (e1, e2) -> e1,
								LinkedHashMap::new));
		return map;
	}

}
