/**
 * 
 */
package org.hpi.data.factoy;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.com.tatu.helper.FileHelper;
import org.hpi.common.HPIConstants;
import org.hpi.dialogue.protocol.entities.Executable;
import org.hpi.dialogue.protocol.entities.Invoker;
import org.hpi.dialogue.protocol.entities.Parameter;
import org.hpi.dialogue.protocol.entities.User;
import org.simplestructruedata.data.SSDContextManager;
import org.simplestructruedata.data.SSDContextManager.SSDRootObject;
import org.simplestructruedata.entities.SSDObjectArray;
import org.simplestructruedata.entities.SSDObjectNode;

/**
 * @author Jean Villete
 *
 */
public abstract class LoadFactoryManager {

	private static final Logger log = Logger.getLogger(LoadFactoryManager.class.getName());
	
	/**
	 * 
	 * @param user
	 */
	public void addUser(User user) {
		if (user == null) {
			throw new IllegalArgumentException("The argument user can't be null.");
		}
		HPIDataFactory.getInstance().addUser(user);
	}
	
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
		invoker.setDescription(root.getLeaf(HPIConstants.DESCRIPTION_INVOKER_FILE).getValue());
		
		SSDObjectArray ssdExecutables = root.getArray(HPIConstants.EXECUTABLES_INVOKER_FILE);
		for (int i = 0; i < ssdExecutables.getSize(); i++) {
			SSDObjectNode ssdExecutable = ssdExecutables.getNode(i);;
			Executable executable = new Executable();
			executable.setCanonicalPath(ssdExecutable.getLeaf(HPIConstants.CANONICAL_PATH_INVOKER_FILE).getValue());
			
			SSDObjectArray ssdParameters = ssdExecutable.getArray(HPIConstants.PARAMETERS_INVOKER_FILE);
			for (int j = 0; j < ssdParameters.getSize(); j++) {
				SSDObjectNode ssdParameter = ssdParameters.getNode(j);
				Parameter parameter = new Parameter();
				parameter.setKey(ssdParameter.getLeaf(HPIConstants.KEY_INVOKER_FILE).getValue());
				parameter.setValue(ssdParameter.getLeaf(HPIConstants.VALUE_INVOKER_FILE).getValue());
				executable.getParameters().add(parameter);
			}
			invoker.getExecutables().add(executable);
		}
		
		HPIDataFactory.getInstance().addInvoker(invoker);
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
		
		HPIDataFactory.getInstance().removeInvoker(FileHelper.removeExtension(invokerFile.getName()));
	}
}
