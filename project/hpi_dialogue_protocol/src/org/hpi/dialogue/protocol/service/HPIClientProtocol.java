/**
 * 
 */
package org.hpi.dialogue.protocol.service;

import java.net.Socket;

import org.hpi.dialogue.protocol.HPIDialogueProtocol;
import org.hpi.dialogue.protocol.entities.User;
import org.hpi.dialogue.protocol.request.DescribeInvokerRequest;
import org.hpi.dialogue.protocol.request.ExecuteInvokerRequest;
import org.hpi.dialogue.protocol.request.ListInvokersRequest;
import org.hpi.dialogue.protocol.request.LoginRequest;
import org.hpi.dialogue.protocol.request.LogoffRequest;
import org.hpi.dialogue.protocol.request.Request;
import org.hpi.dialogue.protocol.request.ServerShutdownRequest;
import org.hpi.dialogue.protocol.response.DescribeInvokerResponse;
import org.hpi.dialogue.protocol.response.ExecuteInvokerResponse;
import org.hpi.dialogue.protocol.response.ListInvokersResponse;
import org.hpi.dialogue.protocol.response.LoginResponse;
import org.hpi.dialogue.protocol.response.LogoffResponse;
import org.hpi.dialogue.protocol.response.Response;
import org.hpi.dialogue.protocol.response.ServerShutdownResponse;

/**
 * @author Jean Villete
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
	
	private Response writeAndGetResponse(Request request) {
		try {
			// initiating the socket
			this.setSocket(new Socket(this.serverAddress, this.portNumber));
			this.setWriter(this.getSocket().getOutputStream());
			
			this.writeAMessage(request.getSSDServiceMessage().toString(false));
			this.getWriter().flush();

			// initiating the reader and reading the response from the server
			this.setReader(this.getSocket().getInputStream());
			String serverMessage = this.readMessage();
			Response response = (Response) HPIDialogueProtocol.parseMessage(serverMessage);
			
			// closing the connections
			this.closeSocket();
			
			// return the retrieved response
			return response;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public ServerShutdownResponse serverShutdown() {
		return (ServerShutdownResponse) this.writeAndGetResponse(new ServerShutdownRequest());
	}
	
	public LoginResponse doLogin(User user) {
		return (LoginResponse) this.writeAndGetResponse(new LoginRequest(user));
	}
	
	public ListInvokersResponse listInvokers(String sessionId) {
		return (ListInvokersResponse) this.writeAndGetResponse(new ListInvokersRequest(sessionId));
	}
	
	public DescribeInvokerResponse describeInvoker(String sessionId, String invokeId) {
		return (DescribeInvokerResponse) this.writeAndGetResponse(new DescribeInvokerRequest(sessionId, invokeId));
	}
	
	public ExecuteInvokerResponse executeInvoker(String sessionId, String invokeId) {
		return (ExecuteInvokerResponse) this.writeAndGetResponse(new ExecuteInvokerRequest(sessionId, invokeId));
	}
	
	public LogoffResponse doLogoff(String sessionId) {
		return (LogoffResponse) this.writeAndGetResponse(new LogoffRequest(sessionId));
	}
}
