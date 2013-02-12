package org.hpi.service.main;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.com.tatu.helper.GeneralsHelper;
import org.com.tatu.helper.parameter.ConsoleParameters;
import org.hpi.dialogue.protocol.entities.User;
import org.hpi.dialogue.protocol.response.LoginResponse;
import org.hpi.dialogue.protocol.response.Response;
import org.hpi.dialogue.protocol.service.HPIClientProtocol;
import org.hpi.exception.HPIUsageException;
import org.hpi.service.DescribeInvokerService;
import org.hpi.service.ExecuteInvokerService;
import org.hpi.service.ListInvokersService;
import org.hpi.service.LogoffService;
import org.hpi.service.ServerShutdownService;

/**
 * @author Jean Villete
 *
 */
public class HPIClient {
	
	private static HPIClientProtocol connectClientProtocol(String server) {
		Integer port = 4444;
		if (server.contains(":")) {
			String[] serverAndPort = server.split(":");
			server = serverAndPort[0];
			port = Integer.parseInt(serverAndPort[1]);
		}
		return new HPIClientProtocol(server, port);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		HPIClientProtocol clientProtocol = null;
		try {
			ConsoleParameters consoleParameters = ConsoleParameters.getInstance(args);
			
			// -server[-s]
			String serverParam = null;
			if (!GeneralsHelper.isStringOk(serverParam = consoleParameters.getValue("-server")) &&
					!GeneralsHelper.isStringOk(serverParam = consoleParameters.getValue("-s"))) {
				throw new HPIUsageException();
			}
			
			// -user[-u]
			String userParam = null;
			if (!GeneralsHelper.isStringOk(userParam = consoleParameters.getValue("-user")) &&
					!GeneralsHelper.isStringOk(userParam = consoleParameters.getValue("-u"))) {
				throw new HPIUsageException();
			}
			
			// -password[-p]
			String passwordParam = null;
			if (!GeneralsHelper.isStringOk(passwordParam = consoleParameters.getValue("-password")) &&
					!GeneralsHelper.isStringOk(passwordParam = consoleParameters.getValue("-p"))) {
				throw new HPIUsageException();
			}
			
			// create the client protocol instance and tries do login
			clientProtocol = connectClientProtocol(serverParam.trim());
			LoginResponse loginResponse = clientProtocol.doLogin(new User(userParam, passwordParam));
			if (loginResponse.getStatus().equals(Response.Status.SUCCESS)) {
				BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
				String command = null;
				System.out.print("User connected successfully\n");
				printPossibilities();
				System.out.print("client's command > ");
				
				while ((command = stdIn.readLine()) != null) {
					command = command.trim();
					command = command.replace(";", "");
					command = "-" + command;
					ConsoleParameters innerParam = ConsoleParameters.getInstance(command.trim().split(" "));
					
					String paramValue = null;
					String response = null;
					
					if (GeneralsHelper.isStringOk(paramValue = innerParam.getValue(ListInvokersService.COMMAND))) { // list
						response = new ListInvokersService(loginResponse.getSessionId()).execute(clientProtocol);
					} else if (GeneralsHelper.isStringOk(paramValue = innerParam.getValue(DescribeInvokerService.COMMAND))) { // describe
						response = new DescribeInvokerService(loginResponse.getSessionId(), paramValue).execute(clientProtocol);
					} else if (GeneralsHelper.isStringOk(paramValue = innerParam.getValue(ExecuteInvokerService.COMMAND))) { // execute
						response = new ExecuteInvokerService(loginResponse.getSessionId(), paramValue).execute(clientProtocol);
					} else if (GeneralsHelper.isStringOk(paramValue = innerParam.getValue(ServerShutdownService.COMMAND))) { // shutdown
						response = new ServerShutdownService().execute(clientProtocol);
					} else if (GeneralsHelper.isStringOk(paramValue = innerParam.getValue(LogoffService.COMMAND))) { // exit
						response = new LogoffService(loginResponse.getSessionId()).execute(clientProtocol);
						
						System.out.println(response);
						System.out.println("Bye!");
						break;
					} else {
						printPossibilities();
					}
					
					if (GeneralsHelper.isStringOk(response)) {
						System.out.println("Server's response > " + response);
					}
					System.out.print("client's command > ");
				}
			} else {
				throw new IllegalStateException(loginResponse.getMessage());
			}
		} catch (HPIUsageException e) {
			System.out.println("hpi (all parameters are mandatories)");
			System.out.println("  -server[-s]    the target server (e.g.)\"127.0.0.1\", the port number is optional (e.g.)\"127.0.0.1:4444\" default is \"4444\"");
			System.out.println("  -user[u]       the user name");
			System.out.println("  -password[p]   the user password");
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		} finally {
			if (clientProtocol != null) {
				clientProtocol.closeSocket();
			}
		}

	}
	
	private static void printPossibilities() {
		System.out.println("Commands possibilities;");
		System.out.println("  list                     show all invokers managed by the current server");
		System.out.println("  describe \"invoker id\"    show the description of an invoker");
		System.out.println("  execute \"invoker id\"     execute the invoker in the server's context");
		System.out.println("  exit                     command to exit or logoff the session");
		System.out.println("  shutdown                 command to shutdown server");
	}
}
