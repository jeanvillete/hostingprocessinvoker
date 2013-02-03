/**
 * 
 */
package org.hpi.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.hpi.protocol.request.ExecuteInvokerOperation;
import org.hpi.protocol.request.ListInvokerOperation;
import org.hpi.protocol.request.LoginOperation;
import org.hpi.protocol.request.Operation;
import org.hpi.protocol.response.ServerRunningResponse;

/**
 * @author carrefour
 *
 */
class HostingInvokerProtocol extends Thread {

	private Socket			socket = null;
	
	HostingInvokerProtocol(Socket socket) {
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
					// TODO
				} else if (operation instanceof ListInvokerOperation) {
					// TODO
				} else if (operation instanceof ExecuteInvokerOperation) {
					// TODO
				} else throw new IllegalStateException("Unkonw the operation. operation name: " + operation.getOperationName());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
