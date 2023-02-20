/*
 * (C) Copyright 2021 Joaquín Garzón (http://opentext.com) and others.
 * 
 * Contributors:
 *   Joaquín Garzón - initial implementation
 */
package com.opentext.otmm.sc.modules.plates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PlateTest {
	
	@Test
	void testGetCountryNameFromPlateCountryCode() {
		assertEquals("España", Plate.getCountryNameFromPlateCountryCode("E"));
		assertEquals("Francia", Plate.getCountryNameFromPlateCountryCode("F"));
		assertEquals("Italia", Plate.getCountryNameFromPlateCountryCode("I"));		
		assertEquals("Holanda", Plate.getCountryNameFromPlateCountryCode("NL"));		
	}
	
	@Test
	void testFindFirstCountryCode() {
		assertNull(Plate.findFirstCountryCode("6852 KWS"));
		assertNull(Plate.findFirstCountryCode("6852 KWS\\n"));	
		
		assertNull(Plate.findFirstCountryCode("S\\n2093 GSW\\nE\\n"));			
		assertEquals("Suecia", Plate.findFirstCountryCode("S\n2093 GSW\nE\n"));	
		
		assertNull(Plate.findFirstCountryCode("pa\\n4210 KVH\\ncoches.com\\n"));		
		assertNull(Plate.findFirstCountryCode("pa\n4210 KVH\ncoches.com\n"));
		
		assertNull(Plate.findFirstCountryCode("32\\n9649 HDG\\nE\\nTPOR\\nHerranz\\nwww.bmw.es/herranz\\n"));
		assertEquals("España", Plate.findFirstCountryCode("32\n9649 HDG\nE\nTPOR\nHerranz\nwww.bmw.es/herranz\n"));
	}

	@Test
	void testContainsPlate(){
		assertTrue(Plate.containsPlate("1234BCD"));
		assertTrue(Plate.containsPlate("1234 FGH"));
		assertTrue(Plate.containsPlate("2345-JKL"));
		assertTrue(Plate.containsPlate("A 0849 CS"));
		assertTrue(Plate.containsPlate("A-0849 CS"));
		assertTrue(Plate.containsPlate("B 1234 BL"));
		assertTrue(Plate.containsPlate("M-1234 BL"));
		assertTrue(Plate.containsPlate("SO 1234 BL"));
		assertTrue(Plate.containsPlate("B 1234 BL"));
		assertTrue(Plate.containsPlate("M 1234 BL"));
		assertTrue(Plate.containsPlate("SO-1234 BL"));
	}

	@Test
	void testContainsPlateMultiline() {
		assertTrue(Plate.containsPlate("6852 KWS"));
		assertTrue(Plate.containsPlate("6852 KWS\\n"));	
		
		assertTrue(Plate.containsPlate("S\\n2093 GSW\\nE\\n"));			
		assertTrue(Plate.containsPlate("S\n2093 GSW\nE\n"));	
		
		assertTrue(Plate.containsPlate("pa\\n4210 KVH\\ncoches.com\\n"));		
		assertTrue(Plate.containsPlate("pa\n4210 KVH\ncoches.com\n"));
		
		assertTrue(Plate.containsPlate("32\\n9649 HDG\\nE\\nTPOR\\nHerranz\\nwww.bmw.es/herranz\\n"));
		assertTrue(Plate.containsPlate("32\n9649 HDG\nE\nTPOR\nHerranz\nwww.bmw.es/herranz\n"));
	}
		
	@Test
	void testNotContainsPlate(){
		assertFalse(Plate.containsPlate("Lorem ipsum dolor sit amet, consectetur adipiscing elit."));
	}	
	 
	@Test
	void testContainsPlateOneLetterCode() {
		assertTrue(Plate.containsPlate("A 0849 CS"));
	}

	@Test
	void testContainsPlateTwoLetterCode() {
		assertTrue(Plate.containsPlate("GI-1234-BL"));
	}

	@Test
	void testContainsPlateDGP() {
		//DGP (Dirección General de Policia) - Spanish Police
		assertTrue(Plate.containsPlate("DGP1234"));
	}

	@Test
	void testContainsPlateCorpsOFTheMossosEsquadra() {
		//Corps of the Mossos d'Esquadra"
		assertTrue(Plate.containsPlate("CME1234"));
	}
	
	@Test
	void testIsNew() {
		assertTrue(Plate.isNew("1234BCD"));
		assertTrue(Plate.isNew("1234 FGH"));
		assertTrue(Plate.isNew("2345-JKL"));
	}
	
	@Test
	void testIsOld() {
		assertTrue(Plate.isOld("A 0849 CS"));
		assertTrue(Plate.isOld("GI-1234-BL"));
		assertTrue(Plate.isOld("DGP 1234 BL"));
	}
	
	@Test
	void testIsSpecial() {
		assertTrue(Plate.isSpecial("CME 2342"));
		assertTrue(Plate.isSpecial("E 1660"));
		assertTrue(Plate.isSpecial("DGP1234"));
	}		
	
	@Test
	void testfindFirstPlate() {
		assertEquals("0815 GYR", Plate.findFirstPlate("0815 GYR E"));
		assertEquals("8806 KZS", Plate.findFirstPlate("8806 KZS"));
		assertEquals("4210 KVH", Plate.findFirstPlate("pa 4210 KVH coches.com"));
		assertEquals("2093 GSW", Plate.findFirstPlate("S 2093 GSW E"));
		assertEquals("6662 HYW", Plate.findFirstPlate("E 6662 HYW BKMIB RETAIL www.kmtretail madrid.com Actualidad Motor"));		
		assertEquals("9659 HJC", Plate.findFirstPlate("9659 HJC E"));
		assertEquals("3685 HDP", Plate.findFirstPlate("NISSAN 3685 HDP E)"));
		assertEquals("6852 KWS", Plate.findFirstPlate("6852 KWS"));
		assertEquals("9649 HDG", Plate.findFirstPlate("32 9649 HDG E TPOR Herranz www.bmw.es/herranz"));		
	}
	
	@Test
	void testfindFirstPlateMultiline() {
		assertEquals("6852 KWS", Plate.findFirstPlate("6852 KWS"));
		assertEquals("6852 KWS", Plate.findFirstPlate("6852 KWS\\n"));	
		
		assertEquals("2093 GSW", Plate.findFirstPlate("S\\n2093 GSW\\nE\\n"));			
		assertEquals("2093 GSW", Plate.findFirstPlate("S\n2093 GSW\nE\n"));	
		
		assertEquals("4210 KVH", Plate.findFirstPlate("pa\\n4210 KVH\\ncoches.com\\n"));		
		assertEquals("4210 KVH", Plate.findFirstPlate("pa\n4210 KVH\ncoches.com\n"));
		
		assertEquals("9649 HDG", Plate.findFirstPlate("32\\n9649 HDG\\nE\\nTPOR\\nHerranz\\nwww.bmw.es/herranz\\n"));
		assertEquals("9649 HDG", Plate.findFirstPlate("32\n9649 HDG\nE\nTPOR\nHerranz\nwww.bmw.es/herranz\n"));
	}	
	
	@Test
	void testNotFindFirstPlate() {
		assertNull(Plate.findFirstPlate("$ INI 1276 E"));
		assertNull(Plate.findFirstPlate("Lorem ipsum dolor sit amet, consectetur adipiscing elit."));
	}	
}
