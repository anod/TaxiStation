package com.station.taxi;

import com.station.taxi.configuration.StationConfigLoader;
import com.station.taxi.configuration.StationConfigStorage;


public class Main {
	
    public static void main(String[] args) {
    	
    	//Create configuration loader
    	StationConfigLoader configLoader = new StationConfigLoader("configs/config1.xml");
    	
    	Station station;
    	try {
    		//Load station from configuration
			station = configLoader.load();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
    	//Start station thread
		station.start();
		
		//Save configuration
		StationConfigStorage configStorage = new StationConfigStorage("configs/config2.xml");
		configStorage.save(station);
    }
}
