package org.hpi.service;

import org.hpi.dialogue.protocol.response.LogoffResponse;
import org.hpi.dialogue.protocol.response.Response;
import org.hpi.dialogue.protocol.service.HPIClientProtocol;
import org.hpi.service.command.Command;
import org.hpi.service.command.SessionedCommand;

public class LogoffService extends SessionedCommand implements Command {

	public static final String COMMAND = "-exit";
	
	public LogoffService(String sessionId) {
		super(sessionId);
	}

	@Override
	public String execute(HPIClientProtocol clientProtocol) {
		// do logoff
		LogoffResponse logoffResponse = clientProtocol.doLogoff(this.getSessionId());
		if (logoffResponse.getStatus().equals(Response.Status.SUCCESS)) {
			return logoffResponse.getMessage();
		} else if (logoffResponse.getStatus().equals(Response.Status.FAIL)) {
			return logoffResponse.getMessage();
		} else throw new IllegalStateException("Unkonw the server's status code response");
	}

}
