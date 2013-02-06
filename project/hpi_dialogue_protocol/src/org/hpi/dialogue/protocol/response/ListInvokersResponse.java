package org.hpi.dialogue.protocol.response;

import java.util.List;

import org.hpi.dialogue.protocol.entities.Invoker;

public class ListInvokersResponse extends Response {

	private static final long serialVersionUID = -5143130097598698256L;
	
	private List<Invoker>					listInvokers;
	
	public ListInvokersResponse(List<Invoker> listInvokers, String message, Status status) {
		super(message, status);
		this.listInvokers = listInvokers;
	}

	// GETTERS AND SETTERS //
	public List<Invoker> getListInvokers() {
		return listInvokers;
	}

}
