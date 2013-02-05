package org.hpi.dialogue.protocol.service;

import java.net.Socket;

import org.hpi.dialogue.protocol.request.Request;
import org.hpi.dialogue.protocol.response.Response;

public class HPIServerProtocol extends HPIServiceProtocol {

	public HPIServerProtocol(Socket clientSocket) {
		this.prepareSocket(clientSocket);
	}
	
	public Request readRequest() {
		try {
			return (Request) this.getReader().readObject();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void writeResponse(Response response) {
		try {
			this.getWriter().writeObject(response);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
