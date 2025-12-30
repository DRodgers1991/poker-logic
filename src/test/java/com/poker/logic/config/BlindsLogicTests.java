package com.poker.logic.config;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BlindsLogicTests {
	private PokerProperties properties;
	private static final int START_S = 5;
	private static final int STAR_B = 10;

	@Test
	public void testRoundOnDouble() {
		properties = new PokerProperties(4);
		properties.setCurrentSmallBlind(START_S);
		properties.setCurrentBigBlind(STAR_B);
		properties.setBlindsAggression("double");
		
		properties.upDateBlinds();
		assertEquals(10, properties.getCurrentSmallBlind());
		assertEquals(20, properties.getCurrentBigBlind());
		assertEquals(20, properties.getNextSmallBlind());
		assertEquals(40, properties.getNextBigBlind());
	}

	@Test
	public void testRoundOn3ShouldAdd() {
		properties = new PokerProperties(5);
		properties.setCurrentSmallBlind(START_S);
		properties.setCurrentBigBlind(STAR_B);
		properties.setBlindsAggression("3");
		properties.upDateBlinds();
		assertEquals(8, properties.getCurrentSmallBlind());
		assertEquals(13, properties.getCurrentBigBlind());
		assertEquals(11, properties.getNextSmallBlind());
		assertEquals(16, properties.getNextBigBlind());
		
	}
}
