package com.station.taxi.configuration;

import java.util.Random;

import com.station.taxi.Passenger;

public class PassengerGenerator {
	final private String[] DESTNATIONS = {"Holon","Eilat","RamatGan","Herzeliya","TelAviv","Haifa","BatYam"};
	public Passenger generateRandomPassenger(){
		Random rand = new Random();
		int length = DESTNATIONS.length;
		String dest = DESTNATIONS[rand.nextInt(length)];
		String name = generateName(4);
		Passenger p = new Passenger(name, dest);
		return p;
	}
	private String generateName(int length) {
		String name = "";    
		for(int i=0;i<length;i++){	
		int rnd = (int) (Math.random() * 52); // or use Random or whatever
		char base = (rnd < 26) ? 'A' : 'a';
		name +=(char) (base + rnd % 26);
		}
	return name;
	}
}
		