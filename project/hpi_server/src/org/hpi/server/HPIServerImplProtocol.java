/**
 * 
 */
package org.hpi.server;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.hpi.data.factoy.HPIDataFactory;
import org.hpi.dialogue.protocol.entities.Invoker;
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
import org.hpi.dialogue.protocol.service.HPIServerProtocol;
import org.hpi.exception.HPIExecuteInvokerException;
import org.hpi.exception.HPISessionException;
import org.hpi.server.session.HPISession;
import org.hpi.server.session.HPISessionManager;
import org.hpi.service.ExecuteInvoker;

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
		HPIServerProtocol serverProtocol = null;
		try {
			serverProtocol = new HPIServerProtocol(this.socket);
			Request clientRequest = serverProtocol.readRequest();
			Response response = null;

			// deciding the correct request
			if (clientRequest instanceof LoginRequest) { // login request
				response = this.doLogin((LoginRequest) clientRequest);
			} else if (clientRequest instanceof ListInvokersRequest) { // list invokers request
				response = this.retrieveListInvokers((ListInvokersRequest) clientRequest);
			} else if (clientRequest instanceof DescribeInvokerRequest) { // describe invoker request
				response = this.describeInvoker((DescribeInvokerRequest) clientRequest);
			} else if (clientRequest instanceof ExecuteInvokerRequest) { // execute invoker request
				response = this.executeInvoker((ExecuteInvokerRequest) clientRequest);
			} else if (clientRequest instanceof LogoffRequest) { // logoff request
				response = this.doLogoff((LogoffRequest) clientRequest);
			} else if (clientRequest instanceof ServerShutdownRequest) { // shutdown request
				new Thread() {
					@Override
					public void run() {
						try {
							Thread.sleep(ServerBridge.TIME_CHECK_SHUTDOWN);
							ServerBridge.SHUTDOWN = true;
						} catch (InterruptedException e) {
							throw new RuntimeException(e);
						}
					}
				}.start();
				response = new ServerShutdownResponse("Request to shutdown server received successfully and schedulled to execute in 5 seconds.", Response.Status.SUCCESS);
			} else throw new IllegalStateException("Request type unknown.");
			
			// writing the serialized response
			serverProtocol.writeResponse(response);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (serverProtocol != null) {
				serverProtocol.closeSocket();
			}
		}
	}
	
	/**
	 * Method responsible to manage the execution of the referenced invoker
	 * @param clientRequest
	 * @return
	 */
	private Response executeInvoker(ExecuteInvokerRequest clientRequest) {
		try {
			HPISessionManager sessionManager = HPISessionManager.getInstance();
			sessionManager.updateSession(clientRequest.getSessionId());
			
			HPIDataFactory dataFactory = HPIDataFactory.getInstance();
			Invoker invoker = dataFactory.getInvoker(clientRequest.getInvokeId());
			
			if (invoker != null) {
				ExecuteInvoker service = new ExecuteInvoker(invoker);
				String executionResult = service.runExecutables();
				return new ExecuteInvokerResponse(executionResult, Response.Status.SUCCESS);
			} else {
				return new ExecuteInvokerResponse("No invoker was found for the id: " + clientRequest.getInvokeId(), Response.Status.FAIL);
			}
		} catch (HPISessionException e) {
			return new ExecuteInvokerResponse("The session is not valid. " + e.getMessage(), Response.Status.FAIL);
		} catch (HPIExecuteInvokerException e) {
			return new ExecuteInvokerResponse("Error executing invoker. " + e.getMessage(), Response.Status.FAIL);
		}
	}

	/**
	 * 
	 * @param clientRequest
	 * @return
	 */
	private Response describeInvoker(DescribeInvokerRequest clientRequest) {
		Invoker invoker = null;
		try {
			HPISessionManager sessionManager = HPISessionManager.getInstance();
			sessionManager.updateSession(clientRequest.getSessionId());
			HPIDataFactory dataFactory = HPIDataFactory.getInstance();
			invoker = dataFactory.getInvoker(clientRequest.getInvokeId());
			if (invoker != null) {
				return new DescribeInvokerResponse(invoker, "Describe/consult invoker command executed successfully.", Response.Status.SUCCESS);
			} else {
				return new DescribeInvokerResponse(invoker, "No invoker was found for the id: " + clientRequest.getInvokeId(), Response.Status.FAIL);
			}
		} catch (HPISessionException e) {
			return new DescribeInvokerResponse(invoker, "The session is not valid. " + e.getMessage(), Response.Status.FAIL);
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
		User user = HPIDataFactory.getInstance().getUser(request.getUser().getNickname());
		if (user != null && user.getPassphrase().equals(request.getUser().getPassphrase())) {
			String remoteAddress = this.socket.getRemoteSocketAddress().toString();
			HPISession session = HPISessionManager.getInstance().newSession(user, remoteAddress);
			
			return new LoginResponse(session.getSession_id(), "User logged successfully.", Response.Status.SUCCESS);
		} else {
			return new LoginResponse("invalid session", "User not found or passphrase doesn't match.", Response.Status.FAIL);
		}
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	private ListInvokersResponse retrieveListInvokers(ListInvokersRequest request) {
		List<Invoker> listInvokers = null;
		try {
			HPISessionManager sessionManager = HPISessionManager.getInstance();
			sessionManager.updateSession(request.getSessionId());
			HPIDataFactory dataFactory = HPIDataFactory.getInstance();
			listInvokers = new ArrayList<Invoker>(dataFactory.getInvokers());
			return new ListInvokersResponse(listInvokers, "List invokers executed successfully.", Response.Status.SUCCESS);
		} catch (HPISessionException e) {
			return new ListInvokersResponse(listInvokers, "The session is not valid. " + e.getMessage(), Response.Status.FAIL);
		}
	}
}
