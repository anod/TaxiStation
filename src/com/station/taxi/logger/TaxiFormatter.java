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
