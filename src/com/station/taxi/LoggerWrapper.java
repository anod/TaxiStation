package com.station.taxi;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Filter;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Helper to create log
 * @author alex
 *
 */
public class LoggerWrapper {
	private static Logger sLogger = Logger.getLogger("TaxiStation");
	
	static {
		ConsoleHandler consoleHandler = new ConsoleHandler();
		consoleHandler.setFormatter(new TaxiFormatter());
		sLogger.setUseParentHandlers(false);
		sLogger.addHandler(consoleHandler);
		try {
			sLogger.addHandler(new FileHandler("StationLog.xml"));
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	class TaxiFormatter extends Formatter {
		final private SimpleDateFormat mDateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		@Override
		public String format(LogRecord record) {
			StringBuffer sb = new StringBuffer(1000);
			sb.append(mDateFormatter.format(new Date()));
			sb.append(" ");
			sb.append(record.getMessage());
			return sb.toString();
		}
		
	}
	
	/**
	 * Filters log messages for specific cab
	 * @author alex
	 *
	 */
	class CabFilter implements Filter {
		final private static String PATTERN = "[cab%d]";
		private int mCabId;
		
		public CabFilter(int cabId) {
			mCabId = cabId;
		}

		@Override
		public boolean isLoggable(LogRecord record) {
			String cabPattern = String.format(PATTERN, mCabId); 
			if (record.getMessage().contains(cabPattern)) {
				return true;
			}
			return false;
		}
	}
	
	/**
	 * Add a new Cab Logger
	 * @param cab
	 * @throws SecurityException
	 * @throws IOException
	 */
	public static void addCabLogger(Cab cab) throws SecurityException, IOException {
		FileHandler fileHandler = new FileHandler("CabLog"+cab.getNumer()+".txt");
		fileHandler.setFormatter(new SimpleFormatter());
		fileHandler.setFilter(new CabFilter(cab.getNumer()));
		sLogger.addHandler(fileHandler);
	}
	
	public static void log(String message) {
		sLogger.log(Level.INFO, message);
	}

}
