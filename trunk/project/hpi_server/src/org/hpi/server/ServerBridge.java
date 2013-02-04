/**
 * 
 */
package org.hpi.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hpi.common.HPIConstants;
import org.simplestructruedata.data.SSDContextManager.SSDRootObject;
import org.simplestructruedata.entities.SSDObjectNode;

/**
 * @author Jean Villete
 *
 */
public class ServerBridge {
	
	private static final Logger 	log = Logger.getLogger(ServerBridge.class.getName());
	private static final long		TIME_CHECK_SHUTDOWN = 1000 * 5;
	static boolean					SHUTDOWN = false;

	public ServerBridge() { }
	
	public void turnup(SSDRootObject ssdSettingsData) throws InterruptedException, IOException {
		log.log(Level.INFO, "Turning the ServerBridge UP.");
		
		// first load to invokers SSD files
		SSDObjectNode ssdConfiguration = ssdSettingsData.getNode(HPIConstants.CONFIGURATIONS_CONFIG_SERVER);
		
		// instantiating and loading the server socket to new thread manager
		int portNumber = Integer.parseInt(ssdConfiguration.getLeaf(HPIConstants.CONFIGURATIONS_PORT_NUMBER).getValue());
		if (portNumber <= 0) {
			throw new IllegalStateException("The port number must be a valid number greater than 0.");
		}
		ServerSocket serverSocket = new ServerSocket(portNumber);
		ServerSocketManager serverSocketManager = new ServerSocketManager(serverSocket);
		serverSocketManager.start();
		
		while (!SHUTDOWN) {
			Thread.sleep(TIME_CHECK_SHUTDOWN);
		}
		
		log.log(Level.INFO, "Turning the ServerBridge DOWN.");
		serverSocketManager.interrupt();
		serverSocket.close();
	}
	
	private class ServerSocketManager extends Thread {
		
		private ServerSocket serverSocket = null;
		
		ServerSocketManager(ServerSocket serverSocket) {
			super("ServerSocketManager");
			this.serverSocket = serverSocket;
		}
		
		@Override
		public void run() {
			try {
				boolean listening = true;
				while (listening) {
					new HPIServerImplProtocol(this.serverSocket.accept()).start();
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
}
