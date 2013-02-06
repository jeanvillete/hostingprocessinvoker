/**
 * 
 */
package org.hpi.dialogue.protocol.request;

/**
 * @author Jean Villete
 *
 */
public class DescribeInvokerRequest extends SessionedRequest {

	private static final long serialVersionUID = 1257749502283139474L;
	
	private String					invokeId;
	
	public DescribeInvokerRequest(String sessionId, String invokeId) {
		super(sessionId);
		this.invokeId = invokeId;
	}
	
	// GETTERS AND SETTERS //
	public String getInvokeId() {
		return invokeId;
	}
	
}
