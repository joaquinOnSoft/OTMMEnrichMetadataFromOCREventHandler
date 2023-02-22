/*
 * (C) Copyright 2023 Joaquín Garzón (http://opentext.com) and others.
 * 
 * Contributors:
 *   Joaquín Garzón - initial implementation
 */
package com.opentext.otmm.sc.modules.coins;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.opentext.otmm.sc.modules.TextExtractor;

/**
 * Utility class to manage OCR text extracted from an coin 
 * picture once the media analysis is completed
 **/
public class Coin  extends TextExtractor{
	public static final String CURRENCY_EURO = "EUR";
	public static final String CURRENCY_PTA = "PTA";

	private static final String PATTERN_YEAR = "(?!0000)(\\d{4}|(\\d{2} \\d{2}))";
	private static final String PATTERN_VALUE = "([0-9]{1,3}\\s*(EURO|euro|E|Pta|pta|Ptas|ptas|Pts|pts))";
	private static final String PATTERN_NUMERIC_VALUE = "[0-9]{1,3}";
	private static final String PATTERN_CURRENCY = "(\\s*(EURO|euro|E|Pta|pta|Ptas|ptas|Pts|pts))";

	public static boolean hasYear(String txt) {
		return matchPatter(txt, PATTERN_YEAR);
	}

	public static boolean hasValue(String txt) {
		return matchPatter(txt, PATTERN_VALUE);
	}	

	/**
	 * Find the first occurrence of a year (4 digits in a row) in the text provided
	 * @param txt - OCR text extracted in the media analysis of a coin's picture
	 * @return first occurrence of a year, or null 
	 */
	public static String findFirstYear(String txt) {
		String year = null;

		Matcher m = Pattern.compile(PATTERN_YEAR, Pattern.DOTALL | Pattern.MULTILINE).matcher(txt);
		if (m.find()) {
			year = m.group();
		}

		return year;
	}
	
	/**
	 * Find the first occurrence of a facial value of a coin in the text provided
	 * @param txt - OCR text extracted in the media analysis of a coin's picture
	 * @return first occurrence of a facial value, or null 
	 */	
	public static String findFirstValue(String txt) {
		//Normalize value, remove currency reference: EURO, euro, E, Pta, pta		
		return findFirstValueWithCurrency(txt, PATTERN_CURRENCY);
	}

	/**
	 * Find the first occurrence of a currency name in the text provided
	 * NOTE: This method only recognizes EURO and PTA (legacy Spanish currency)
	 * @param txt - OCR text extracted in the media analysis of a coin's picture
	 * @return "EUR", "PTA", or null 
	 */		
	public static String findFirstCurrency(String txt) {
		//Normalize currency, remove facial value
		String currency = findFirstValueWithCurrency(txt, PATTERN_NUMERIC_VALUE);

		if(currency != null) {
			switch (currency.trim().toUpperCase()) {
			case "EURO":
				//Intentionally empty
			case "E":			
				currency = CURRENCY_EURO;
				break;
			case "PTA":
				//Intentionally empty				
			case "PTS":
				//Intentionally empty				
			case "PTAS":			
				currency = CURRENCY_PTA;
				break;
			default:
				currency = null;
			}
		}
		
		return currency;
	}	

	private static String findFirstValueWithCurrency(String txt, String removePatter) {
		String value = null;

		Matcher m = Pattern.compile(PATTERN_VALUE, Pattern.DOTALL | Pattern.MULTILINE).matcher(txt);
		if (m.find()) {
			value = m.group();
		}

		if(value != null && removePatter != null) { 
			value = value.replaceAll(removePatter, "");
		}		

		return value;
	}		
}
