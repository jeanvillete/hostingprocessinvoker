package org.hpi.dialogue.protocol.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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
			if (this.getReader() == null) {
				BufferedInputStream bufferedInput = new BufferedInputStream(this.getSocket().getInputStream());
				this.setReader(new ObjectInputStream(bufferedInput));
			}

			// reading the serialized object
			return (Request) this.getReader().readObject();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void writeResponse(Response response) {
		try {
			// initiating the writer
			if (this.getWriter() == null) {
				BufferedOutputStream bufferedOutput = new BufferedOutputStream(this.getSocket().getOutputStream());
				this.setWriter(new ObjectOutputStream(bufferedOutput));
			}
			
			// printing the serialized object
			this.getWriter().writeObject(response);
			
			// releasing the stream flush
			this.getWriter().flush();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
