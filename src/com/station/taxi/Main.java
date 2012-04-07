package com.station.taxi;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.station.taxi.configuration.StationConfigLoader;


public class Main {
	
    public static void main(String[] args) {
    	
    	try {
    		StationConfigLoader config = new StationConfigLoader("configs/config1.xml");
			Station station = config.load();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
