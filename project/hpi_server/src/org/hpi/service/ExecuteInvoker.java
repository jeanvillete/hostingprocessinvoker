/**
 * 
 */
package org.hpi.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.com.tatu.helper.GeneralsHelper;
import org.hpi.dialogue.protocol.entities.Executable;
import org.hpi.dialogue.protocol.entities.Invoker;
import org.hpi.dialogue.protocol.entities.Parameter;
import org.hpi.exception.HPIExecuteInvokerException;

/**
 * @author Jean Villete
 *
 */
public class ExecuteInvoker {
	
	private Invoker					invoker;
	
	public ExecuteInvoker(Invoker invoker) {
		super();
		if (invoker == null) {
			throw new IllegalArgumentException("The argument invoker cann't be null.");
		}
		this.invoker = invoker;
	}
	
	public String runExecutables() throws HPIExecuteInvokerException {
		StringBuffer resultMsg = new StringBuffer();
		for (Executable executable : this.invoker.getExecutables()) {
			try {
				List<String> cmd = new ArrayList<String>();
				cmd.add(executable.getCanonicalPath());
				for (Parameter parameter : executable.getParameters()) {
					if (GeneralsHelper.isStringOk(parameter.getKey())) {
						cmd.add(parameter.getKey());
					}
					if (GeneralsHelper.isStringOk(parameter.getValue())) {
						cmd.add(parameter.getValue());
					}
				}
				
				ProcessBuilder pb = new ProcessBuilder();
				pb.directory(new File(System.getProperty("user.home")));
		        pb.redirectErrorStream(true);
		        pb.command(cmd);
				
				Process process = pb.start();
				
				ExecuteInvokerStreamGobbler outputGobbler = new ExecuteInvokerStreamGobbler(process.getInputStream());
				outputGobbler.start();
				
				process.waitFor();
				resultMsg.append(outputGobbler.getMessage());
			} catch (IOException e) {
				throw new HPIExecuteInvokerException(e);
			} catch (InterruptedException e) {
				throw new HPIExecuteInvokerException(e);
			}
		}
		return resultMsg.toString();
	}
	
}
