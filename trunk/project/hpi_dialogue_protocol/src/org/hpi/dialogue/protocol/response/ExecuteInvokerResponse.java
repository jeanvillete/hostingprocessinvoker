/**
 * 
 */
package org.hpi.dialogue.protocol.response;

/**
 * @author Jean Villete
 *
 */
public class ExecuteInvokerResponse extends Response {

	private static final long serialVersionUID = 2655139286365237415L;

	public ExecuteInvokerResponse(String message, Status status) {
		super(message, status);
	}

}
