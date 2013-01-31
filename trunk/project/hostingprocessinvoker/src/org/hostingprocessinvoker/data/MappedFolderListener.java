/**
 * 
 */
package org.hostingprocessinvoker.data;

import java.io.File;

import org.hostingprocessinvoker.data.factoy.LoadFactoryManager;

import br.com.datawatcher.entity.SimpleRegister;
import br.com.datawatcher.interfaces.DataChangeable;

/**
 * @author villjea
 *
 */
public class MappedFolderListener extends LoadFactoryManager implements DataChangeable {

	@Override
	public void delete(SimpleRegister oldFile) {
		br.com.datawatcher.entity.File invokerFile = (br.com.datawatcher.entity.File) oldFile;
		this.removeInvoker(new File(invokerFile.getName()));
	}

	@Override
	public void insert(SimpleRegister newFile) {
		br.com.datawatcher.entity.File invokerFile = (br.com.datawatcher.entity.File) newFile;
		this.addInvokerFile(new File(invokerFile.getName()));
	}

	@Override
	public void update(SimpleRegister oldFile, SimpleRegister newFile) {
	}
}