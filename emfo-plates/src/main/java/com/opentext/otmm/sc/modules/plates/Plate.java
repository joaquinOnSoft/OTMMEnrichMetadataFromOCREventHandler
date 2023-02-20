/*
 * (C) Copyright 2021 Joaquín Garzón (http://opentext.com) and others.
 *
 *
 * Contributors:
 *   Joaquín Garzón - initial implementation
 */
package com.opentext.otmm.sc.modules.plates;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.opentext.otmm.sc.modules.TextExtractor;

public class Plate extends TextExtractor {
	private static final String PATTERN_COUNTRY_CODE_PLATE = "^(AL|D|AND|A|B|BY|BIH|BG|CY|HR|DK|SK|SLO|E|EST|FIN|F|GR|NL|H|IRL|IS|I|LV|FL|LT|L|MK|M|MD|MC|MNE|N|PL|P|GB|CZ|RO|RUS|RSM|SRB|S|CH|UA|V)$";
	
	private static final String PATTERN_SPECIAL_PLATE = "[CMEDGPNATFSHMORW]{1,5}(\\s|-)*[0-9]{4}";
	private static final String PATTERN_OLD_PLATE = "[A-Z]{1,3}(\\s|-)*[0-9]{4}(\\s|-)*[A-Z]{2}";
	private static final String PATTERN_NEW_PLATE = "[0-9]{4}(\\s|-)*[BCDFGHJKLMNPRSTVWXYZ]{3}";

	private static final String PATTERN_ALL_PLATES = PATTERN_NEW_PLATE + "|" + PATTERN_SPECIAL_PLATE + "|"
			+ PATTERN_OLD_PLATE;

	/**
	 * Provides the european country name (in Spanish) from the country code included in the plate.
	 * 
	 * This are the European country codes supported:
	 * 		Albania	- AL	
	 * 		Alemania - D	
	 * 		Andorra	- AND	
	 * 		Austria	- A	
	 * 		Bélgica	- B	
	 * 		Bielorrusia	- BY	
	 * 		Bosnia y Hercegovina - BIH	
	 * 		Bulgaria - BG	
	 * 		Chipre	- CY	
	 * 		Croacia - HR	
	 * 		Dinamarca - DK	
	 * 		Eslovaquia - SK	
	 * 		Eslovenia	- SLO	
	 * 		España - E	
	 * 		Estonia - EST	
	 * 		Finlandia - FIN	
	 * 		Francia - F	
	 * 		Grecia - GR	
	 * 		Holanda	- NL	
	 * 		Hungría	- H	
	 * 		Irlanda	- IRL	
	 * 		Islandia - IS	
	 * 		Italia	- I	
	 * 		Letonia	- LV	
	 * 		Liechtenstein - FL	
	 * 		Lituania - LT	
	 * 		Luxemburgo - L	
	 * 		Macedonia - MK	
	 * 		Malta - M	
	 * 		Moldavia - MD	
	 * 		Mónaco - MC	
	 * 		Montenegro	- MNE	
	 * 		Noruega - N	
	 * 		Polonia	- PL	
	 * 		Portugal - P	
	 * 		Reino Unido-	GB	
	 * 		República Checa - CZ	
	 * 		Rumanía - RO	
	 * 		Rusia - RUS	
	 * 		San Marino - RSM	
	 * 		Serbia - SRB	
	 * 		Suecia - S	
	 * 		Suiza - CH	
	 * 		Ucrania - UA	
	 * 		Vaticano - V
	 */
	public static String getCountryNameFromPlateCountryCode(String countryCode) {
		Map<String, String> countries = new HashMap<String, String>();
		countries.put("AL", "Albania");
		countries.put("D", "Alemania");
		countries.put("AND", "Andorra");
		countries.put("A", "Austria");
		countries.put("B", "Bélgica");
		countries.put("BY", "Bielorrusia");
		countries.put("BIH", "Bosnia y Hercegovina");
		countries.put("BG", "Bulgaria");
		countries.put("CY", "Chipre");
		countries.put("HR", "Croacia");
		countries.put("DK", "Dinamarca");
		countries.put("SK", "Eslovaquia");
		countries.put("SLO", "Eslovenia");
		countries.put("E", "España");
		countries.put("EST", "Estonia");
		countries.put("FIN", "Finlandia");		
		countries.put("F", "Francia");
		countries.put("GR", "Grecia");
		countries.put("NL", "Holanda");
		countries.put("H", "Hungría");
		countries.put("IRL", "Irlanda");
		countries.put("IS", "Islandia");			
		countries.put("I", "Italia");
		countries.put("LV", "Letonia");
		countries.put("FL", "Liechtenstein");
		countries.put("LT", "Lituania");
		countries.put("L", "Luxemburgo");
		countries.put("MK", "Macedonia");
		countries.put("M", "Malta");
		countries.put("MD", "Moldavia");				
		countries.put("MC", "Mónaco");		
		countries.put("MNE", "Montenegro");		
		countries.put("N", "Noruega");				
		countries.put("P", "Polonia");						
		countries.put("P", "Portugal");				
		countries.put("GB", "Reino Unido");		
		countries.put("CZ", "República Checa");		
		countries.put("RO", "Rumanía");				
		countries.put("RUS", "Rusia");						
		countries.put("RSM", "San Marino");				
		countries.put("SRB", "Serbia");		
		countries.put("S", "Suecia");		
		countries.put("CH", "Suiza");				
		countries.put("UA", "Ucrania");						
		countries.put("V", "Vaticano");		

		return countries.get(countryCode);
	}

	public static String findFirstCountryCode(String txt) {
		String countryCode = null;
		String countryName = null;
		
		Matcher m = Pattern.compile(PATTERN_COUNTRY_CODE_PLATE, Pattern.DOTALL | Pattern.MULTILINE).matcher(txt);
		if (m.find()) {
			countryCode = m.group();
		}

		if(countryCode != null) { 
			countryName = Plate.getCountryNameFromPlateCountryCode(countryCode);
		}
		return countryName;
	}	
	
	public static String findFirstPlate(String txt) {
		String plate = null;
		String pattern = PATTERN_NEW_PLATE;

		if (isNew(txt)) {
			pattern = PATTERN_NEW_PLATE;
		} else if (isSpecial(txt)) {
			pattern = PATTERN_SPECIAL_PLATE;
		} else if (isOld(txt)) {
			pattern = PATTERN_OLD_PLATE;
		}

		Matcher m = Pattern.compile(pattern, Pattern.DOTALL | Pattern.MULTILINE).matcher(txt);
		if (m.find()) {
			plate = m.group();
		}

		return plate;
	}

	/**
	 * Validate if a given text contains a valid Spanish plate number
	 * 
	 * Valid Spanish plate number: 1234BCD 1234 FGH 2345-JKL A 0849 CS A-0849 CS B
	 * 1234 BL M-1234 BL SO 1234 BL B 1234 BL M 1234 BL SO-1234 BL Old plate number:
	 * One letter code: A 0849 CS
	 * 
	 * Two letter code: GI-1234-BL
	 * 
	 * Two/three-letter special code (such as ET for army cars and DGP for police
	 * cars) DGP 1234 BL DGP 3874 CNP-5764 E8720
	 * 
	 * DGP (Dirección General de Policia) - Spanish Police DGP1234
	 * 
	 * Corps of the Mossos d'Esquadra" CME1234
	 * 
	 * @param plateNumber - Text that might contain a plate number
	 * @return true if is a valid plate number, false in other case
	 * 
	 * @see https://stackoverflow.com/questions/41146180/how-to-build-a-regex-pattern-to-verify-a-spanish-car-enrollment
	 * @see https://github.com/singuerinc/spanish-car-plate
	 * @see https://regex101.com/
	 */
	public static boolean containsPlate(String plateNumber) {
		return matchPatter(plateNumber, PATTERN_ALL_PLATES);
	}

	/**
	 * Check if a text includes a special plate, i.e. Police, Air force, Army, Navy,
	 * etc. Some examples: CME 2342 E 1660
	 * 
	 * @param txt - Text to evaluate looking for special plate numbers
	 * @return Returns true if is a valid special car plate
	 */
	public static boolean isSpecial(String txt) {
		return matchPatter(txt, PATTERN_SPECIAL_PLATE);
	}

	/**
	 * Check if a text includes a valid old system (1971-2000) car plate Some
	 * examples: one-letter code: A 0849 CS two-letter code: GI-1234-BL
	 * two/three-letter special code (such as ET for army cars and DGP for police
	 * cars): DGP 1234 BL
	 * 
	 * @param txt - Text to evaluate looking for old plate numbers
	 * @return Returns true if is a valid (old system 1971-2000) car plate
	 */
	public static boolean isOld(String txt) {
		return matchPatter(txt, PATTERN_OLD_PLATE);
	}

	/**
	 * Check if a text includes a valid "new" car plate (2000 to present date) Some
	 * examples: 1234BCD 1234 FGH 2345-JKL
	 * 
	 * @param txt - Text to evaluate looking for new plate numbers
	 * @return Returns true if is a valid "new" (2000 to present date) car plate
	 */
	public static boolean isNew(String txt) {
		return matchPatter(txt, PATTERN_NEW_PLATE);
	}
}