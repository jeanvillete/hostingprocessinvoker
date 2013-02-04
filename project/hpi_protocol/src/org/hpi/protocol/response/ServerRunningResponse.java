package org.hpi.protocol.response;

public class ServerRunningResponse extends Response {

	public static final String				SERVER_RUNNING = "server_running";
	
	public ServerRunningResponse(String statusCode, String message) {
		super(SERVER_RUNNING, statusCode, message);
	}

}
