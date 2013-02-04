/**
 * 
 */
package org.hpi.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.hpi.data.factoy.HPIDataFactory;
import org.hpi.entities.HPISession;
import org.hpi.entities.Invoker;
import org.hpi.entities.User;
import org.hpi.protocol.request.ExecuteInvokerOperation;
import org.hpi.protocol.request.ListInvokersOperation;
import org.hpi.protocol.request.LoginOperation;
import org.hpi.protocol.request.Operation;
import org.hpi.protocol.request.ShutdownServerOperation;
import org.hpi.protocol.response.ListInvokersResponse;
import org.hpi.protocol.response.LoginResponse;
import org.hpi.protocol.response.ServerRunningResponse;
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
		try {
			PrintWriter writer = new PrintWriter(this.socket.getOutputStream(), true);
			BufferedReader reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			
			String inputString = null;
			
			// print the first response that the server is alive
			ServerRunningResponse serverUp = new ServerRunningResponse("1", "service alive and ready to response");
			writer.println(serverUp.toString());
			
			while ((inputString = reader.readLine()) != null) {
				Operation operation = Operation.build(inputString);
				
				if (operation instanceof LoginOperation) {
					LoginResponse loginResponse = this.doLogin((LoginOperation) operation); 
					writer.println(loginResponse.toString());
				} else if (operation instanceof ListInvokersOperation) {
					ListInvokersResponse invokersResponse = this.getListInvokers((ListInvokersOperation) operation);
					writer.println(invokersResponse.toString());
				} else if (operation instanceof ExecuteInvokerOperation) {
					// TODO
				} else if (operation instanceof ShutdownServerOperation) {
					ServerBridge.SHUTDOWN = true;
				} else throw new IllegalStateException("Unkonw the operation. operation name: " + operation.getOperationName());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
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
			HPISession session = new HPISession(user, this.socket.getRemoteSocketAddress().toString());
			HPISessionManager sessionManager = HPISessionManager.getInstance();
			sessionManager.newSession(session);
			return new LoginResponse("0", "User logged successfully.", session.getSession_id());
		} else {
			return new LoginResponse("1", "User not found or passphrase doesn't match.", "invalid session");
		}
	}
	
	private ListInvokersResponse getListInvokers(ListInvokersOperation operation) {
		HPISessionManager sessionManager = HPISessionManager.getInstance();
		HPISession session = sessionManager.getSession(operation.getSessionId());
		if (session != null) {
			HPIDataFactory dataFactory = HPIDataFactory.getInstance();
			List<String> listInvokers = new ArrayList<String>();
			for (Invoker invoker : dataFactory.getInvokers()) {
				listInvokers.add("id: " + invoker.getId() + ", description: " + invoker.getDescription());
			}
			return new ListInvokersResponse("0", "List invokers executed successfully.", listInvokers);
		} else {
			return new ListInvokersResponse("1", "There's no valid session.", null);
		}
	}
}
