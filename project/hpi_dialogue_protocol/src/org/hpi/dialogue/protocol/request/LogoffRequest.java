package org.hpi.dialogue.protocol.request;

public class LogoffRequest extends SessionedRequest {

	private static final long serialVersionUID = 7946461900230061913L;

	public LogoffRequest(String sessionId) {
		super(sessionId);
	}

}
