package org.hpi.protocol;

public interface HpiProtocolConstants {

	// Constants to Response messages
	String					RESPONSE_TITLE = "response";
	String					RESPONSE_GOAL = "goal";
	String					RESPONSE_STATUS_CODE = "status_code";
	String					RESPONSE_MESSAGE = "message";
	String					RESPONSE_LOGIN = "login";
	String					RESPONSE_LOGIN_SESSION_ID = "session_id";
	String					RESPONSE_INVOKERS = "list_invokers";
	
	// Constants to Operation messages
	String					OPERATION_TITLE = "operation_name";
	String					OPERATION_SESSION_ID = "session_id";
	String					OPERATION_DO_LOGIN_LOGIN = "login";
	String					OPERATION_DO_LOGIN_NICKNAME = "nickname";
	String					OPERATION_DO_LOGIN_PASSPHRASE = "passphrase";
	String					OPERATION_EXECUTE_INVOKER_ID_INVOKER = "passphrase";
}
