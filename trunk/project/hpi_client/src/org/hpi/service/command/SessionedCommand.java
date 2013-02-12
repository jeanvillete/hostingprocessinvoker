package org.hpi.service.command;

import org.com.tatu.helper.parameter.Parameter;

public abstract class SessionedCommand {

	private String			sessionId;

	public SessionedCommand(String sessionId) {
		super();
		Parameter.check(sessionId).notNull().notEmpty();
		
		this.sessionId = sessionId;
	}

	protected String getSessionId() {
		return sessionId;
	}
	
}
