/**
 * 
 */
package org.hpi.dialogue.protocol.response;

/**
 * @author Jean Villete
 *
 */
public class LoginResponse extends Response {

	private static final long serialVersionUID = 4706991214044107045L;
	
	private String					sessionId;

	public LoginResponse(String sessionId, String message, Status status) {
		super(message, status);
		this.sessionId = sessionId;
	}

	// GETTERS AND SETTERS //
	public String getSessionId() {
		return sessionId;
	}
}
