/**
 * 
 */
package org.hostingprocessinvoker.data;

import java.io.File;
import java.io.FilenameFilter;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hostingprocessinvoker.common.Constants;
import org.hostingprocessinvoker.data.factoy.LoadFactoryManager;
import org.hostingprocessinvoker.exception.HostingProcessInvokerException;
import org.simplestructruedata.data.SSDContextManager;
import org.simplestructruedata.data.SSDContextManager.SSDRootObject;
import org.simplestructruedata.entities.SSDObjectArray;

import br.com.datawatcher.entity.CheckChange;
import br.com.datawatcher.entity.DataWatcher;
import br.com.datawatcher.entity.FolderMapping;
import br.com.datawatcher.entity.Listener;

/**
 * @author villjea
 *
 */
public class InvokerDataLoader extends LoadFactoryManager {
	
	private static final Logger log = Logger.getLogger(InvokerDataLoader.class.getName());
	
	public void startup(File currentDirectory) {
		log.log(Level.INFO, "Initializing HPI startup loader.");
		
		try {
			// first load to invokers SSD files
			DataWatcher dataWatcher = new DataWatcher();
			File settingsFile = new File(currentDirectory, Constants.CONFIGURATIONS_FILE_ADDRESS);
			
			log.log(Level.INFO, "Looking for data settings at: " + settingsFile.getCanonicalPath());
			
			SSDContextManager ssdCtx = SSDContextManager.build(settingsFile);
			SSDRootObject root = ssdCtx.getRootObject();
			SSDObjectArray ssdMappedFolders = root.getArray(Constants.CONFIGURATIONS_MAPPED_FOLDER);
			for (int i = 0; i < ssdMappedFolders.getSize(); i++) {
				File mappedFolder = new File(ssdMappedFolders.getLeaf(i).getValue());
				for (File invokerFile : mappedFolder.listFiles(new FolderFilter())) {
					this.addInvokerFile(invokerFile);
				}
				
				// mapping the folder to data watcher
				FolderMapping folder = new FolderMapping();
				folder.setIdentifier(Constants.DATA_WATCHER_FOLDER_MAPPING + i);
				folder.setCanonicalPath(mappedFolder.getCanonicalPath());
				
				log.log(Level.FINE, "Mapping folder to DataWatcher. Folder: " + mappedFolder.getCanonicalPath());
				
				folder.setRegexFilter(Constants.REGEX_FILE);
				folder.setCheckChange(new CheckChange(Constants.DATA_WATCHER_CRON_EXPRESSION));
				folder.addListeners(new Listener(MappedFolderListener.class.getName()));
				dataWatcher.addMapping(folder);
			}
			
			log.log(Level.FINE, "Starting DataWatcher.");
			dataWatcher.start();
		} catch (Exception e) {
			throw new HostingProcessInvokerException(e);
		}
	}
	
	private class FolderFilter implements FilenameFilter {
		@Override
		public boolean accept(File dir, String name) {
			return name.matches(Constants.REGEX_FILE);
		}
	}
}