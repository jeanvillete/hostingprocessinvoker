package org.hpi.dialogue.protocol.service;

import java.net.Socket;

import org.hpi.dialogue.protocol.HPIDialogueProtocol;
import org.hpi.dialogue.protocol.request.Request;
import org.hpi.dialogue.protocol.response.Response;

public class HPIServerProtocol extends HPIServiceProtocol {

	public HPIServerProtocol(Socket clientSocket) {
		try {
			// create socket
			this.setSocket(clientSocket);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public Request readRequest() {
		try {
			// initiating the reader
			this.setReader(this.getSocket().getInputStream());
			String clientMessage = this.readMessage();

			// reading the serialized object
			return (Request) HPIDialogueProtocol.parseMessage(clientMessage);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void writeResponse(Response response) {
		try {
			// initiating the writer
			this.setWriter(this.getSocket().getOutputStream());
			
			// printing the serialized object
			this.writeAMessage(response.getSSDServiceMessage().toString(false));
			
			// releasing the stream flush
			this.getWriter().flush();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
