/**
 * 
 */
package org.hpi.dialogue.protocol.response;

import org.hpi.dialogue.protocol.entities.Invoker;

/**
 * @author Jean Villete
 *
 */
public class DescribeInvokerResponse extends Response {

	private static final long serialVersionUID = 4164109673082824380L;
	
	private Invoker						invoker;
	
	public DescribeInvokerResponse(Invoker invoker, String message, Status status) {
		super(message, status);
		this.invoker = invoker;
	}

	// GETTERS AND SETTERS //
	public Invoker getInvoker() {
		return invoker;
	}

}
