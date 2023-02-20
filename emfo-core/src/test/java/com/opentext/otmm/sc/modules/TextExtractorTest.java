package com.opentext.otmm.sc.modules;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TextExtractorTest {

	@Test
	public void matchPatter() {
		boolean found = TextExtractor.matchPatter("En un lugar de la Mancha, 2n 2023, de cuyo nombre no quiero acordarme", "\\d{4}");
		
		assertTrue(found);
	}
}
