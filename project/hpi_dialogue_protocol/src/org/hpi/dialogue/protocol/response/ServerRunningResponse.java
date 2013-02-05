/**
 * 
 */
package org.hpi.dialogue.protocol.response;

/**
 * @author villjea
 *
 */
public class ServerRunningResponse extends Response {

	private static final long serialVersionUID = -1960805646017838306L;

	public ServerRunningResponse(String message, Status status) {
		super(message, status);
	}

}
