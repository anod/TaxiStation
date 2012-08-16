package com.station.taxi.configuration;

import com.station.taxi.model.Passenger;
import com.station.taxi.spring.StationContext;
import java.util.Random;

/**
 * Utility to generate random passengers
 * @author Eran Zimbler
 */
public class PassengerGenerator {
	
	final private String[] DESTNATIONS = {"Holon","Eilat","RamatGan","Herzeliya","TelAviv","Haifa","BatYam"};
	
	/**
	 * Generates random passenger
	 * @return 
	 */
	public Passenger generateRandomPassenger(StationContext context){
		Random rand = new Random();
		int length = DESTNATIONS.length;
		String dest = DESTNATIONS[rand.nextInt(length)];
		String name = generateName(4);
		return context.createPassenger(name, dest);
	}
	
	/**
	 * 
	 * @param length
	 * @return 
	 */
	private String generateName(int length) {
		StringBuilder name = new StringBuilder();
		for(int i=0;i<length;i++){	
			int rnd = (int) (Math.random() * 52); // or use Random or whatever
			char base = (rnd < 26) ? 'A' : 'a';
			name.append((char) (base + rnd % 26));
		}
		return name.toString();
	}
}
		