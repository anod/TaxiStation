package com.station.taxi.logger;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.station.taxi.Cab;

/**
 * Helper to create log
 * @author alex
 *
 * TODO add passanger loger
 *
 */
public class LoggerWrapper {
	private static final String LOG_CAB = "logs/CabLog_%s.txt";
	private static Logger sLogger = Logger.getLogger("logs/TaxiStation");
	
	static {
		ConsoleHandler consoleHandler = new ConsoleHandler();
		consoleHandler.setFormatter(new TaxiFormatter());
		sLogger.setUseParentHandlers(false);
		sLogger.addHandler(consoleHandler);
		try {
			sLogger.addHandler(new FileHandler("logs/StationLog.txt"));
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Add a new Cab Logger
	 * @param cab
	 * @throws SecurityException
	 * @throws IOException
	 */
	public static void addCabLogger(Cab cab) throws SecurityException, IOException {
		FileHandler fileHandler = new FileHandler(String.format(LOG_CAB, cab.getNumer()));
		fileHandler.setFormatter(new SimpleFormatter());
		fileHandler.setFilter(new CabFilter(cab.getNumer()));
		sLogger.addHandler(fileHandler);
	}
	
	public static void log(String message) {
		sLogger.log(Level.INFO, message);
	}

}