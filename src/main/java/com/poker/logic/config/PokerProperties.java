package com.poker.logic.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Darren Rodgers
 * Class to handle default Property creation,storage and manipulation
 */
@Getter @Setter @Slf4j
public class PokerProperties {
	private int currentSmallBlind;
	private int currentBigBlind;
	private int nextSmallBlind;
	private int nextBigBlind;
	private String blindsAggression;
	private int startingStack;
	private int numOfChips;
	private int breakIncrements;
	private long breakLength;
	private int roundTime;
	private int initialPlayers;
	private double buyInPrice;
	private double reBuyPrice;
	private List<Chip> chips;
	private int payoutPlaces;
	private final String configDir = System.getProperty("configDir");
	private int clientPort;
	private int outBoundPort;
	private int rebuys = 0;
	//TODO add method to decrement this number not just getter/setter
	private int playersRemaining;
	private int stack;
	private int totalChipsInGame;
	private List<Integer> payoutValues = new ArrayList<>();

	/**
	 * Default constructor which creates two objects one which handles 
	 * gameProperties: Time and buy Ins etc...
	 * chipProperties: Value and Colour pairs 
	 */
	public PokerProperties(int chipConfig) {
		try {
			log.info("Attempting to Load Main PokerProperties");
			Properties gameProperties = new Properties();
			gameProperties.load(new FileInputStream(configDir+"/config/poker.properties"));
			gameProperties.forEach((key,value) -> log.info("property added "+ key + " => " + value));
			log.info("Game properties added. Loading Chip Properties");
			Properties chipProperties = new Properties();

			try {
				if (chipConfig == 4){
					chipProperties.load(new FileInputStream(configDir+"/config/4ChipGame.properties"));
				} else {
					chipProperties.load(new FileInputStream(configDir+"/config/5ChipGame.properties"));
				}
			} catch (IOException e) {
				log.error("reading in PokerProperties file :", e);
			}
			chipProperties.forEach((key,value) -> log.info("chip property added {} => {}",key,value));

			currentSmallBlind = Integer.parseInt(chipProperties.getProperty("smallBlind"));
			currentBigBlind = Integer.parseInt(chipProperties.getProperty("bigBlind"));
			blindsAggression = gameProperties.getProperty("blindsAggression");
			startingStack = Integer.parseInt(chipProperties.getProperty("startingStack"));
			numOfChips = Integer.parseInt(chipProperties.getProperty("numOfChips"));
			breakIncrements = Integer.parseInt(gameProperties.getProperty("breakIncrements"));
			breakLength = Long.parseLong(gameProperties.getProperty("breakLength"));
			roundTime = Integer.parseInt(gameProperties.getProperty("standardRoundTime"));
			initialPlayers = Integer.parseInt(gameProperties.getProperty("numOfPlayers"));
			playersRemaining = initialPlayers;
			buyInPrice = Double.parseDouble(gameProperties.getProperty("buyIn"));
			reBuyPrice = Double.parseDouble(gameProperties.getProperty("reBuy"));
			chips = Chip.getChips(numOfChips, chipProperties);
			payoutPlaces = Integer.parseInt(gameProperties.getProperty("payoutPlaces"));
			clientPort = Integer.parseInt(gameProperties.getProperty("clientPort"));
			outBoundPort = (Integer.parseInt(gameProperties.getProperty("outBoundPort")));
			nextSmallBlind = getNextBlind(currentSmallBlind);
			nextBigBlind = getNextBlind(currentBigBlind);
			payoutValues.add(60);
			payoutValues.add(40);
		} catch (IOException e) {
			log.error(" reading in PokerProperties file : "+ e);
		}
	}

	/**
	 * Calculate the average stack of players remaining in the game 
	 * @return average stack of player in game
	 */
	public int calcAverageStack() {
		return totalChipsInGame/playersRemaining;
	}
	
	/**
	 * Add a rebuy to the game
	 */
	public void addRebuy() {
		rebuys=rebuys+1;
	}
	
	/**
	 * Update the blinds typically triggered at the end of a round
	 * moves the small and large blind values with the next configured jump
	 */
	public void upDateBlinds() {
		currentSmallBlind = getNextBlind(currentSmallBlind);
		currentBigBlind = getNextBlind(currentBigBlind);
		nextSmallBlind = getNextBlind(currentSmallBlind);
		nextBigBlind = getNextBlind(currentBigBlind);
	}

	private int getNextBlind(int currentBlind) {
		log.info("Blinds Aggression from properties = "+ blindsAggression);
		if("double".equals(blindsAggression)) {
			log.info("Blind doubled from "+ currentBlind+ " to " + (currentBlind * 2));
			return currentBlind * 2;
		} else {
			log.info("Blind increased from "+ currentBlind+" to " + (currentBlind + Integer.parseInt(blindsAggression)));
			return currentBlind + Integer.parseInt(blindsAggression);
		}
	}

	public int getNextBreak(int currentRound) {
		int x = currentRound + 1;
		while (x % breakIncrements != 0) {
			x++;
		}
		return x;
	}
}