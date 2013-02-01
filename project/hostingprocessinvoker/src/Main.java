import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.com.tatu.helper.parameter.ConsoleParameters;
import org.hostingprocessinvoker.common.Constants;
import org.hostingprocessinvoker.data.InvokerDataLoader;

/**
 * @author villjea
 *
 */
public class Main {

	private static final Logger log = Logger.getLogger(Main.class.getName());
	
	public static void main(String[] args) {
		try {
			log.log(Level.INFO, "HPI (Hosting Process Invoker) initializing.");
			
			ConsoleParameters consoleParameters = ConsoleParameters.getInstance(args);
			File currentDirectory = new File(consoleParameters.getValue(Constants.CONSOLE_PARAMETER_CURRENT_DIR, true));
			if (!currentDirectory.exists() || !currentDirectory.isDirectory()) {
				throw new IllegalArgumentException("The directory " + currentDirectory.getCanonicalPath() 
						+ " either doesn't exist or it isn't a directory.");
			}
			
			// load the configuration and first data
			InvokerDataLoader dataLoader = new InvokerDataLoader();
			dataLoader.startup(currentDirectory);
			
			log.log(Level.INFO, "HPI finalizing successfully.");
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			log.log(Level.SEVERE, "A severe exception has happend and it's forcing HPI down!");
			System.exit(1);
		}
		System.exit(0);
	}
}
