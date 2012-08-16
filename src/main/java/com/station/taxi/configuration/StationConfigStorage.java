package com.station.taxi.configuration;

import com.station.taxi.configuration.jaxb.Config;
import com.station.taxi.configuration.jaxb.Config.ConfigTaxi;
import com.station.taxi.configuration.jaxb.ConfigManager;
import com.station.taxi.model.Cab;
import com.station.taxi.model.Passenger;
import com.station.taxi.model.TaxiMeter;
import com.station.taxi.model.TaxiStation;
import java.util.ArrayList;
import java.util.List;
/**
 * Save station into xml
 * @author Eran Zimbler
 * @version 0.2
 */
public class StationConfigStorage {
	private String mConfigFile;
	
	public StationConfigStorage(String fileName) {
		mConfigFile = fileName;
	}
	
	/**
	 * Save station into xml
	 * @param station
	 */
	public void save(TaxiStation station) {
		saveStation(
			station.getDefaultTaxiMeter(), 
			station.getStationName(), 
			station.getMaxWaitingCount(),
			station.getCabs(), 
			station.getPassengers()
		);
	}
	/**
	 * 
	 * @param meter
	 * @param name
	 * @param maxWaitingTaxis
	 * @param cabs
	 * @param passengers 
	 */
	private void saveStation(TaxiMeter meter,String name,int maxWaitingTaxis,List<Cab> cabs,List<Passenger> passengers)
	{
		Config config = new Config();
		config.setOneSecPrice(meter.getPricePerSecond());
		config.setStartPrice(meter.getStartPrice());
		
		Config.ConfigStation cs = new Config.ConfigStation();
		cs.setName(name);
		cs.setMaxWaitingTaxis(maxWaitingTaxis);

		config.setStationConfig(cs);

		List<Config.ConfigTaxi> taxis = new ArrayList<>();
		for(Cab cab: cabs) {
			ConfigTaxi ct = new Config.ConfigTaxi();
			ct.setNumber(cab.getNumber());
			ct.setWhileWaiting(cab.getWhileWaiting());
			taxis.add(ct);
		}
		config.setTaxis(taxis);
		
		List<Config.ConfigPassenger> confPassangers = new ArrayList<>();
		for(Passenger p: passengers) {
			Config.ConfigPassenger cp = new Config.ConfigPassenger();
			cp.setName(p.getPassangerName());
			cp.setDestination(p.getDestination());
			confPassangers.add(cp);
		}
		config.setPassengers(confPassangers);
		
		ConfigManager cm = new ConfigManager();
		cm.save(config, mConfigFile);
	}

}
