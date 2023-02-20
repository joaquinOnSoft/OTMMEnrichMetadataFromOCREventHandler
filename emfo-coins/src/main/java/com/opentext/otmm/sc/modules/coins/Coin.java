package com.opentext.otmm.sc.modules.coins;

public class Coin {
	private static final int NO_VALUE = -1; 
	private int year = NO_VALUE;
	private int value = NO_VALUE;
	

	public boolean hasYear() {
		return year != NO_VALUE;
	}		
	
	public int getYear() {
		return year;
	}
	
	public void setYear(int year) {
		this.year = year;
	}

	public boolean hasValue() {
		return value != NO_VALUE;
	}	
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	
	
	
}
