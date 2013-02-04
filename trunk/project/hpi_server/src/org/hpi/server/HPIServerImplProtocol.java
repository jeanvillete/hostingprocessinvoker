/**
 * 
 */
package org.hpi.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.hpi.data.factoy.HPIDataFactory;
import org.hpi.entities.Invoker;
import org.hpi.entities.User;
import org.hpi.exception.HPISessionException;
import org.hpi.protocol.operation.ExecuteInvokerOperation;
import org.hpi.protocol.operation.ListInvokersOperation;
import org.hpi.protocol.operation.LoginOperation;
import org.hpi.protocol.operation.LogoffOperation;
import org.hpi.protocol.operation.Operation;
import org.hpi.protocol.operation.ShutdownServerOperation;
import org.hpi.protocol.response.ListInvokersResponse;
import org.hpi.protocol.response.LoginResponse;
import org.hpi.protocol.response.LogoffResponse;
import org.hpi.protocol.response.Response;
import org.hpi.protocol.response.ServerRunningResponse;
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
		PrintWriter writer = null;
		BufferedReader reader = null;
		try {
			writer = new PrintWriter(this.socket.getOutputStream(), true);
			reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			
			String inputString = null;
			
			// print the first response that the server is alive
			ServerRunningResponse serverUp = new ServerRunningResponse(Response.STATUS_CODE_SUCCESS, "service alive and ready to respond");
			writer.println(serverUp.toString());
			
			while ((inputString = reader.readLine()) != null) {
				Operation operation = Operation.build(inputString);
				
				if (operation instanceof LoginOperation) {
					LoginResponse loginResponse = this.doLogin((LoginOperation) operation); 
					writer.println(loginResponse.toString());
				} else if (operation instanceof ListInvokersOperation) {
					ListInvokersResponse invokersResponse = this.retrieveListInvokers((ListInvokersOperation) operation);
					writer.println(invokersResponse.toString());
				} else if (operation instanceof ExecuteInvokerOperation) {
					// TODO
				} else if (operation instanceof LogoffOperation) {
					LogoffResponse logoffResponse = this.doLogoff((LogoffOperation)operation);
					writer.println(logoffResponse.toString());
				} else if (operation instanceof ShutdownServerOperation) {
					ServerBridge.SHUTDOWN = true;
				} else throw new IllegalStateException("Unkonw the operation. operation name: " + operation.getOperationName());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (reader != null) reader.close();
				if (writer != null) writer.close();
				if (this.socket != null) this.socket.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * Method to remove the session of the Session Manager explicitly
	 * @param operation
	 * @return
	 */
	private LogoffResponse doLogoff(LogoffOperation operation) {
		HPISessionManager sessionManager = HPISessionManager.getInstance();
		if (sessionManager.deleteSession(operation.getSessionId())) {
			return new LogoffResponse(Response.STATUS_CODE_SUCCESS, "The session was removed successfully.");
		} else {
			return new LogoffResponse(Response.STATUS_CODE_FAIL, "No session was found to remove.");
		}
	}

	/**
	 * Implementing the service do login and returning the result of it
	 * @param operation
	 * @return
	 */
	private LoginResponse doLogin(LoginOperation operation) {
		User user = HPIDataFactory.getInstance().getUser(operation.getNickname());
		if (user != null && user.getPassphrase().equals(operation.getPassphrase())) {
			String remoteAddress = this.socket.getRemoteSocketAddress().toString();
			HPISession session = HPISessionManager.getInstance().newSession(user, remoteAddress);
			return new LoginResponse(Response.STATUS_CODE_SUCCESS, "User logged successfully.", session.getSession_id());
		} else {
			return new LoginResponse(Response.STATUS_CODE_FAIL, "User not found or passphrase doesn't match.", "invalid session");
		}
	}
	
	/**
	 * 
	 * @param operation
	 * @return
	 */
	private ListInvokersResponse retrieveListInvokers(ListInvokersOperation operation) {
		try {
			HPISessionManager sessionManager = HPISessionManager.getInstance();
			sessionManager.updateSession(operation.getSessionId());
			HPIDataFactory dataFactory = HPIDataFactory.getInstance();
			List<String> listInvokers = new ArrayList<String>();
			for (Invoker invoker : dataFactory.getInvokers()) {
				listInvokers.add("id: " + invoker.getId() + ", description: " + invoker.getDescription());
			}
			return new ListInvokersResponse(Response.STATUS_CODE_SUCCESS, "List invokers executed successfully.", listInvokers);
		} catch (HPISessionException e) {
			return new ListInvokersResponse(Response.STATUS_CODE_FAIL, "The session is not valid. " + e.getMessage(), null);
		}
	}
}
