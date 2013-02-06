/**
 * 
 */
package org.hpi.dialogue.protocol.service;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author Jean Villete
 *
 */
public abstract class HPIServiceProtocol {
	
	private Socket					socket;
	private ObjectInputStream		reader;
	private ObjectOutputStream		writer;

	public void closeSocket() {
		try {
			if (this.reader != null) this.reader.close();
			if (this.writer != null) this.writer.close();
			if (this.socket != null) this.socket.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	// GETTERS AND SETTERS //
	protected Socket getSocket() {
		return socket;
	}
	protected ObjectInputStream getReader() {
		return reader;
	}
	protected ObjectOutputStream getWriter() {
		return writer;
	}
	protected void setSocket(Socket socket) {
		this.socket = socket;
	}
	protected void setReader(ObjectInputStream reader) {
		this.reader = reader;
	}
	protected void setWriter(ObjectOutputStream writer) {
		this.writer = writer;
	}
}
