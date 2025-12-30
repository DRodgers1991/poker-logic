package com.poker.logic.timers;

import com.poker.logic.game.GameStateManager;
import com.poker.logic.game.PokerGame;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 * @author Darren Rodgers
 * This base class handles all time logic related to the game such as time gone and management of break logic
 */
@Slf4j
public abstract class AbstractPokerTimer implements Runnable {
	private PokerGame pokerGame;
	private static final SimpleDateFormat MIN_SEC = new SimpleDateFormat("mm:ss");
	private static final SimpleDateFormat HOUR_MIN_SEC = new SimpleDateFormat("HH:mm:ss");
	@Getter
	private LocalTime initialStartTime;
	@Getter
	private LocalTime timeRoundStarted;
	private LocalTime timeRoundEnds;
	@Getter
	private int currentRound = 1;

	public void configure(PokerGame pokerGame) {
		this.pokerGame = pokerGame;
	}

	@Override
	public void run() {
		initialStartTime = LocalTime.now();
		resetRound();

		GameStateManager gameStateManager = pokerGame.getGameStateManager();
		while (gameStateManager.isGameStarted() && pokerGame.getProperties().getPlayersRemaining() > 1) {
			if (!gameStateManager.isGamePaused() && timeRoundEnds.isBefore(LocalTime.now())) {
				roundUpdate();
			}
		}
		log.info("Game has Ended");
	}

	private void roundUpdate()  {
		Runnable t = this::announceEndOfRound;
		t.run();
		log.info("Round {} completed at {} there are still {} players remaining",currentRound,new Time(System.currentTimeMillis()),pokerGame.getProperties().getPlayersRemaining());

		pokerGame.getProperties().upDateBlinds();
		if (currentRound+1 == pokerGame.getProperties().getNextBreak(currentRound)) {
			createBreak();
		}
		currentRound++;
		resetRound();
	}

	private void resetRound() {
		timeRoundStarted = LocalTime.now();
		timeRoundEnds = timeRoundStarted.plusSeconds(pokerGame.getProperties().getRoundTime() * 60L);
	}

	private void createBreak(){
		long breakLength = pokerGame.getProperties().getBreakLength() * 60 * 1000;
		log.info("break should sleep for {}", breakLength);
		Time resumeTime = new Time(System.currentTimeMillis() + breakLength);
		new Thread(() -> announceBreak(resumeTime))
				.start();
		pokerGame.getGameStateManager().pauseGame();
		try {
			Thread.sleep(breakLength);
		} catch (InterruptedException e) {
			log.error("Error Pausing break Timer Thread");
			Thread.currentThread().interrupt();
		}
		pokerGame.getGameStateManager().unPauseGame();
		log.info("Resuming After Break ");
	}



    public long getRoundTimeToGo(LocalTime timeToCompare) {
		return  timeRoundEnds == null ? 0 : timeToCompare.until(timeRoundEnds, ChronoUnit.MILLIS);
    }

	public long getTotalGameTime(LocalTime timeToCompare) {
		return  initialStartTime == null ? 0 : initialStartTime.until(timeToCompare, ChronoUnit.MILLIS);
	}

	public abstract void announceBreak(Time resumeTime);
	public abstract void announceEndOfRound();

	@Deprecated
	/*
		Method is @Deprecated and should not be used and will be removed in 3.2.0
		pull the timeRoundStarted value from its own getter and convert locally
	 */
	public String getTimeToGoAsString() {
		return MIN_SEC.format(new Time(getRoundTimeToGo(LocalTime.now())));
	}

	@Deprecated
	/*
		Method is @Deprecated and should not be used and will be removed in 3.2.0
		pull the timeRoundStarted value from its own getter and convert locally
	 */
	public String getRoundStartAsString() {
		return HOUR_MIN_SEC.format(new Time(getRoundTimeToGo(LocalTime.now())));
	}
}
