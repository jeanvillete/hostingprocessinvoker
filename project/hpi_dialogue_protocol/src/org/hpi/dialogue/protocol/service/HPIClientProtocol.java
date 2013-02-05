/**
 * 
 */
package org.hpi.dialogue.protocol.service;

import java.io.IOException;
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
import org.hpi.dialogue.protocol.response.ServerRunningResponse;

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
	
	private Response writeRequestReadResponse(Request request) {
		this.validateStarting();
		try {
			this.getWriter().writeObject(request);
			return (Response) this.getReader().readObject();
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	private void validateStarting() {
		if (!this.isSocketPrepared()) {
			throw new IllegalStateException("The socket has not been started yet.");
		}
	}
	
	public boolean isConnected() {
		this.validateStarting();
		return this.getSocket().isConnected();
	}
	
	public ServerRunningResponse openConnection() {
		try {
			// initiating the socket
			this.prepareSocket(new Socket(this.serverAddress, this.portNumber));
			
			// return the current status of the server
			return (ServerRunningResponse) this.getReader().readObject();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void serverShutdown() {
		this.validateStarting();
		try {
			this.getWriter().writeObject(new ServerShutdownRequest());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public LoginResponse doLogin(String nickname, String passphrase) {
		return (LoginResponse) this.writeRequestReadResponse(new LoginRequest(nickname, passphrase));
	}
	
	public ListInvokersResponse listInvokers(String sessionId) {
		return (ListInvokersResponse) this.writeRequestReadResponse(new ListInvokersRequest(sessionId));
	}
	
	public ExecuteInvokerResponse executeInvoker(String sessionId, String invokeId) {
		return (ExecuteInvokerResponse) this.writeRequestReadResponse(new ExecuteInvokerRequest(sessionId, invokeId));
	}
	
	public LogoffResponse doLogoff(String sessionId) {
		return (LogoffResponse) this.writeRequestReadResponse(new LogoffRequest(sessionId));
	}
}
