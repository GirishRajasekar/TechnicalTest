package com.jpmorgan.supersimple.stock.bean;

import java.util.Map;

public class Report {

	private Map<String, Double> buySettlementDateMap;
	private Map<String, Double> sellSettlementDateMap;
	private Map<String, Double> buyEntityMap;
	private Map<String, Double> sellEntityMap
	;
	public Map<String, Double> getBuySettlementDateMap() {
		return buySettlementDateMap;
	}
	public void setBuySettlementDateMap(Map<String, Double> buySettlementDateMap) {
		this.buySettlementDateMap = buySettlementDateMap;
	}
	public Map<String, Double> getSellSettlementDateMap() {
		return sellSettlementDateMap;
	}
	public void setSellSettlementDateMap(Map<String, Double> sellSettlementDateMap) {
		this.sellSettlementDateMap = sellSettlementDateMap;
	}
	public Map<String, Double> getBuyEntityMap() {
		return buyEntityMap;
	}
	public void setBuyEntityMap(Map<String, Double> buyEntityMap) {
		this.buyEntityMap = buyEntityMap;
	}
	public Map<String, Double> getSellEntityMap() {
		return sellEntityMap;
	}
	public void setSellEntityMap(Map<String, Double> sellEntityMap) {
		this.sellEntityMap = sellEntityMap;
	}
	
	
	
	
}
