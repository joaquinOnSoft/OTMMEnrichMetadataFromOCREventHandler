/*
 * (C) Copyright 2023 Joaqu�n Garz�n (http://opentext.com) and others.
 * 
 * Contributors:
 *   Joaqu�n Garz�n - initial implementation
 */
package com.opentext.otmm.sc.modules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextExtractor {

	public TextExtractor() {
		super();
	}
	
	/**
	 * Match multiline text using regular expression
	 * @param txt - Base text
	 * @param regex - Regular expression to identify a pattern
	 * @return true if a match is found, false in other case
	 */
	public static boolean matchPatter(String txt, String regex) {
		Pattern pattern = Pattern.compile(regex, Pattern.DOTALL | Pattern.MULTILINE);
		Matcher matcher = pattern.matcher(txt);
	
		// See:
		// https://stackoverflow.com/questions/3651725/match-multiline-text-using-regular-expression
		// find() succeeds if a match can be found anywhere in the target string,
		// while matches() expects the regex to match the entire string.
		return matcher.find();
	}
}