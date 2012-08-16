package com.station.taxi.logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * Format dates according to exercise
 * @author alex
 *
 */
class StationFormatter extends Formatter {
	final private SimpleDateFormat mDateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	@Override
	public String format(LogRecord record) {
		StringBuilder sb = new StringBuilder(1000);
		sb.append(mDateFormatter.format(new Date()));
		sb.append(" ");
		sb.append(record.getMessage());
		return sb.toString();
	}
	
}
