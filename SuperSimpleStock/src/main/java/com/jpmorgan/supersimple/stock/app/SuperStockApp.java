package com.jpmorgan.supersimple.stock.app;

import java.util.List;

import org.apache.log4j.Logger;

import com.jpmorgan.supersimple.stock.api.FileProcesser;
import com.jpmorgan.supersimple.stock.bean.Report;
import com.jpmorgan.supersimple.stock.bean.Trade;
import com.jpmorgan.supersimple.stock.exception.SuperStockExcpetion;
import com.jpmorgan.supersimple.stock.helper.GenerateReportHelper;
import com.jpmorgan.supersimple.stock.impl.TextFileProcesserImpl;

/*
 * Application to show daily report based on the settlement date and entity for
 * both buy and sell trades.
 * 
 * @author Girish Rajasekar
 */
public class SuperStockApp {

	private static Logger logger = Logger.getLogger("SuperStockApp");

	public static void main(String[] args) {
		
		logger.debug("SuperStockApp Application Main Method -- Start");

		// Creating fileProcessHelper Object
		FileProcesser fileProcesser = new TextFileProcesserImpl("trade.txt");

		try {
			// Calling method which helps in reading the text file
			// and populating the trade bean object
			List<Trade> tradeList = fileProcesser.processInputFile();

			if (tradeList != null && tradeList.size() > 0) {

				// Calling method to sort and populate to map
				Report reportBean = GenerateReportHelper
						.generateReport(tradeList);

				// Calling display method to to show the final report to user
				GenerateReportHelper.displayReport(reportBean);

			} else{
				logger.debug("Issue While Processing the txt file");
			}

		} catch (SuperStockExcpetion e) {
			logger.error("Expception while reading the Input file", e);
		}
		logger.debug("SuperStockApp Application Main Method -- End");
	}
}
