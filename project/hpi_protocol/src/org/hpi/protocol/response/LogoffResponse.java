package org.hpi.protocol.response;

public class LogoffResponse extends Response {

	public static final String				LOGOFF = "logoff";
	
	public LogoffResponse(String statusCode, String message) {
		super(LOGOFF, statusCode, message);
	}

}
