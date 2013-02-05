package org.hpi.dialogue.protocol.request;

public abstract class SessionedRequest extends Request {

	private static final long serialVersionUID = 8977087273850942176L;
	
	private String					sessionId;

	public SessionedRequest(String sessionId) {
		super();
		this.sessionId = sessionId;
	}

	// GETTERS AND SETTESRS //
	public String getSessionId() {
		return sessionId;
	}
	
}
