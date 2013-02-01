package org.hostingprocessinvoker.logging;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class HpiFormatter extends Formatter {

	// [LEVEL MESSAGE][DATE yyyyMMdd HHmmss] message
	
	private static final String				patternDate = "yyyy/MM/dd HH:mm:ss";
	private static final SimpleDateFormat 	dateFormat = new SimpleDateFormat(patternDate);
	
	@Override
	public String format(LogRecord record) {
		return "["+record.getLevel().toString()+"]" + 
				"[" + dateFormat.format(new Date(record.getMillis())) + "]" +
				record.getMessage() +
				"\n";
	}

}
