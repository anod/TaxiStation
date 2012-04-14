package com.station.taxi;

import com.station.taxi.configuration.StationConfigLoader;


public class Main {
	
    public static void main(String[] args) {
    	
    	StationConfigLoader config = new StationConfigLoader("configs/config1.xml");
    	
    	Station station;
    	try {
			station = config.load();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		station.start();
    }
}
