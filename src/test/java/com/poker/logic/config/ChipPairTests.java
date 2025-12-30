package com.poker.logic.config;


import java.util.List;
import java.util.Properties;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChipPairTests {
	Properties props = new Properties();
	
	@BeforeEach
	public void setupChips() {
		props.setProperty("chip1Value","50");
		props.setProperty("chip1","white");
		props.setProperty("chip1Dealt", "10");
		
		props.setProperty("chip2Value","100");
		props.setProperty("chip2","red");
		props.setProperty("chip2Dealt", "5");
		
		props.setProperty("chip3Value","150");
		props.setProperty("chip3","black");
		props.setProperty("chip3Dealt", "1");
	}

	@Test
	public void testChipPairs() {
		List<Chip> pairs = Chip.getChips(3, props);
		assertEquals(3, pairs.size());
		Chip pair1 = pairs.get(0);
		pair1.setNumDealt(10);
		pair1.setValue("50");
        assertEquals("50", pair1.getValue());
        assertEquals("white", pair1.getColor());


		assertEquals(10, pair1.getNumDealt());
		
		Chip pair2 = pairs.get(1);
        assertEquals("100", pair2.getValue());
        assertEquals("red", pair2.getColor());
		assertEquals(5, pair2.getNumDealt());
		
		Chip pair3 = pairs.get(2);
        assertEquals("150", pair3.getValue());
        assertEquals("black", pair3.getColor());
		assertEquals(1, pair3.getNumDealt());
	}
}
