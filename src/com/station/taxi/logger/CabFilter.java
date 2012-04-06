package com.station.taxi.logger;

import java.util.logging.Filter;
import java.util.logging.LogRecord;

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
