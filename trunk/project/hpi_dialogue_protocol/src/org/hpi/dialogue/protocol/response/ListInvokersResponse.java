package org.hpi.dialogue.protocol.response;

import java.util.List;

public class ListInvokersResponse extends Response {

	private static final long serialVersionUID = 1794369418580495583L;
	
	private List<String>					listInvokersId;
	
	public ListInvokersResponse(List<String> listInvokersId, String message, Status status) {
		super(message, status);
		this.listInvokersId = listInvokersId;
	}

	// GETTERS AND SETTERS //
	public List<String> getListInvokersId() {
		return listInvokersId;
	}

}
