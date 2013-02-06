/**
 * 
 */
package org.hpi.dialogue.protocol.response;

/**
 * @author Jean Villete
 *
 */
public class ServerShutdownResponse extends Response {

	private static final long serialVersionUID = -9158103958729952002L;

	public ServerShutdownResponse(String message, Status status) {
		super(message, status);
	}

}
