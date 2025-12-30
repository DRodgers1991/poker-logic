package com.poker.logic.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Darren
 * ValueObject to hold the values for a specific chip pair
 */
@AllArgsConstructor @Getter @Setter
public class Chip {
	private String value;
	private String color;
	private int numDealt;
	
	/**
	 * Create the container of chip pairs
	 * @param numOfChips how many chip colours will be used in game
	 * @param chipProps property file to be used
	 * @return List containing the colour and value of the requested chip configurations
	 */
	public static List<Chip> getChips(int numOfChips, Properties chipProps) {
		List<Chip> chips = new ArrayList<>();
		for(int x = 1; x<= numOfChips; x++ ){
			chips.add(new Chip(chipProps.getProperty("chip"+x+"Value"),
					chipProps.getProperty("chip"+x),
					Integer.parseInt(chipProps.getProperty("chip"+x+"Dealt"))));
		}
		return chips;
	}
}
