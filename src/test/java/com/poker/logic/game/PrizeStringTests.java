package com.poker.logic.game;


import java.util.Arrays;

import com.poker.logic.timers.AbstractPokerTimer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrizeStringTests {

	private PokerGame game;
	
	@BeforeEach
	public void setup() {
		game = new PokerGame(4, Mockito.mock(AbstractPokerTimer.class));
		game.getProperties().setReBuyPrice(5.0);
		game.getProperties().setBuyInPrice(10.0);
		game.getProperties().setPayoutPlaces(2);
		game.getProperties().setBlindsAggression("double");
	}
	
	@Test
	public void onePayoutPlace() {
		int totalPlayers = 5;
		int reBuys = 0;
		double prizePot = 50.0;
		game.getProperties().setInitialPlayers(5);
		game.getProperties().setPayoutPlaces(1);
		game.getProperties().setRebuys(0);
		String endGame = "Game Over, there has been a total of "+ totalPlayers+" players "+reBuys+" reBuys so Total Prize Pot is £ " + prizePot;
		String singlePayout = ", with Only one payout place the winner gets "+prizePot;
		assertEquals(endGame+singlePayout, game.getPrizePlaceString());
	}
	
	@Test
	public void twoPayoutPlace() {
		int totalPlayers = 5;
		int reBuys = 0;
		double prizePot = 50.0;
		game.getProperties().setInitialPlayers(5);
		game.getProperties().setPayoutPlaces(2);
		game.getProperties().setPayoutValues(Arrays.asList(60,40));
		game.getProperties().setRebuys(0);
		String endGame = "Game Over, there has been a total of "+ totalPlayers+" players "+reBuys+" reBuys so Total Prize Pot is £ " + prizePot;
		String play1 = "\nPlayer in position "+ 1 + " receives : £"+50 *.6;
		String play2 = "\nPlayer in position "+ 2 + " receives : £"+50 *.4;
		assertEquals(endGame+play1+play2, game.getPrizePlaceString());
	}
}
