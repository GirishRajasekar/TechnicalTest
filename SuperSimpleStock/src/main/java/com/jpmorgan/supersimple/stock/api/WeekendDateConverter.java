package com.jpmorgan.supersimple.stock.api;

import com.jpmorgan.supersimple.stock.bean.Trade;
import com.jpmorgan.supersimple.stock.exception.SuperStockExcpetion;

public interface WeekendDateConverter {

	public Trade incrementDaysExcludingWeekends(Trade trade) throws SuperStockExcpetion;
}
