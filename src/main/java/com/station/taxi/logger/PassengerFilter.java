package com.station.taxi.logger;

import java.util.logging.Filter;
import java.util.logging.LogRecord;

/**
 * Filters log messages for passenger
 * @author Eran Zimbler
 */
class PassengerFilter implements Filter {
	final public static String PATTERN = "%s -";
	private String mPassengerName;
	
	public PassengerFilter(String mPassengerName) {
		this.mPassengerName = mPassengerName;
	}

	@Override
	public boolean isLoggable(LogRecord arg0) {
		String pattern = String.format(PATTERN, mPassengerName); 
		if (arg0.getMessage().contains(pattern)) {
			return true;
		}
		return false;
	}

}
