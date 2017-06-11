package com.jpmorgan.supersimple.stock.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.jpmorgan.supersimple.stock.api.FileProcesser;
import com.jpmorgan.supersimple.stock.api.WeekendDateConverter;
import com.jpmorgan.supersimple.stock.bean.Trade;
import com.jpmorgan.supersimple.stock.exception.SuperStockExcpetion;
import com.jpmorgan.supersimple.stock.util.JPConstants;
import com.jpmorgan.supersimple.stock.util.SuperStockUtil;

/**
 * @author Girish Rajasekar
 *
 */
public class TextFileProcesserImpl implements FileProcesser {

	private static Logger logger = Logger.getLogger("TextFileProcesserImpl");

	public String fileName = "";

	public TextFileProcesserImpl(String fileName) {
		this.fileName = fileName;
	}

	/*
	 * Populates the TradeBean by parsing the txt file.
	 * 
	 * @return List<Trade>
	 * 
	 * @throws SuperStockExcpetion
	 */
	@Override
	public List<Trade> processInputFile() throws SuperStockExcpetion {

		logger.debug("Inside TextFileProcesserImpl processInputFile() Method -- Start");

		List<Trade> tradeList = new ArrayList<>();

		try (FileReader fr = new FileReader(fileName);
				BufferedReader br = new BufferedReader(fr);) {

			WeekendDateConverter weekendDateConverter = new WeekendDateConverterImpl();

			while (br != null) {
				// Read each line from the text file
				String line = br.readLine();

				if (line != null) {
					// Split the line using delimiter /
					String[] tradeArray = line.split(JPConstants.DELIMITER);

					try {
						// Calling method to fill tradeBean Object
						Trade trade = populateTradeBean(tradeArray,
												weekendDateConverter);
						tradeList.add(trade);
						
					} catch (SuperStockExcpetion ex) {
						logger.error(line, ex);
					}

				} else {
					break;
				}
			}

		} catch (IOException ex) {
			throw new SuperStockExcpetion("Issue in Reading The Input File", ex);
		}
		logger.debug("Inside TextFileProcesserImpl processInputFile() Method -- End");

		// Returning the list
		return tradeList;
	}

	/*
	 * This method take the string array and loads the trade object along which
	 * checking for weekends logic and currency conversion if required is also
	 * done.
	 * 
	 * @param tardeArray line from the text file is passed after splitting it
	 * 
	 * @param weekendDateConverter check if the date falls on the weekend if yes
	 * then changes the date to first weekday
	 * 
	 * @param currencyConveter if incoming currency is not in USD convert to USD
	 * 
	 * @return trade
	 * 
	 * @throws SuperStockExcpetion
	 */

	public Trade populateTradeBean(String[] tradeArray,
			WeekendDateConverter weekendDateConverter)
			throws SuperStockExcpetion {

		Trade trade = new Trade();
		try {
		
			// Checking for valid currency length
			if (tradeArray != null && tradeArray.length == 8) {

				String currency = tradeArray[3];
				
				if(!SuperStockUtil.isNullOrEmpty(tradeArray[0])){
					trade.setEntity(tradeArray[0]);
				}else{
					throw new SuperStockExcpetion("Entity Field is invalid");
				}
				if(!SuperStockUtil.isNullOrEmpty(tradeArray[1])){
					trade.setInstruction(tradeArray[1]);
				}else{
					throw new SuperStockExcpetion("Instruction Field is invalid");
				}
				if(!SuperStockUtil.isNullOrEmpty(currency) && currency.length() == 3) {
					trade.setCurrency(currency);
				}else{
					throw new SuperStockExcpetion("Currency Field is Invalid");
				}
				if(!SuperStockUtil.isNullOrEmpty(tradeArray[4])){
					trade.setInstructionDate(tradeArray[4]);
				}else{
					throw new SuperStockExcpetion("InstructionDate Field is invalid");
				}
				if(!SuperStockUtil.isNullOrEmpty(tradeArray[5])){
					trade.setSettlementDate(tradeArray[5]);
				}else{
					throw new SuperStockExcpetion("SettlementDate Field is invalid");
				}
				if(!SuperStockUtil.isNullOrEmpty(tradeArray[2])){
					trade.setAgreedFx(Double.parseDouble(tradeArray[2]));
				}else{
					throw new SuperStockExcpetion("AgreedFX Field is invalid");
				}
				if(!SuperStockUtil.isNullOrEmpty(tradeArray[6])){
					trade.setUnits(Integer.parseInt(tradeArray[6]));
				}else{
					throw new SuperStockExcpetion("Units Field is invalid");
				}
				if(!SuperStockUtil.isNullOrEmpty(tradeArray[7])){
					trade.setPricePerUnit(Double.parseDouble(tradeArray[7]));
				}else{
					throw new SuperStockExcpetion("PricePerUnit Field is Invalid");
				}
			
				// Calling method to calculate the total trade amount
				trade.setTotalTradeAmount(calcuateTotalTradeAmt(trade));

				// Calling method to check for weekend and change accordingly
				trade = weekendDateConverter.incrementDaysExcludingWeekends(trade);
				
			}else{
				throw new SuperStockExcpetion("Invalid Trade Input Line");
			}
		}catch (NumberFormatException ex) {
			throw new SuperStockExcpetion("Error While Parsing String to Number",ex);
		}
		return trade;
	}

	/*
	 * This method calculates total trade amount in USD
	 * 
	 * @param trade
	 * 
	 * @return double
	 */
	private double calcuateTotalTradeAmt(Trade trade) {
		double totalTradeAmt = 0;
		if(trade.getPricePerUnit() > 0 && trade.getAgreedFx() > 0 && trade.getUnits() > 0){
			totalTradeAmt = trade.getPricePerUnit() * trade.getAgreedFx()
					* trade.getUnits();
		}
		return totalTradeAmt;
	}
	
	
}
