/**
 * 
 */
package org.hpi.server;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.hpi.data.factoy.HPIDataFactory;
import org.hpi.dialogue.protocol.request.ExecuteInvokerRequest;
import org.hpi.dialogue.protocol.request.ListInvokersRequest;
import org.hpi.dialogue.protocol.request.LoginRequest;
import org.hpi.dialogue.protocol.request.LogoffRequest;
import org.hpi.dialogue.protocol.request.Request;
import org.hpi.dialogue.protocol.request.ServerShutdownRequest;
import org.hpi.dialogue.protocol.response.ListInvokersResponse;
import org.hpi.dialogue.protocol.response.LoginResponse;
import org.hpi.dialogue.protocol.response.LogoffResponse;
import org.hpi.dialogue.protocol.response.Response;
import org.hpi.dialogue.protocol.response.ServerRunningResponse;
import org.hpi.dialogue.protocol.service.HPIServerProtocol;
import org.hpi.entities.Invoker;
import org.hpi.entities.User;
import org.hpi.exception.HPISessionException;
import org.hpi.server.session.HPISession;
import org.hpi.server.session.HPISessionManager;

/**
 * @author Jean Villete
 *
 */
class HPIServerImplProtocol extends Thread {

	private Socket			socket = null;
	
	HPIServerImplProtocol(Socket socket) {
		super("HostingInvokerProtocol");
		this.socket = socket;
	}
	
	@Override
	public void run() {
		HPIServerProtocol serverProtocol = new HPIServerProtocol(this.socket);
		try {
			
			// print the first response that the server is alive
			serverProtocol.writeResponse(new ServerRunningResponse("service alive and ready to respond", Response.Status.SUCCESS));
			
			Request clientRequest = null;
			while ((clientRequest = serverProtocol.readRequest()) != null) {
				if (clientRequest instanceof LoginRequest) { // login request
					serverProtocol.writeResponse(this.doLogin((LoginRequest) clientRequest));
				} else if (clientRequest instanceof ListInvokersRequest) { // list invokers request
					serverProtocol.writeResponse(this.retrieveListInvokers((ListInvokersRequest) clientRequest));
				} else if (clientRequest instanceof ExecuteInvokerRequest) { // execute invoker request
					// TODO
				} else if (clientRequest instanceof LogoffRequest) { // logoff request
					this.doLogoff((LogoffRequest) clientRequest);
				} else if (clientRequest instanceof ServerShutdownRequest) { // shutdown request
					ServerBridge.SHUTDOWN = true;
					break;
				} else throw new IllegalStateException("Request type unknown.");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			serverProtocol.closeSocket();
		}
	}

	/**
	 * Method to remove the session of the Session Manager explicitly
	 * @param operation
	 * @return
	 */
	private LogoffResponse doLogoff(LogoffRequest operation) {
		HPISessionManager sessionManager = HPISessionManager.getInstance();
		if (sessionManager.deleteSession(operation.getSessionId())) {
			return new LogoffResponse("The session was removed successfully.", Response.Status.SUCCESS);
		} else {
			return new LogoffResponse("No session was found to remove.", Response.Status.FAIL);
		}
	}

	/**
	 * Implementing the service do login and returning the result of it
	 * @param request
	 * @return
	 */
	private LoginResponse doLogin(LoginRequest request) {
		User user = HPIDataFactory.getInstance().getUser(request.getNickname());
		if (user != null && user.getPassphrase().equals(request.getPassphrase())) {
			String remoteAddress = this.socket.getRemoteSocketAddress().toString();
			HPISession session = HPISessionManager.getInstance().newSession(user, remoteAddress);
			
			return new LoginResponse(session.getSession_id(), "User logged successfully.", Response.Status.SUCCESS);
		} else {
			return new LoginResponse("invalid session", "User not found or passphrase doesn't match.", Response.Status.FAIL);
		}
	}
	
	/**
	 * 
	 * @param operation
	 * @return
	 */
	private ListInvokersResponse retrieveListInvokers(ListInvokersRequest operation) {
		List<String> listInvokers = new ArrayList<String>();
		try {
			HPISessionManager sessionManager = HPISessionManager.getInstance();
			sessionManager.updateSession(operation.getSessionId());
			HPIDataFactory dataFactory = HPIDataFactory.getInstance();
			for (Invoker invoker : dataFactory.getInvokers()) {
				listInvokers.add("id: " + invoker.getId() + ", description: " + invoker.getDescription());
			}
			return new ListInvokersResponse(listInvokers, "List invokers executed successfully.", Response.Status.SUCCESS);
		} catch (HPISessionException e) {
			return new ListInvokersResponse(listInvokers, "The session is not valid. " + e.getMessage(), Response.Status.FAIL);
		}
	}
}
