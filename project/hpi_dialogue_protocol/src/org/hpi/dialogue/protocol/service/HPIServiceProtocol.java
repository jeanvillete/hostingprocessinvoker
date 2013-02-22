/**
 * 
 */
package org.hpi.dialogue.protocol.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.hpi.dialogue.protocol.common.HPIDialogueConstants;

/**
 * @author Jean Villete
 *
 */
public abstract class HPIServiceProtocol {
	
	private Socket					socket;
	private InputStream				reader;
	private OutputStream			writer;

	public void closeSocket() {
		if (this.reader != null) try { this.reader.close(); } catch (IOException e) { throw new RuntimeException(e); }
		if (this.writer != null) try { this.writer.close(); } catch (IOException e) { throw new RuntimeException(e); }
		if (this.socket != null) try { this.socket.close(); } catch (IOException e) { throw new RuntimeException(e); }
	}
	
	public void writeAMessage(String message) throws IOException {
		//print begin token message
		this.writer.write(HPIDialogueConstants.BYTE_BEGIN_TOKEN_MESSAGE);
		//print request message
		this.writer.write(message.getBytes());
		//print end token message
		this.writer.write(HPIDialogueConstants.BYTE_END_TOKEN_MESSAGE);
	}
	
	public String readMessage() throws IOException {
		StringBuffer sb = new StringBuffer();
        int character = 0;
        do {
        	character = this.reader.read();
        	if (character == -1) {
        		throw new RuntimeException("Unexpected broken connection or invalid character value!");
        	}
        	sb.append((char) character);
        } while (!sb.toString().endsWith(HPIDialogueConstants.END_TOKEN_MESSAGE));
        
        return sb.toString().replace(HPIDialogueConstants.BEGIN_TOKEN_MESSAGE, "").replace(HPIDialogueConstants.END_TOKEN_MESSAGE, "");
	}
	
	// GETTERS AND SETTERS //
	protected Socket getSocket() {
		return socket;
	}
	protected InputStream getReader() {
		return reader;
	}
	protected OutputStream getWriter() {
		return writer;
	}
	protected void setSocket(Socket socket) {
		this.socket = socket;
	}
	protected void setReader(InputStream reader) {
		this.reader = reader;
	}
	protected void setWriter(OutputStream writer) {
		this.writer = writer;
	}
}
