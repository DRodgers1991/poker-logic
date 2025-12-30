package com.poker.logic.game;

import com.poker.logic.config.PokerProperties;
import com.poker.logic.timers.AbstractPokerTimer;
import lombok.Getter;

/**
 * @author Darren
 *	Holds  all main functionality controllers needed by a Poker Match
 */
public class GameStateManager {

	@Getter private boolean gamePaused = false;
	@Getter private boolean gameStarted = false;
	@Getter private boolean gameEnded = false;
	private final PokerProperties properties;

	public GameStateManager(PokerProperties properties) {
		this.properties = properties;
	}

	/**
	 * Decrements the remaining players and if only one is remaining puts the game into an ended state
	 */
	public void eliminatePlayer() {
		int newPlayerNumber = properties.getPlayersRemaining()-1;
		properties.setPlayersRemaining(newPlayerNumber);
		if(newPlayerNumber ==1) {
			endGame();
		}
	}

	/**
	 * Creates two timers
	 * 1. to keep track of Time game started and total time played
	 * 2. Keep track of the configured round time and management of rounds
	 * @param timer The overall time manager controlling this game
	 */
	public void startGame(AbstractPokerTimer timer) {
		startGame();
		new Thread(timer).start();
	}
	
	public void pauseGame() {
		gamePaused = true;
	}
	
	public void unPauseGame() {
		gamePaused = false;
	}

	public void endGame(){
		gameEnded = true;
		gamePaused = false;
		gameStarted = false;
	}

	public void startGame() {
		gameEnded = false;
		gamePaused = false;
		gameStarted = true;
	}
}
