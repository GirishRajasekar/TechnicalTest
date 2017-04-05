package com.jpmorgan.supersimple.stock.api;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import com.jpmorgan.supersimple.stock.bean.Trade;

public interface FileProcesser {

	public List<Trade> processInputFile() throws IOException,ParseException;
}
