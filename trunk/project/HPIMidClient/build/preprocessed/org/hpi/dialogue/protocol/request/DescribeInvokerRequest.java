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
public class DescribeInvokerRequest extends SessionedRequest {
	
	private static final long serialVersionUID = 1257749502283139474L;
	
	private String					invokeId;
	
	public DescribeInvokerRequest(String sessionId, String invokeId) {
            super(sessionId);
            this.invokeId = invokeId;
	}
	
	public String getServiceName() {
            return HPIDialogueConstants.DESCRIBE_INVOKER_SERVICE;
	}
	
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
