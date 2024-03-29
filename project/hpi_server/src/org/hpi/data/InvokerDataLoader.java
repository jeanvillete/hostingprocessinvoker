/**
 * 
 */
package org.hpi.data;

import java.io.File;
import java.io.FilenameFilter;

import org.apache.log4j.Logger;
import org.hpi.common.HPIConstants;
import org.hpi.data.factoy.LoadFactoryManager;
import org.hpi.dialogue.protocol.entities.User;
import org.hpi.exception.HPIRuntimeException;
import org.simplestructruedata.data.SSDContextManager.SSDRootObject;
import org.simplestructruedata.entities.SSDObjectArray;
import org.simplestructruedata.entities.SSDObjectNode;

import br.com.datawatcher.entity.CheckChange;
import br.com.datawatcher.entity.DataWatcher;
import br.com.datawatcher.entity.FolderMapping;
import br.com.datawatcher.entity.Listener;

/**
 * @author Jean Villete
 *
 */
public class InvokerDataLoader extends LoadFactoryManager {
	
	private static final Logger log = Logger.getLogger(InvokerDataLoader.class.getName());
	
	public void startup(SSDRootObject ssdSettingsData) {
		log.info("Initializing HPI startup loader.");
		
		try {
			// load users to Factory Manager
			SSDObjectArray ssdUsers = ssdSettingsData.getArray(HPIConstants.CONFIGURATIONS_USERS);
			for (int i = 0; i < ssdUsers.getSize(); i++) {
				SSDObjectNode ssdUser = ssdUsers.getNode(i);
				String passphrase = ssdUser.getLeaf(HPIConstants.CONFIGURATIONS_USERS_PASSPHRASE).getValue();
				String nickname = ssdUser.getLeaf(HPIConstants.CONFIGURATIONS_USERS_NICKNAME).getValue();
				this.addUser(new User(nickname, passphrase));
			}
			
			// instantiate the data watcher object, because afeter it will be started to watch some directory(ies)
			DataWatcher dataWatcher = new DataWatcher();
			
			// first load to invokers SSD files
			SSDObjectArray ssdMappedFolders = ssdSettingsData.getArray(HPIConstants.CONFIGURATIONS_MAPPED_FOLDER);
			for (int i = 0; i < ssdMappedFolders.getSize(); i++) {
				SSDObjectNode ssdMappedFolder = ssdMappedFolders.getNode(i);
				File mappedFolder = null;
				if (ssdMappedFolder.get(HPIConstants.CONFIGURATIONS_RELATIVE_SERVER) != null) {
					mappedFolder = new File(System.getProperty(HPIConstants.ENV_HPI_BASE), 
							System.getProperty("file.separator") + 
							ssdMappedFolder.getLeaf(HPIConstants.CONFIGURATIONS_RELATIVE_SERVER).getValue());
				} else if (ssdMappedFolder.get(HPIConstants.CONFIGURATIONS_CANONICAL_PATH) != null) {
					mappedFolder = new File(ssdMappedFolder.getLeaf(HPIConstants.CONFIGURATIONS_CANONICAL_PATH).getValue());
				} else throw new IllegalStateException("There's some wrong thing in the " + HPIConstants.CONFIGURATIONS_MAPPED_FOLDER);
				
				for (File invokerFile : mappedFolder.listFiles(new FolderFilter())) {
					this.addInvokerFile(invokerFile);
				}
				
				// mapping the folder to data watcher
				FolderMapping folder = new FolderMapping();
				folder.setIdentifier(HPIConstants.DATA_WATCHER_FOLDER_MAPPING + i);
				folder.setCanonicalPath(mappedFolder.getCanonicalPath());
				
				log.info("Mapping folder to DataWatcher. Folder: " + mappedFolder.getCanonicalPath());
				
				folder.setRegexFilter(HPIConstants.REGEX_FILE);
				folder.setCheckChange(new CheckChange(HPIConstants.DATA_WATCHER_CRON_EXPRESSION));
				folder.addListeners(new Listener(MappedFolderListener.class.getName()));
				dataWatcher.addMapping(folder);
			}
			
			log.info("Starting DataWatcher.");
			dataWatcher.start();
		} catch (Exception e) {
			throw new HPIRuntimeException(e);
		}
	}
	
	private class FolderFilter implements FilenameFilter {
		@Override
		public boolean accept(File dir, String name) {
			return name.matches(HPIConstants.REGEX_FILE);
		}
	}
}