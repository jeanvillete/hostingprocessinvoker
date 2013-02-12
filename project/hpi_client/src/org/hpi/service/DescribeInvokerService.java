package org.hpi.service;

import org.hpi.dialogue.protocol.entities.Executable;
import org.hpi.dialogue.protocol.entities.Parameter;
import org.hpi.dialogue.protocol.response.DescribeInvokerResponse;
import org.hpi.dialogue.protocol.response.Response;
import org.hpi.dialogue.protocol.service.HPIClientProtocol;
import org.hpi.service.command.Command;
import org.hpi.service.command.SessionedCommand;

public class DescribeInvokerService extends SessionedCommand implements Command {

	public static final String 		COMMAND = "-describe";
	
	private String					invoker;
	
	public DescribeInvokerService(String sessionId, String invoker) {
		super(sessionId);
		this.invoker = invoker.trim();
	}

	@Override
	public String execute(HPIClientProtocol clientProtocol) {
		// describe an invoker
		StringBuffer returning = new StringBuffer();
		DescribeInvokerResponse invokerResponse = clientProtocol.describeInvoker(this.getSessionId(), this.invoker);
		if (invokerResponse.getStatus().equals(Response.Status.SUCCESS)) {
			returning.append("\n");
			returning.append("\tid = " + invokerResponse.getInvoker().getId() + "\n");
			returning.append("\tdescription = " + invokerResponse.getInvoker().getDescription());
			for (Executable executable : invokerResponse.getInvoker().getExecutables()) {
				returning.append("\n");
				returning.append("\t\texecutable = " + executable.getCanonicalPath());
				for (Parameter parameter : executable.getParameters()) {
					returning.append("\n");
					returning.append("\t\t\tparameter key = " + parameter.getKey() + ", parameter value = " + parameter.getValue());
				}
			}
		} else if (invokerResponse.getStatus().equals(Response.Status.FAIL)) {
			return invokerResponse.getMessage();
		} else throw new IllegalStateException("Unkonw the server's status code response");
		
		return returning.toString();
	}

}
