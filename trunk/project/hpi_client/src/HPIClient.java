import org.com.tatu.helper.GeneralsHelper;
import org.com.tatu.helper.parameter.ConsoleParameters;
import org.hpi.dialogue.protocol.entities.Invoker;
import org.hpi.dialogue.protocol.entities.User;
import org.hpi.dialogue.protocol.response.ListInvokersResponse;
import org.hpi.dialogue.protocol.response.LoginResponse;
import org.hpi.dialogue.protocol.response.LogoffResponse;
import org.hpi.dialogue.protocol.response.Response;
import org.hpi.dialogue.protocol.response.ServerShutdownResponse;
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
		
		HPIClientProtocol clientProtocol = new HPIClientProtocol("127.0.0.1", 4444);
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
			
			// do login
			LoginResponse loginResponse = clientProtocol.doLogin(new User(userParam, passwordParam));
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
				for (Invoker invoker : invokersResponse.getListInvokers()) {
					System.out.println("\t\tid=" + invoker.getId() + ", description=" + invoker.getDescription());
				}
			} else if (invokersResponse.getStatus().equals(Response.Status.FAIL)) {
				throw new IllegalStateException(invokersResponse.getMessage());
			} else throw new IllegalStateException("Unkonw the server's status code response");
			
			// do logoff
			LogoffResponse logoffResponse = clientProtocol.doLogoff(loginResponse.getSessionId());
			if (logoffResponse.getStatus().equals(Response.Status.SUCCESS)) {
				System.out.println("The logoff has been executed.");
				System.out.println(logoffResponse.getMessage());
			} else if (logoffResponse.getStatus().equals(Response.Status.FAIL)) {
				throw new IllegalStateException(logoffResponse.getMessage());
			} else throw new IllegalStateException("Unkonw the server's status code response");
			
			// invoking shutdown
			ServerShutdownResponse shutdownResponse = clientProtocol.serverShutdown();
			if (shutdownResponse.getStatus().equals(Response.Status.SUCCESS)) {
				System.out.println("The shutdown request has been executed.");
				System.out.println(shutdownResponse.getMessage());
			} else if (shutdownResponse.getStatus().equals(Response.Status.FAIL)) {
				throw new IllegalStateException(shutdownResponse.getMessage());
			} else throw new IllegalStateException("Unkonw the server's status code response");
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			clientProtocol.closeSocket();
		}
	}
	
//	private void printUsage() {
//		System.out.println("hpi (login)");
//		System.out.println(" -user[u] theUserName");
//		System.out.println(" -password[p] thePasswordValue");
//	}

}
