/**
 * 
 */
package org.hpi.dialogue.protocol.request;

import org.hpi.dialogue.protocol.common.HPIDialogueConstants;
import org.simplestructruedata.entities.SSDObjectLeaf;
import org.simplestructruedata.entities.SSDObjectNode;

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
	
	@Override
	public String getServiceName() {
		return HPIDialogueConstants.EXECUTE_INVOKER_SERVICE;
	}
	
	@Override
	public SSDObjectNode getSSDParameters() {
		SSDObjectNode parameters = new SSDObjectNode(HPIDialogueConstants.PARAMETERS);
		parameters.addAttribute(new SSDObjectLeaf(HPIDialogueConstants.SESSION_ID, this.getSessionId()));
		parameters.addAttribute(new SSDObjectLeaf(HPIDialogueConstants.INVOKER_ID, this.getInvokeId()));
		return parameters;
	}
	
	// GETTERS AND SETTERS //
	public String getInvokeId() {
		return invokeId;
	}
	
}
