package com.jpmorgan.supersimple.stock.api;

import java.util.List;

import com.jpmorgan.supersimple.stock.bean.Trade;
import com.jpmorgan.supersimple.stock.exception.SuperStockExcpetion;

public interface FileProcesser {

	public List<Trade> processInputFile() throws SuperStockExcpetion;
}
