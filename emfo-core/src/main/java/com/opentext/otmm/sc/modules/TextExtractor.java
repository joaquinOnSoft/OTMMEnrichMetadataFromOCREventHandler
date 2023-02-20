package com.opentext.otmm.sc.modules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextExtractor {

	public static boolean matchPatter(String txt, String regex) {
		Pattern pattern = Pattern.compile(regex, Pattern.DOTALL | Pattern.MULTILINE);
		Matcher matcher = pattern.matcher(txt);
	
		// See:
		// https://stackoverflow.com/questions/3651725/match-multiline-text-using-regular-expression
		// find() succeeds if a match can be found anywhere in the target string,
		// while matches() expects the regex to match the entire string.
		return matcher.find();
	}

	public TextExtractor() {
		super();
	}

}