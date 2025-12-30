package com.poker.logic.game;


import com.poker.logic.config.PokerProperties;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameStateManagerTests {

	
	@Test
	public void testStates() {
		GameStateManager state = new GameStateManager(new PokerProperties(4));
		assertFalse(state.isGameStarted());
		assertFalse(state.isGameEnded());
		assertFalse(state.isGamePaused());
		state.startGame();
		assertTrue(state.isGameStarted());
		assertFalse(state.isGameEnded());
		assertFalse(state.isGamePaused());
		state.pauseGame();
		assertTrue(state.isGameStarted());
		assertFalse(state.isGameEnded());
		assertTrue(state.isGamePaused());
		state.unPauseGame();
		assertTrue(state.isGameStarted());
		assertFalse(state.isGameEnded());
		assertFalse(state.isGamePaused());
		state.endGame();
		assertFalse(state.isGameStarted());
		assertTrue(state.isGameEnded());
		assertFalse(state.isGamePaused());
	}
}
