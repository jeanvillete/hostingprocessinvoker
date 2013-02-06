/**
 * 
 */
package org.hpi.dialogue.protocol.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.hpi.dialogue.protocol.request.ExecuteInvokerRequest;
import org.hpi.dialogue.protocol.request.ListInvokersRequest;
import org.hpi.dialogue.protocol.request.LoginRequest;
import org.hpi.dialogue.protocol.request.LogoffRequest;
import org.hpi.dialogue.protocol.request.Request;
import org.hpi.dialogue.protocol.request.ServerShutdownRequest;
import org.hpi.dialogue.protocol.response.ExecuteInvokerResponse;
import org.hpi.dialogue.protocol.response.ListInvokersResponse;
import org.hpi.dialogue.protocol.response.LoginResponse;
import org.hpi.dialogue.protocol.response.LogoffResponse;
import org.hpi.dialogue.protocol.response.Response;
import org.hpi.dialogue.protocol.response.ServerShutdownResponse;

/**
 * @author villjea
 *
 */
public class HPIClientProtocol extends HPIServiceProtocol {
	
	private String					serverAddress;
	private int 					portNumber;
	
	public HPIClientProtocol(String serverAddress, int portNumber) {
		super();
		this.serverAddress = serverAddress;
		this.portNumber = portNumber;
	}
	
	private Response doWriteAndGetResponse(Request request) {
		try {
			// initiating the socket
			this.setSocket(new Socket(this.serverAddress, this.portNumber));
			
			// initiating the writer
			BufferedOutputStream bufferedOutput = new BufferedOutputStream(this.getSocket().getOutputStream());
			this.setWriter(new ObjectOutputStream(bufferedOutput));
			this.getWriter().writeObject(request);
			this.getWriter().flush();

			// initiating the reader
			BufferedInputStream bufferedInput = new BufferedInputStream(this.getSocket().getInputStream());
			this.setReader(new ObjectInputStream(bufferedInput));
			
			// deserializing the object from server
			Response response = (Response) this.getReader().readObject();
			
			// closing the connections
			this.closeSocket();
			
			// return the retrieved response
			return response;
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	public ServerShutdownResponse serverShutdown() {
		return (ServerShutdownResponse) this.doWriteAndGetResponse(new ServerShutdownRequest());
	}
	
	public LoginResponse doLogin(String nickname, String passphrase) {
		return (LoginResponse) this.doWriteAndGetResponse(new LoginRequest(nickname, passphrase));
	}
	
	public ListInvokersResponse listInvokers(String sessionId) {
		return (ListInvokersResponse) this.doWriteAndGetResponse(new ListInvokersRequest(sessionId));
	}
	
	public ExecuteInvokerResponse executeInvoker(String sessionId, String invokeId) {
		return (ExecuteInvokerResponse) this.doWriteAndGetResponse(new ExecuteInvokerRequest(sessionId, invokeId));
	}
	
	public LogoffResponse doLogoff(String sessionId) {
		return (LogoffResponse) this.doWriteAndGetResponse(new LogoffRequest(sessionId));
	}
}
