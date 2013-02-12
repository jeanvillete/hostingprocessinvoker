package org.hpi.service;

import org.hpi.dialogue.protocol.response.Response;
import org.hpi.dialogue.protocol.response.ServerShutdownResponse;
import org.hpi.dialogue.protocol.service.HPIClientProtocol;
import org.hpi.service.command.Command;

public class ServerShutdownService implements Command {

	public static final String COMMAND = "-shutdown";
	
	public ServerShutdownService() {
	}

	@Override
	public String execute(HPIClientProtocol clientProtocol) {
		// invoking shutdown
		ServerShutdownResponse shutdownResponse = clientProtocol.serverShutdown();
		if (shutdownResponse.getStatus().equals(Response.Status.SUCCESS)) {
			return shutdownResponse.getMessage();
		} else if (shutdownResponse.getStatus().equals(Response.Status.FAIL)) {
			throw new RuntimeException(shutdownResponse.getMessage());
		} else throw new IllegalStateException("Unkonw the server's status code response");
	}

}
