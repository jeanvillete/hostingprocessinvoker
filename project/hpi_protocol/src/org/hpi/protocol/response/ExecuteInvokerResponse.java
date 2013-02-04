package org.hpi.protocol.response;

public class ExecuteInvokerResponse extends Response {

	public static final String						EXECUTE_INVOKER = "execute_invoker";
	
	public ExecuteInvokerResponse(String statusCode, String message) {
		super(EXECUTE_INVOKER, statusCode, message);
	}

}
