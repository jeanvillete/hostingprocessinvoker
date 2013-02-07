import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.com.tatu.helper.parameter.ConsoleParameters;
import org.hpi.common.HPIConstants;
import org.hpi.data.InvokerDataLoader;
import org.hpi.server.ServerBridge;
import org.hpi.server.session.HPISessionManager;
import org.simplestructruedata.data.SSDContextManager;
import org.simplestructruedata.data.SSDContextManager.SSDRootObject;

/**
 * @author Jean Villete
 *
 */
public class HPIServer {

	private static final Logger log = Logger.getLogger(HPIServer.class.getName());
	
	public static void main(String[] args) {
		try {
			log.log(Level.INFO, "HPI (Hosting Process Invoker) initializing.");
			
			File currentDirectory = new File(
					ConsoleParameters.getInstance(args).getValue(HPIConstants.CONSOLE_PARAMETER_CURRENT_DIR, true).trim());
			if (!currentDirectory.exists() || !currentDirectory.isDirectory()) {
				throw new IllegalArgumentException("The directory " + currentDirectory.getCanonicalPath() 
						+ " either doesn't exist or it isn't a directory.");
			}
			
			log.log(Level.INFO, currentDirectory.getPath());
			
			// getting the settings file and its data to SSD context manager
			File settingsFile = new File(currentDirectory, HPIConstants.CONFIGURATIONS_FILE_ADDRESS);
			
			log.log(Level.INFO, settingsFile.getPath());
			
			log.log(Level.INFO, "Looking for data settings at: " + settingsFile.getCanonicalPath());
			SSDContextManager ssdCtx = SSDContextManager.build(settingsFile);
			SSDRootObject ssdSettingsData = ssdCtx.getRootObject();
			
			// load the configuration and first data to FactoryManager and as well instatiating the data watcher to invokers folders
			InvokerDataLoader dataLoader = new InvokerDataLoader();
			dataLoader.startup(currentDirectory, ssdSettingsData);
			
			// getting the keep session time alive to session manager startup
			int keepSessionAlive = Integer.parseInt(
				ssdSettingsData.getNode(HPIConstants.CONFIGURATIONS_CONFIG_SERVER)
					.getNode(HPIConstants.CONFIGURATIONS_SESSION_MANAGER)
					.getLeaf(HPIConstants.CONFIGURATIONS_KEEP_SESSION_ALIVE).getValue()
			);
			HPISessionManager.startup(keepSessionAlive);
			
			// turning the server up
			ServerBridge serverBridge = new ServerBridge();
			serverBridge.turnup(ssdSettingsData);
			
			log.log(Level.INFO, "HPI finalizing successfully.");
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			log.log(Level.SEVERE, "A severe exception has happend and it's forcing HPI down!");
			System.exit(1);
		}
		System.exit(0);
	}
}
