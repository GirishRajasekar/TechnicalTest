package com.jpmorgan.supersimple.stock.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.jpmorgan.supersimple.stock.api.FileProcesser;
import com.jpmorgan.supersimple.stock.api.WeekendDateConverter;
import com.jpmorgan.supersimple.stock.bean.Trade;
import com.jpmorgan.supersimple.stock.util.JPConstants;

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
	 * @throws IOException,ParseException
	 */
	@Override
	public List<Trade> processInputFile() throws IOException, ParseException {
		
		logger.debug("Inside TextFileProcesserImpl processInputFile() Method -- Start");

		List<Trade> tradeList = new ArrayList<>();

		FileReader fi = new FileReader(fileName);
		BufferedReader br = new BufferedReader(fi);
		WeekendDateConverter weekendDateConverter = new WeekendDateConverterImpl();

		while (br != null) {
			// Read each line from the text file
			String line = br.readLine();

			if (line != null) {
				// Split the line using delimiter /
				String[] tradeArray = line.split(JPConstants.DELIMITER);

				// Check if all the input elements are there in each line
				if (tradeArray != null && tradeArray.length == 8) {

					// Calling method to fill tradeBean Object
					Trade trade = populateTradeBean(tradeArray,weekendDateConverter);

					//Before adding to the list checking if trade object is there
					if (trade != null) {
						tradeList.add(trade);
					}else{
						logger.debug("Invalid Input Format");
						logger.debug(line);
					}
				}else {
					logger.debug("Invalid Data");
				}
			} else {
				break;
			}
		}
		// Closing the buffer reader object.
		br.close();
		logger.debug("Inside TextFileProcesserImpl processInputFile() Method -- End");
		
		//Returning the list
		return tradeList;
	}

	/*
	 * This method take the string array and loads the trade object along which
	 * checking for weekends logic and currency conversion if required is also
	 * done.
	 * 
	 * @param tardeArray line from the text file is passed after splitting it
	 * @param weekendDateConverter check if the date falls on the weekend if yes
	 * 							   then changes the date to first weekday
	 * @param currencyConveter if incoming currency is not in USD convert to USD
	 * 
	 * @return trade
	 */

	public Trade populateTradeBean(String[] tradeArray,
			WeekendDateConverter weekendDateConverter) {
		
		Trade trade = new Trade();
		try {

			trade.setEntity(tradeArray[0]);
			trade.setInstruction(tradeArray[1]);
			trade.setAgreedFx(Double.parseDouble(tradeArray[2]));
			trade.setCurrency(tradeArray[3]);
			trade.setInstructionDate(tradeArray[4]);
			trade.setSettlementDate(tradeArray[5]);
			trade.setUnits(Integer.parseInt(tradeArray[6]));
			trade.setPricePerUnit(Double.parseDouble(tradeArray[7]));

			// Calling method to calculate the total trade amount
			trade.setTotalTradeAmount(calcuateTotalTradeAmt(trade));
			
			//Checking for valid currency length
			if (!trade.getCurrency().isEmpty()
					&& trade.getCurrency().length() == 3) {
				
				// Calling method to check for weekend and change accordingly
				weekendDateConverter.incrementDaysExcludingWeekends(trade);
			} else {
				logger.debug("Invalid Data For Currency Field");
				return null;
			}
		} catch (NumberFormatException | DateTimeParseException ex) {
			trade = null;
			logger.error("Error while parsing the input data", ex);
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
		totalTradeAmt = trade.getPricePerUnit() * trade.getAgreedFx()
				* trade.getUnits();
		return totalTradeAmt;
	}

}
