package org.hpi.protocol.response;

import org.hpi.protocol.HpiProtocolConstants;
import org.simplestructruedata.data.SSDContextManager;
import org.simplestructruedata.data.SSDContextManager.SSDRootObject;
import org.simplestructruedata.entities.SSDObjectLeaf;
import org.simplestructruedata.entities.SSDObjectNode;

public class LoginResponse extends Response {

	public static final String					LOGIN = "login";
	
	private String								session_id;
	
	public LoginResponse(String statusCode, String message, String session_id) {
		super(LOGIN, statusCode, message);
		this.session_id = session_id;
	}
	
	@Override
	public String toString() {
		SSDContextManager ssdCtx = this.getSSDCtx();
		SSDRootObject root = ssdCtx.getRootObject();
		SSDObjectNode login = new SSDObjectNode(HpiProtocolConstants.RESPONSE_LOGIN);
		login.addAttribute(new SSDObjectLeaf(HpiProtocolConstants.RESPONSE_LOGIN_SESSION_ID, this.session_id));
		root.addAttribute(login);
		
		return ssdCtx.toString();
	}

	// GETTERS AND SETTERS //
	public String getSession_id() {
		return session_id;
	}

}
