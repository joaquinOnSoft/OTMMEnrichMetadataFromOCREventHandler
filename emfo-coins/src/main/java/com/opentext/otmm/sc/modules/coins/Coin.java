package com.opentext.otmm.sc.modules.coins;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.opentext.otmm.sc.modules.TextExtractor;

/**
 * Utility class to manage OCR text extracted from an coin 
 * picture once the media analysis is completed
 **/
public class Coin  extends TextExtractor{
	private static final String PATTERN_YEAR = "(?!0000)(\\d{4}|(\\d{2} \\d{2}))";
	private static final String PATTERN_VALUE = "([0-9]{1,3}\\s*(EURO|euro|Pta|pta|E))";
	
	public static boolean hasYear(String txt) {
		return matchPatter(txt, PATTERN_YEAR);
	}
	
	public static boolean hasValue(String txt) {
		return matchPatter(txt, PATTERN_VALUE);
	}	
	
	public static String findFirstYear(String txt) {
		String year = null;

		Matcher m = Pattern.compile(PATTERN_YEAR, Pattern.DOTALL | Pattern.MULTILINE).matcher(txt);
		if (m.find()) {
			year = m.group();
		}

		return year;
	}
	
	public static String findFirstValue(String txt) {
		String value = null;

		Matcher m = Pattern.compile(PATTERN_VALUE, Pattern.DOTALL | Pattern.MULTILINE).matcher(txt);
		if (m.find()) {
			value = m.group();
		}

		return value;
	}		
}
