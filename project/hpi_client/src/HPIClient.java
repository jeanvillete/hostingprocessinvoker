import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import org.com.tatu.helper.GeneralsHelper;
import org.com.tatu.helper.parameter.ConsoleParameters;
import org.hpi.dialogue.protocol.response.ListInvokersResponse;
import org.hpi.dialogue.protocol.response.LoginResponse;
import org.hpi.dialogue.protocol.response.LogoffResponse;
import org.hpi.dialogue.protocol.response.Response;
import org.hpi.dialogue.protocol.response.ServerRunningResponse;
import org.hpi.dialogue.protocol.service.HPIClientProtocol;

/**
 * 
 */

/**
 * @author villjea
 *
 */
public class HPIClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Socket managedSocket = null;
		PrintWriter writer = null;
		BufferedReader input = null;
		try {
			ConsoleParameters consoleParameters = ConsoleParameters.getInstance(args);
			
			// user parameter
			String userParam = null;
			if (!GeneralsHelper.isStringOk(userParam = consoleParameters.getValue("-user")) &&
					!GeneralsHelper.isStringOk(userParam = consoleParameters.getValue("-u"))) {
				throw new IllegalArgumentException("The parameter -user[u] is mandatory.");
			}
			
			// password parameter
			String passwordParam = null;
			if (!GeneralsHelper.isStringOk(passwordParam = consoleParameters.getValue("-password")) &&
					!GeneralsHelper.isStringOk(passwordParam = consoleParameters.getValue("-p"))) {
				throw new IllegalArgumentException("The parameter -password[p] is mandatory.");
			}
			
			HPIClientProtocol clientProtocol = new HPIClientProtocol("127.0.0.1", 4444);
			
			// getting the first response of the target server, telling if the server is up
			ServerRunningResponse serverUpResponse = clientProtocol.openConnection();
			if (serverUpResponse.getStatus().equals(Response.Status.SUCCESS)) {
				System.out.println("Communication stablished successfully.");
				System.out.println("Server response: " + serverUpResponse.getMessage());
			} else if (serverUpResponse.getStatus().equals(Response.Status.FAIL)) {
				throw new IllegalStateException("The communication couldn't be stablished. " + serverUpResponse.getMessage());
			} else throw new IllegalStateException("Unkonw the status code server's response");
			
			// do login
			LoginResponse loginResponse = clientProtocol.doLogin(userParam, passwordParam);
			if (loginResponse.getStatus().equals(Response.Status.SUCCESS)) {
				System.out.println("The user has been logged successfully");
				System.out.println("The created session id was: " + loginResponse.getSessionId());
				System.out.println("Server response: " + loginResponse.getMessage());
			} else if (loginResponse.getStatus().equals(Response.Status.FAIL)) {
				throw new IllegalStateException(loginResponse.getMessage()); 
			} else throw new IllegalStateException("Unkonw the server's status code response");
			
			// list invokers
			ListInvokersResponse invokersResponse = clientProtocol.listInvokers(loginResponse.getSessionId());
			if (invokersResponse.getStatus().equals(Response.Status.SUCCESS)) {
				System.out.println("The ListInvokers command has been executed successfully.");
				System.out.println("The list invokers are;");
				for (String invoker : invokersResponse.getListInvokersId()) {
					System.out.println("\t\t" + invoker);
				}
			} else if (invokersResponse.getStatus().equals(Response.Status.FAIL)) {
				throw new IllegalStateException(invokersResponse.getMessage());
			} else throw new IllegalStateException("Unkonw the server's status code response");
			
			// do logoff
			LogoffResponse logoffResponse = clientProtocol.doLogoff(loginResponse.getSessionId());
			if (logoffResponse.getStatus().equals(Response.Status.SUCCESS)) {
				System.out.println("The logoff has been executed.");
			} else if (logoffResponse.getStatus().equals(Response.Status.FAIL)) {
				throw new IllegalStateException(logoffResponse.getMessage());
			} else throw new IllegalStateException("Unkonw the server's status code response");
			
			// invoking shutdown
//			clientProtocol.serverShutdown();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			try {
				if (input != null) input.close();
				if (writer != null) writer.close();
				if (managedSocket != null) managedSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
//	private void printUsage() {
//		System.out.println("hpi (login)");
//		System.out.println(" -user[u] theUserName");
//		System.out.println(" -password[p] thePasswordValue");
//	}

}
