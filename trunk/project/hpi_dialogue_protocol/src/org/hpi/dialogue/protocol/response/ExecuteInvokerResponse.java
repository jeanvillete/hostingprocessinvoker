/**
 * 
 */
package org.hpi.dialogue.protocol.response;

import org.hpi.dialogue.protocol.common.HPIDialogueConstants;
import org.simplestructruedata.entities.SSDObjectNode;

/**
 * @author Jean Villete
 *
 */
public class ExecuteInvokerResponse extends Response {

	private static final long serialVersionUID = 2655139286365237415L;

	public ExecuteInvokerResponse(String message, Status status) {
		super(message, status);
	}

	@Override
	public String getServiceName() {
		return HPIDialogueConstants.EXECUTE_INVOKER_SERVICE;
	}

	@Override
	public SSDObjectNode getSSDParameters() {
		return new SSDObjectNode(HPIDialogueConstants.PARAMETERS);
	}
}
