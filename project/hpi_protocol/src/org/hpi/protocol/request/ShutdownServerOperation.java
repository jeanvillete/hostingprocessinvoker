/**
 * 
 */
package org.hpi.protocol.request;

/**
 * @author Jean Villete
 *
 */
public class ShutdownServerOperation extends Operation {

	public static final String				SHUTDOWN_SERVER = "shutdown_server";
	
	public ShutdownServerOperation() {
		super(SHUTDOWN_SERVER);
	}

}
