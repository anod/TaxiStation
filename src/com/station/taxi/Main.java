package com.station.taxi;

import com.station.taxi.configuration.StationConfigLoader;


public class Main {
	
    public static void main(String[] args) {
    	
    	//Create configuration loader
    	StationConfigLoader config = new StationConfigLoader("configs/config1.xml");
    	
    	Station station;
    	try {
    		//Load station from configuration
			station = config.load();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
    	//Start station thread
		station.start();
		station.save("configs/config2.xml");
    }
}
