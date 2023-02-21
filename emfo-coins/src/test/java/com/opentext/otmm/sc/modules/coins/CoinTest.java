/*
 * (C) Copyright 2023 Joaquín Garzón (http://opentext.com) and others.
 * 
 * Contributors:
 *   Joaquín Garzón - initial implementation
 */
package com.opentext.otmm.sc.modules.coins;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CoinTest {
	private static final String COIN_ZAMORA_5_EURO = "CATEDRAL 5E O EURO palan PUENTE DE PIEDE "
			+ "C C C C C C C o o o o o Anony fen\r\n\r\n";
	
	private static final String COIN_ALCALA_5_EURO = "ALCALÁ DES PATRIMONIO DE LA HUMA EURO I DE HENARES\r\n\r\n";
	
	private static final String COIN_SANTANDER_5_EURO = "၀ဝ-၁၁၁၁၁၁၁၁၁၁၁၁၁၁၁ OFF ,,,- EEEEEE T 000000000000 PALACIO DE LA MAGDALENA 5 11. ဟု EURO to g 27 BOL ecco";

	private static final String COIN_EUFA_2020_100_EURO = "UEFA EURO 2020 QUEFA ATM ۱۱۲ 100 EURO M\r\n\r\n";
	
	@Test
	public void hasYear() {		
		assertFalse(Coin.hasYear(COIN_ZAMORA_5_EURO));
		assertFalse(Coin.hasYear(COIN_ALCALA_5_EURO));
		assertFalse(Coin.hasYear(COIN_SANTANDER_5_EURO));
		assertTrue(Coin.hasYear(COIN_EUFA_2020_100_EURO));
	}
	
	@Test
	public void hasValue() {		
		assertTrue(Coin.hasValue(COIN_ZAMORA_5_EURO));
		assertFalse(Coin.hasValue(COIN_ALCALA_5_EURO));
		assertFalse(Coin.hasValue(COIN_SANTANDER_5_EURO));
		assertTrue(Coin.hasValue(COIN_EUFA_2020_100_EURO));
	}
	
	@Test
	public void findFirstYear() {		
		assertEquals("2020", Coin.findFirstYear(COIN_EUFA_2020_100_EURO));
	}
	
	@Test
	public void findFirstValue() {
		assertEquals("5", Coin.findFirstValue(COIN_ZAMORA_5_EURO));		
		assertEquals("100", Coin.findFirstValue(COIN_EUFA_2020_100_EURO));
	}
	
	@Test
	public void findFirstCurrency() {
		assertEquals(Coin.CURRENCY_EURO, Coin.findFirstCurrency(COIN_ZAMORA_5_EURO));		
		assertEquals(Coin.CURRENCY_EURO, Coin.findFirstCurrency(COIN_EUFA_2020_100_EURO));
	}		
}
