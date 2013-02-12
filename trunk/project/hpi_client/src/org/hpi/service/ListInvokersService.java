package org.hpi.service;

import java.util.List;

import org.com.tatu.helper.GeneralsHelper;
import org.hpi.dialogue.protocol.entities.Invoker;
import org.hpi.dialogue.protocol.response.ListInvokersResponse;
import org.hpi.dialogue.protocol.response.Response;
import org.hpi.dialogue.protocol.service.HPIClientProtocol;
import org.hpi.service.command.Command;
import org.hpi.service.command.SessionedCommand;

public class ListInvokersService extends SessionedCommand implements Command {

	public static final String COMMAND = "-list";
	
	public ListInvokersService(String sessionId) {
		super(sessionId);
	}

	@Override
	public String execute(HPIClientProtocol clientProtocol) {
		// list invokers
		StringBuffer returning = new StringBuffer();
		ListInvokersResponse invokersResponse = clientProtocol.listInvokers(this.getSessionId());
		if (invokersResponse.getStatus().equals(Response.Status.SUCCESS)) {
			List<Invoker> invokers = invokersResponse.getListInvokers();
			if (GeneralsHelper.isCollectionOk(invokers)) {
				for (Invoker invoker : invokers) {
					returning.append("\n");
					returning.append("\t" + invoker.getId());
				}
			} else {
				returning.append("The server has not found no invoker.");
			}
		} else if (invokersResponse.getStatus().equals(Response.Status.FAIL)) {
			returning.append(invokersResponse.getMessage());
		} else throw new IllegalStateException("Unkonw the server's status code response");
		
		return returning.toString();
	}

}
