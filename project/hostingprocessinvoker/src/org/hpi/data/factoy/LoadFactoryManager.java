/**
 * 
 */
package org.hpi.data.factoy;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.com.tatu.helper.FileHelper;
import org.hpi.common.Constants;
import org.hpi.entities.Executable;
import org.hpi.entities.Invoker;
import org.hpi.entities.Parameter;
import org.simplestructruedata.data.SSDContextManager;
import org.simplestructruedata.data.SSDContextManager.SSDRootObject;
import org.simplestructruedata.entities.SSDObjectArray;
import org.simplestructruedata.entities.SSDObjectNode;

/**
 * @author villjea
 *
 */
public abstract class LoadFactoryManager {

	private static final Logger log = Logger.getLogger(LoadFactoryManager.class.getName());
	
	/**
	 * 
	 * @param invokerFile
	 */
	public void addInvokerFile(File invokerFile) {
		if (invokerFile == null || !invokerFile.isFile()) {
			throw new IllegalArgumentException("The invokerFile is null or isn't a valid file.");
		}
		
		log.log(Level.FINE, "Adding a new invokerFile: " + invokerFile.getName());
		
		SSDContextManager ssdCtx = SSDContextManager.build(invokerFile);
		SSDRootObject root = ssdCtx.getRootObject();
		
		Invoker invoker = new Invoker(FileHelper.removeExtension(invokerFile.getName()));
		invoker.setDescription(root.getLeaf(Constants.DESCRIPTION_INVOKER_FILE).getValue());
		
		SSDObjectArray ssdExecutables = root.getArray(Constants.EXECUTABLES_INVOKER_FILE);
		for (int i = 0; i < ssdExecutables.getSize(); i++) {
			SSDObjectNode ssdExecutable = ssdExecutables.getNode(i);;
			Executable executable = new Executable();
			executable.setCanonicalPath(ssdExecutable.getLeaf(Constants.CANONICAL_PATH_INVOKER_FILE).getValue());
			
			SSDObjectArray ssdParameters = ssdExecutable.getArray(Constants.PARAMETERS_INVOKER_FILE);
			for (int j = 0; j < ssdParameters.getSize(); i++) {
				SSDObjectNode ssdParameter = ssdParameters.getNode(j);
				Parameter parameter = new Parameter();
				parameter.setKey(ssdParameter.getLeaf(Constants.KEY_INVOKER_FILE).getValue());
				parameter.setValue(ssdParameter.getLeaf(Constants.VALUE_INVOKER_FILE).getValue());
				executable.getParameters().add(parameter);
			}
			invoker.getExecutables().add(executable);
		}
		
		InvokerFactory.getInstance().addInvoker(invoker);
	}
	
	/**
	 * 
	 * @param invokerFile
	 */
	public void removeInvoker(File invokerFile) {
		if (invokerFile == null || invokerFile.isFile()) {
			throw new IllegalArgumentException("The invokerFile is null or isn't a valid file.");
		}
		
		log.log(Level.FINE, "Removing an invokerFile: " + invokerFile.getName());
		
		InvokerFactory.getInstance().removeInvoker(FileHelper.removeExtension(invokerFile.getName()));
	}
}