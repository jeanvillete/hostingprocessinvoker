package org.hpi.service.command;

import org.hpi.dialogue.protocol.service.HPIClientProtocol;

public interface Command {

	String execute(HPIClientProtocol clientProtocol);
	
}
