package com.station.taxi.logger;

import com.station.taxi.ICab;
import com.station.taxi.IPassenger;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Helper object to create log
 * @author alex
 * @author Eran Zimbler
 * @version 0.1
 *
 */
public class LoggerWrapper {
	private static final String LOG_CAB = "logs/CabLog_%s.txt";
	private static final String LOG_PASSENGER = "logs/PassengerLog_%s.txt";
	private static final Logger sLogger = Logger.getLogger("logs/TaxiStation");
	
	static {
		ConsoleHandler consoleHandler = new ConsoleHandler();
		consoleHandler.setFormatter(new StationFormatter());
		sLogger.setUseParentHandlers(false);
		sLogger.addHandler(consoleHandler);
		try {
			sLogger.addHandler(new FileHandler("logs/StationLog.txt"));
		} catch (IOException | SecurityException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Add a new Cab Logger
	 * @param cab
	 */
	public static void addCabLogger(ICab cab) {
		
		FileHandler fileHandler;
		try {
			fileHandler = new FileHandler(String.format(LOG_CAB, cab.getNumber()));
			fileHandler.setFormatter(new StationFormatter());
			fileHandler.setFilter(new CabFilter(cab.getNumber()));
			sLogger.addHandler(fileHandler);
		} catch (IOException | SecurityException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * Add a passenger logger
	 * @param p
	 */
	public static void addPassengerLogger(IPassenger p) {
		FileHandler fileHandler;
		try {
			fileHandler = new FileHandler(String.format(LOG_PASSENGER, p.getPassangerName()));
			fileHandler.setFormatter(new StationFormatter());
			fileHandler.setFilter(new PassengerFilter(p.getPassangerName()));
			sLogger.addHandler(fileHandler);
			
		} catch (IOException | SecurityException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Put a message into info log
	 * @param message
	 */
	public static void log(String message) {
		sLogger.log(Level.INFO, message + "\n");
	}

}