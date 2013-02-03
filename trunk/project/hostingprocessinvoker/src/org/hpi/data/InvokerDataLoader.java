/**
 * 
 */
package org.hpi.data;

import java.io.File;
import java.io.FilenameFilter;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hpi.common.Constants;
import org.hpi.data.factoy.LoadFactoryManager;
import org.hpi.exception.HostingProcessInvokerException;
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
	
	public void startup(SSDRootObject ssdSettingsData) {
		log.log(Level.INFO, "Initializing HPI startup loader.");
		
		try {
			DataWatcher dataWatcher = new DataWatcher();
			
			// first load to invokers SSD files
			SSDObjectArray ssdMappedFolders = ssdSettingsData.getArray(Constants.CONFIGURATIONS_MAPPED_FOLDER);
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