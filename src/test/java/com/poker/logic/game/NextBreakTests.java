package com.poker.logic.game;

import com.poker.logic.config.PokerProperties;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NextBreakTests {


    @Test
    void oneRoundBreakTest() {
        PokerProperties properties = new PokerProperties(4);
        properties.setBreakIncrements(1);
        assertEquals(2,properties.getNextBreak(1));
    }

    @Test
    void threeRoundBreakTest() {
        PokerProperties properties = new PokerProperties(4);
        properties.setBreakIncrements(3);
        assertEquals(3,properties.getNextBreak(1));
    }

    @Test
    void fiveRoundBreakTest() {
        PokerProperties properties = new PokerProperties(4);
        properties.setBreakIncrements(5);
        assertEquals(5,properties.getNextBreak(1));
    }
}
