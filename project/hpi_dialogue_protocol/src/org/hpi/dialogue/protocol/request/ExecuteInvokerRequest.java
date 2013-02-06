/**
 * 
 */
package org.hpi.dialogue.protocol.request;

/**
 * @author Jean Villete
 *
 */
public class ExecuteInvokerRequest extends SessionedRequest {

	private static final long serialVersionUID = 8936703791587957733L;
	
	private String					invokeId;
	
	public ExecuteInvokerRequest(String sessionId, String invokeId) {
		super(sessionId);
		this.invokeId = invokeId;
	}
	
	// GETTERS AND SETTERS //
	public String getInvokeId() {
		return invokeId;
	}
	
}
