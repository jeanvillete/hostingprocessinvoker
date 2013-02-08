package org.hpi.service.main;
import java.io.File;

import org.apache.log4j.Logger;
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
public class HPIStartupServer {

	private static final Logger log = Logger.getLogger(HPIStartupServer.class.getName());
	
	public static void main(String[] args) {
		try {
			log.info("HPI (Hosting Process Invoker) initializing.");
			
			// getting the settings file and its data to SSD context manager
			File settingsFile = new File(System.getProperty(HPIConstants.ENV_HPI_BASE) + System.getProperty("file.separator"), HPIConstants.CONFIGURATIONS_FILE_ADDRESS);
			
			log.info( "Looking for data settings at: " + settingsFile.getCanonicalPath());
			SSDContextManager ssdCtx = SSDContextManager.build(settingsFile);
			SSDRootObject ssdSettingsData = ssdCtx.getRootObject();
			
			// load the configuration and first data to FactoryManager and as well instatiating the data watcher to invokers folders
			InvokerDataLoader dataLoader = new InvokerDataLoader();
			dataLoader.startup(ssdSettingsData);
			
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
			
			log.info("HPI finalizing successfully.");
		} catch (Exception e) {
			log.error(e);
			log.error("A severe exception has happend and it's forcing HPI down!");
			System.exit(1);
		}
		System.exit(0);
	}
}
