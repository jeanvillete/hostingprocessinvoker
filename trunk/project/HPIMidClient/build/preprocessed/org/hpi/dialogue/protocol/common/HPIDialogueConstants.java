package org.hpi.dialogue.protocol.common;

public interface HPIDialogueConstants {

	String 				BEGIN_TOKEN_MESSAGE = ":bm:";
	String 				END_TOKEN_MESSAGE = ":em:";
	byte[] 				BYTE_BEGIN_TOKEN_MESSAGE = BEGIN_TOKEN_MESSAGE.getBytes();
	byte[] 				BYTE_END_TOKEN_MESSAGE = END_TOKEN_MESSAGE.getBytes();
	
	String 				RESPONSE_SERVICE = "response";
	String 				REQUEST_SERVICE = "request";

	String 				LOGIN_SERVICE = "login";
	String 				DESCRIBE_INVOKER_SERVICE = "describeInvoker";
	String 				EXECUTE_INVOKER_SERVICE = "executeInvoker";
	String 				LIST_INVOKERS_SERVICE = "listInvokers";
	String				LOGOFF_SERVICE = "logoff";
	String				SERVER_SHUTDOWN_SERVICE = "serverShutdown";
	
	String 				HPI_MESSAGE = "hpi_message";
	String 				SERVICE_TYPE = "service_type";
	String 				SERVICE_NAME = "service_name";
	String 				MESSAGE = "message";
	String 				STATUS = "status";
	
	String 				PARAMETERS = "parameters";
	String 				PARAMETER = "parameter";
	String 				KEY = "key";
	String 				VALUE = "value";
	
	String 				NICKNAME = "nickname";
	String 				PASSPHRASE = "passphrase";

	String 				SESSION_ID = "session_id";
	String 				INVOKER_ID = "invoker_id";

	String 				LIST_INVOKERS = "list_invokers";
	String 				ID = "id";
	String 				DESCRIPTION = "description";
	String 				EXECUTABLES = "executables";
	String 				EXECUTABLE = "executable";
	String 				CANONICAL_PATH = "canonical_path";
	String 				INVOKER = "invoker";
	
}
