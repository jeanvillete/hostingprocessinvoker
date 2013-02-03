package org.hpi.logging;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.logging.ErrorManager;
import java.util.logging.Filter;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

import org.com.tatu.helper.StringHelper;

public class FileHandler extends Handler {

	private String date = "";

	private String directory = null;
	
	private String prefix = null;
	
	private String suffix = null;
	
	private PrintWriter writer = null;
	
	public FileHandler() {
		this(null, null, null);
	}
	
	public FileHandler(String directory, String prefix, String suffix) {
		this.directory = directory;
		this.prefix = prefix;
		this.suffix = suffix;
		configure();
		open();
	}

	private void configure() {
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		String tsString = ts.toString().substring(0, 19);
		this.date = tsString.substring(0, 10);
	
		String className = FileHandler.class.getName();
	
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
	
		if (this.directory == null)
			this.directory = getProperty(className + ".directory", "logs");
		
		if (this.prefix == null)
			this.prefix = getProperty(className + ".prefix", "hpi.");
		
		if (this.suffix == null) {
			this.suffix = getProperty(className + ".suffix", ".log");
		}
	
		setLevel(Level.parse(getProperty(className + ".level", "" + Level.ALL)));
	
		String filterName = getProperty(className + ".filter", null);
		
		if (filterName != null) {
			try {
				setFilter((Filter)cl.loadClass(filterName).newInstance());
			} catch (Exception e) { }
		}
	
		String formatterName = getProperty(className + ".formatter", null);
		if (formatterName != null)
			try {
				setFormatter((Formatter)cl.loadClass(formatterName).newInstance());
			} catch (Exception e) { }
		else {
			setFormatter(new SimpleFormatter());
		}
	
		setErrorManager(new ErrorManager());
	}
	
	private void open() {
		File dir = new File(StringHelper.replaceEnvironmentVariable(this.directory));
		dir.mkdirs();
		try {
			String pathname = dir.getAbsolutePath() + File.separator + this.prefix + this.date + this.suffix;
		
			this.writer = new PrintWriter(new FileWriter(pathname, true), true);
			this.writer.write(getFormatter().getHead(this));
		} catch (Exception e) {
			reportError(null, e, 4);
			this.writer = null;
		}
	}
	
	private String getProperty(String name, String defaultValue) {
		String value = LogManager.getLogManager().getProperty(name);
		if (value == null)
			value = defaultValue;
		else {
			value = value.trim();
		}
		return value;
	}
	
	@Override
	public void publish(LogRecord record) {
		if (!isLoggable(record)) {
			return;
		}

		Timestamp ts = new Timestamp(System.currentTimeMillis());
		String tsString = ts.toString().substring(0, 19);
		String tsDate = tsString.substring(0, 10);

		if (!this.date.equals(tsDate)) {
			synchronized (this) {
				if (!this.date.equals(tsDate)) {
					close();
					this.date = tsDate;
					open();
				}
			}
		}

		String result = null;
		try {
			result = getFormatter().format(record);
		} catch (Exception e) {
			reportError(null, e, 5);
			return;
		}
		try {
			this.writer.write(result);
			this.writer.flush();
		} catch (Exception e) {
			reportError(null, e, 1);
			return;
		}
	}

	@Override
	public void flush() {
		try {
		  this.writer.flush();
		} catch (Exception e) {
		  reportError(null, e, 2);
		}
	}

	@Override
	public void close() throws SecurityException {
		try {
			if (this.writer == null)
				return;
			
			this.writer.write(getFormatter().getTail(this));
			this.writer.flush();
			this.writer.close();
			this.writer = null;
			
			this.date = "";
		} catch (Exception e) {
			reportError(null, e, 3);
		}
	}

}
