package com.station.taxi;

import com.station.taxi.configuration.StationConfigLoader;


public class Main {
	
    public static void main(String[] args) {
    	
    	StationConfigLoader config = new StationConfigLoader("configs/config1.xml");
    	
    	try {
			Station station = config.load();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
