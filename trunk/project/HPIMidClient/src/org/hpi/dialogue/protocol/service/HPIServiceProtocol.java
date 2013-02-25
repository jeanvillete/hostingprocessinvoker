/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpi.dialogue.protocol.service;

import javax.microedition.io.SocketConnection;
import java.io.*;
import org.hpi.dialogue.protocol.common.HPIDialogueConstants;
import org.hpi.dialogue.protocol.common.HPIUtil;

/**
 *
 * @author carrefour
 */
public abstract class HPIServiceProtocol {
	
    private SocketConnection			socket;
    private InputStream				reader;
    private OutputStream			writer;

    public void closeSocket() {
        if (this.reader != null) try { this.reader.close(); } catch (IOException e) { throw new RuntimeException(e.getMessage()); }
        if (this.writer != null) try { this.writer.close(); } catch (IOException e) { throw new RuntimeException(e.getMessage()); }
        if (this.socket != null) try { this.socket.close(); } catch (IOException e) { throw new RuntimeException(e.getMessage()); }
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
        
        String message = sb.toString();
        message = HPIUtil.StringReplace(message, HPIDialogueConstants.BEGIN_TOKEN_MESSAGE);
        message = HPIUtil.StringReplace(message, HPIDialogueConstants.END_TOKEN_MESSAGE);

        return message;
    }

    // GETTERS AND SETTERS //
    protected SocketConnection getSocket() {
        return socket;
    }
    protected InputStream getReader() {
        return reader;
    }
    protected OutputStream getWriter() {
        return writer;
    }
    protected void setSocket(SocketConnection socket) {
        this.socket = socket;
    }
    protected void setReader(InputStream reader) {
        this.reader = reader;
    }
    protected void setWriter(OutputStream writer) {
        this.writer = writer;
    }
}
