package com.station.taxi.configuration;

import com.station.taxi.configuration.jaxb.Config;
import com.station.taxi.configuration.jaxb.Config.ConfigPassenger;
import com.station.taxi.configuration.jaxb.Config.ConfigStation;
import com.station.taxi.configuration.jaxb.Config.ConfigTaxi;
import com.station.taxi.configuration.jaxb.ConfigManager;
import com.station.taxi.model.Cab;
import com.station.taxi.model.Passenger;
import com.station.taxi.model.TaxiMeter;
import com.station.taxi.model.TaxiStation;
import com.station.taxi.spring.StationContext;
import java.util.ArrayList;
import java.util.List;
/**
 * Parse configuration xml and load station
 * @author alex
 * @version 0.2
 */
public class StationConfigLoader {
	private final String mFileName;
	private final StationContext mContext;
	private final String mSchemaFileName;

	/**
	 * 
	 * @param fileName
	 * @param schemaFileName 
	 * @param context 
	 */
	public StationConfigLoader(String fileName, String schemaFileName, StationContext context) {
		mFileName = fileName;
		mSchemaFileName = schemaFileName;
		mContext = context;
	}
	
	/**
	 * Load station from configuration file
	 * @return
	 */
	public TaxiStation load() {
		ConfigManager cm = new ConfigManager();
		Config config = cm.load(mFileName,mSchemaFileName);
        
        // parse <system> tag
        TaxiMeter meter = createTaxiMeter(config);
        TaxiStation station = createStation(config, meter);
        ArrayList<Cab> taxis = createTaxiCabs(config);
        ArrayList<Passenger> passengers = readPassengers(config);
        station.init(taxis, passengers);
		return station;
	}

	/**
	 * Creates passengers from configuration
	 * @param config
	 * @return
	 */
	private ArrayList<Passenger> readPassengers(Config config) {
		ArrayList<Passenger> result = new ArrayList<>();
		List<ConfigPassenger> passengers = config.getPassengers();
        for(int i=0; i<passengers.size() ; i++){
			ConfigPassenger cp = passengers.get(i);
            String name = cp.getName();
            String destination = cp.getDestination();
			result.add(mContext.createPassenger(name, destination));
        }
		return result;		
	}

	/**
	 * Creates cabs from configuration
	 * @param config
	 * @return
	 */
	private ArrayList<Cab> createTaxiCabs(Config config) {
		ArrayList<Cab> result = new ArrayList<>();
		List<ConfigTaxi> taxis = config.getTaxis();
		
        for(int i=0; i<taxis.size() ; i++){
			ConfigTaxi taxi = taxis.get(i);
            Integer cabNum = taxi.getNumber(); 
            String whileWaiting = taxi.getWhileWaiting();
        	result.add(mContext.createCab(cabNum, whileWaiting));
        }
		return result;
	}

	/**
	 * Create a station from configuration
	 * @param config
	 * @param meter
	 * @return
	 */
	private TaxiStation createStation(Config config, TaxiMeter meter) {
        
		ConfigStation sc = config.getConfigStation();
		
        String stationName = sc.getName();
        Integer maxWaitingTaxis = sc.getMaxWaitingTaxis();
        
		return new TaxiStation(mContext, stationName, maxWaitingTaxis, meter);
	}

	/**
	 * Read taxi meter values
	 * @param config
	 */
	private TaxiMeter createTaxiMeter(Config config) {
        return new TaxiMeter(
       		config.getStartPrice(),
        	config.getOneSecPrice()
        );
	}	
	
	
}
