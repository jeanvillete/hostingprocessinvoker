import java.io.File;
import java.io.IOException;

import org.com.tatu.helper.parameter.ConsoleParameters;
import org.hostingprocessinvoker.common.Constants;
import org.hostingprocessinvoker.data.InvokerDataLoader;

/**
 * 
 */

/**
 * @author villjea
 *
 */
public class Main {

	public static void main(String[] args) {
		try {
			ConsoleParameters consoleParameters = ConsoleParameters.getInstance(args);
			
			File currentDirectory = new File(consoleParameters.getValue(Constants.CONSOLE_PARAMETER_CURRENT_DIR, true));
			if (!currentDirectory.exists() || !currentDirectory.isDirectory()) {
				throw new IllegalArgumentException("The directory " + currentDirectory.getCanonicalPath() 
						+ " either doesn't exist or it isn't a directory.");
			}
			
			// load the configuration and first data
			InvokerDataLoader dataLoader = new InvokerDataLoader();
			dataLoader.startup(currentDirectory);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
