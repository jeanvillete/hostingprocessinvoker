/**
 * 
 */
package org.hpi.dialogue.protocol.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author villjea
 *
 */
public abstract class HPIServiceProtocol {
	
	private Socket					socket;
	private ObjectInputStream		reader;
	private ObjectOutputStream		writer;
	private boolean					prepareSocketCalled = false;

	protected void prepareSocket(Socket socket) {
		if (this.prepareSocketCalled) {
			throw new IllegalStateException("This method can me invoked just one time per instance.");
		} else if (socket == null) {
			throw new IllegalArgumentException("Argument socket cann't be null");
		}
		
		try {
			this.prepareSocketCalled = true;
			this.socket = socket;
			
			// initiating the reader
			BufferedInputStream bufferedInput = new BufferedInputStream(socket.getInputStream());
			this.reader = new ObjectInputStream(bufferedInput);
			
			// initiating the writer
			BufferedOutputStream bufferedOutput = new BufferedOutputStream(socket.getOutputStream());
			this.writer = new ObjectOutputStream(bufferedOutput);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void closeSocket() {
		try {
			this.reader.close();
			this.writer.close();
			this.socket.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public boolean isSocketPrepared() {
		return this.prepareSocketCalled;
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
}
