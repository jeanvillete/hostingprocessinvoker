/**
 * 
 */
package org.hpi.data;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.hpi.data.factoy.LoadFactoryManager;
import org.hpi.exception.HPIRuntimeException;

import br.com.datawatcher.entity.FileWrapper;
import br.com.datawatcher.entity.SimpleRegister;
import br.com.datawatcher.interfaces.DataChangeable;

/**
 * @author Jean Villete
 *
 */
public class MappedFolderListener extends LoadFactoryManager implements DataChangeable {
	
	private static final Logger log = Logger.getLogger(MappedFolderListener.class.getName());

	@Override
	public void delete(SimpleRegister oldFile) {
		try {
			FileWrapper invokerFile = (FileWrapper) oldFile;
			this.removeInvoker(new File(invokerFile.getFile().getCanonicalPath()));
			
			log.info("An invokerFile has been removed. File : " + invokerFile.getFile().getName());
		} catch (IOException e) {
			throw new HPIRuntimeException(e);
		}
	}

	@Override
	public void insert(SimpleRegister newFile) {
		try {
			FileWrapper invokerFile = (FileWrapper) newFile;
			this.addInvokerFile(new File(invokerFile.getFile().getCanonicalPath()));
			
			log.info("A new invokerFile has been added. File : " + invokerFile.getFile().getName());
		} catch (IOException e) {
			throw new HPIRuntimeException(e);
		}
	}

	@Override
	public void update(SimpleRegister oldFile, SimpleRegister newFile) {
		throw new HPIRuntimeException("Unimplemented method!");
	}
}