/**
 * 
 */
package org.hostingprocessinvoker.data;

import java.io.File;
import java.io.IOException;

import org.hostingprocessinvoker.data.factoy.LoadFactoryManager;
import org.hostingprocessinvoker.exception.HostingProcessInvokerException;

import br.com.datawatcher.entity.FileWrapper;
import br.com.datawatcher.entity.SimpleRegister;
import br.com.datawatcher.interfaces.DataChangeable;

/**
 * @author villjea
 *
 */
public class MappedFolderListener extends LoadFactoryManager implements DataChangeable {

	@Override
	public void delete(SimpleRegister oldFile) {
		try {
			FileWrapper invokerFile = (FileWrapper) oldFile;
			this.removeInvoker(new File(invokerFile.getFile().getCanonicalPath()));
		} catch (IOException e) {
			throw new HostingProcessInvokerException(e);
		}
	}

	@Override
	public void insert(SimpleRegister newFile) {
		try {
			FileWrapper invokerFile = (FileWrapper) newFile;
			this.addInvokerFile(new File(invokerFile.getFile().getCanonicalPath()));
		} catch (IOException e) {
			throw new HostingProcessInvokerException(e);
		}
	}

	@Override
	public void update(SimpleRegister oldFile, SimpleRegister newFile) {
	}
}