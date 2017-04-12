package com.jpmorgan.supersimple.stock.exception;

/*
 * This is custom excpetion class
 * 
 * @author Girish rajasekar
 */
public class SuperStockExcpetion extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SuperStockExcpetion(){
		
	}
	
	public SuperStockExcpetion(String message){
		super(message);
	}
	
	public SuperStockExcpetion(Throwable cause){
		super(cause);
	}
	
	public SuperStockExcpetion(String message,Throwable cause){
		super(message,cause);
	}
}
