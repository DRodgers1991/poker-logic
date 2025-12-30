package com.poker.logic.game;

import com.poker.logic.config.PokerProperties;
import com.poker.logic.timers.AbstractPokerTimer;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Darren Rodgers
 * A Value Object to centralise the properties, controllers and game stats 
 * for a specific game
 */
@Getter
@Slf4j
public class PokerGame {
	private final PokerProperties properties;
	private final GameStateManager gameStateManager;
	private final AbstractPokerTimer timer;

	/**
	 * Create the PokerGame
	 */
	public PokerGame(int chipConfig, AbstractPokerTimer timer) {
		properties = new PokerProperties(chipConfig);
		gameStateManager = new GameStateManager(properties);
		this.timer = timer;
		this.timer.configure(this);
	}
	
	/**
	 * @return a string representation of the Payout structure
	 */
	public String getPrizePlaceString() {
		int totalPlayers = properties.getInitialPlayers();
		int reBuys = properties.getRebuys();
		double prizePot = calcTotalPrizeMoney();

		int payOutPlaces = properties.getPayoutPlaces();
		StringBuilder prizePlacesString = new StringBuilder("Game Over,");
		prizePlacesString.append(" there has been a total of ").append(totalPlayers).append(" players ").append(reBuys).append(" reBuys so Total Prize Pot is £ ").append(prizePot);
		if(payOutPlaces == 1 ){
			prizePlacesString.append(", with Only one payout place the winner gets ").append(prizePot);
		} else {
			for(int x = 0; x< payOutPlaces;x++) {
				double multiplier = Double.valueOf(properties.getPayoutValues().get(x))/100;
				prizePlacesString.append("\nPlayer in position ").append(x+1).append(" receives : £").append(prizePot*multiplier);
			}
		}
		log.info("Prize String | {}",prizePlacesString);
		return prizePlacesString.toString();
	}

	public double calcTotalPrizeMoney() {
		double prizePot = (properties.getInitialPlayers() * properties.getBuyInPrice())+(properties.getReBuyPrice() * properties.getRebuys());
		log.info("There has been a total of {} players {} reBuys so Total Prize Pot is £{} ",properties.getInitialPlayers(),properties.getRebuys(),prizePot);
		return prizePot;
	}
}
