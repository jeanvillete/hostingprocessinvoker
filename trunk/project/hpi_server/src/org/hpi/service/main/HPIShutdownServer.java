package org.hpi.service.main;
import java.io.File;

import org.apache.log4j.Logger;
import org.hpi.common.HPIConstants;
import org.hpi.dialogue.protocol.response.ServerShutdownResponse;
import org.hpi.dialogue.protocol.service.HPIClientProtocol;
import org.simplestructruedata.data.SSDContextManager;
import org.simplestructruedata.data.SSDContextManager.SSDRootObject;
import org.simplestructruedata.entities.SSDObjectNode;

/**
 * @author Jean Villete
 *
 */
public class HPIShutdownServer {

	private static final Logger log = Logger.getLogger(HPIShutdownServer.class.getName());
	
	public static void main(String[] args) {
		try {
			log.info("HPI (Hosting Process Invoker) turning SHUTDOWN.");
			
			// getting the settings file and its data to SSD context manager
			File settingsFile = new File(System.getProperty(HPIConstants.ENV_HPI_BASE) + System.getProperty("file.separator"), HPIConstants.CONFIGURATIONS_FILE_ADDRESS);
			
			log.info( "Shutdown looking for data settings at: " + settingsFile.getCanonicalPath());
			SSDContextManager ssdCtx = SSDContextManager.build(settingsFile);
			SSDRootObject ssdSettingsData = ssdCtx.getRootObject();

			SSDObjectNode ssdConfiguration = ssdSettingsData.getNode(HPIConstants.CONFIGURATIONS_CONFIG_SERVER);
			int portNumber = Integer.parseInt(ssdConfiguration.getLeaf(HPIConstants.CONFIGURATIONS_PORT_NUMBER).getValue());
			HPIClientProtocol clientProtocol = new HPIClientProtocol("127.0.0.1", portNumber);
			ServerShutdownResponse shutdownResponse = clientProtocol.serverShutdown();
			
			log.info(shutdownResponse.getMessage());
		} catch (Exception e) {
			log.error(e);
			System.exit(1);
		}
		System.exit(0);
	}
}
