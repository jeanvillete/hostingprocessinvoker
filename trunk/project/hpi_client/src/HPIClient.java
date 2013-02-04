import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.com.tatu.helper.GeneralsHelper;
import org.com.tatu.helper.parameter.ConsoleParameters;
import org.hpi.protocol.operation.ListInvokersOperation;
import org.hpi.protocol.operation.LoginOperation;
import org.hpi.protocol.operation.LogoffOperation;
import org.hpi.protocol.operation.ShutdownServerOperation;
import org.hpi.protocol.response.ListInvokersResponse;
import org.hpi.protocol.response.LoginResponse;
import org.hpi.protocol.response.LogoffResponse;
import org.hpi.protocol.response.Response;
import org.hpi.protocol.response.ServerRunningResponse;

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
			
			// the server socket connection
			managedSocket = new Socket("127.0.0.1", 4444);
			writer = new PrintWriter(managedSocket.getOutputStream(), true);
			input = new BufferedReader(new InputStreamReader(managedSocket.getInputStream()));
			String inputString = null;

			// getting the first response of the target server, telling if the server is up
			inputString = input.readLine();
			ServerRunningResponse serverUpResponse = (ServerRunningResponse) Response.build(inputString);
			if (serverUpResponse.getStatusCode().equals(Response.STATUS_CODE_SUCCESS)) {
				System.out.println("Communication stablished successfully.");
				System.out.println("Server response: " + serverUpResponse.getMessage());
			} else if (serverUpResponse.getStatusCode().equals(Response.STATUS_CODE_FAIL)) {
				throw new IllegalStateException("The communication couldn't be stablished. " + serverUpResponse.getMessage());
			} else throw new IllegalStateException("Unkonw the status code server's response");
			
			// do login
			writer.println(new LoginOperation(userParam, passwordParam).toString());
			inputString = input.readLine();
			LoginResponse loginResponse = (LoginResponse) Response.build(inputString);
			if (loginResponse.getStatusCode().equals(Response.STATUS_CODE_SUCCESS)) {
				System.out.println("The user has been logged successfully");
				System.out.println("The created session id was: " + loginResponse.getSession_id());
				System.out.println("Server response: " + loginResponse.getMessage());
			} else if (loginResponse.getStatusCode().equals(Response.STATUS_CODE_FAIL)) {
				throw new IllegalStateException(loginResponse.getMessage()); 
			} else throw new IllegalStateException("Unkonw the server's status code response");
			
			// list invokers
			writer.println(new ListInvokersOperation(loginResponse.getSession_id()).toString());
			inputString = input.readLine();
			ListInvokersResponse invokersResponse = (ListInvokersResponse) Response.build(inputString);
			if (invokersResponse.getStatusCode().equals(Response.STATUS_CODE_SUCCESS)) {
				System.out.println("The ListInvokers command has been executed successfully.");
				System.out.println("The list invokers are;");
				for (String invoker : invokersResponse.getListInvokers()) {
					System.out.println("\t\t" + invoker);
				}
			} else if (invokersResponse.getStatusCode().equals(Response.STATUS_CODE_FAIL)) {
				throw new IllegalStateException(invokersResponse.getMessage());
			} else throw new IllegalStateException("Unkonw the server's status code response");
			
			// do logoff
			writer.println(new LogoffOperation(loginResponse.getSession_id()));
			inputString = input.readLine();
			LogoffResponse logoffResponse = (LogoffResponse) Response.build(inputString);
			if (logoffResponse.getStatusCode().equals(Response.STATUS_CODE_SUCCESS)) {
				System.out.println("The logoff has been executed.");
			} else if (logoffResponse.getStatusCode().equals(Response.STATUS_CODE_FAIL)) {
				throw new IllegalStateException(logoffResponse.getMessage());
			} else throw new IllegalStateException("Unkonw the server's status code response");
			
			// invoking shutdown
			writer.println(new ShutdownServerOperation().toString());
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
