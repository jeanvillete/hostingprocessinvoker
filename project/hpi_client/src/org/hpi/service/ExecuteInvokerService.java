package org.hpi.service;

import org.com.tatu.helper.parameter.Parameter;
import org.hpi.dialogue.protocol.response.ExecuteInvokerResponse;
import org.hpi.dialogue.protocol.response.Response;
import org.hpi.dialogue.protocol.service.HPIClientProtocol;
import org.hpi.service.command.Command;
import org.hpi.service.command.SessionedCommand;

public class ExecuteInvokerService extends SessionedCommand implements Command {

	public static final String 		COMMAND = "-execute";
	
	private String					invoker;
	
	public ExecuteInvokerService(String sessionId, String invoker) {
		super(sessionId);
		Parameter.check(invoker).notNull().notEmpty();
		
		this.invoker = invoker.trim();
	}
	
	@Override
	public String execute(HPIClientProtocol clientProtocol) {
		// execute invoker
		ExecuteInvokerResponse executeResponse = clientProtocol.executeInvoker(this.getSessionId(), this.invoker);
		if (executeResponse.getStatus().equals(Response.Status.SUCCESS)) {
			return executeResponse.getMessage();
		} else if (executeResponse.getStatus().equals(Response.Status.FAIL)) {
			return executeResponse.getMessage();
		} else throw new IllegalStateException("Unkonw the server's status code response");
	}

}
