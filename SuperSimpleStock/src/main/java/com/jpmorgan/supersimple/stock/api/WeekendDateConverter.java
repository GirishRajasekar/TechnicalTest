package com.jpmorgan.supersimple.stock.api;

import java.time.format.DateTimeParseException;

import com.jpmorgan.supersimple.stock.bean.Trade;

public interface WeekendDateConverter {

	public Trade incrementDaysExcludingWeekends(Trade trade) throws DateTimeParseException;
}
