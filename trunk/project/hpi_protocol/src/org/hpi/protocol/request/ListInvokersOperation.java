/**
 * 
 */
package org.hpi.protocol.request;

import org.hpi.protocol.HpiProtocolConstants;
import org.simplestructruedata.data.SSDContextManager;
import org.simplestructruedata.data.SSDContextManager.SSDRootObject;
import org.simplestructruedata.entities.SSDObjectLeaf;

/**
 * @author Jean Villete
 *
 */
public class ListInvokersOperation extends Operation {

	public static final String				LIST_INVOKERS = "list_invokers";
	
	private String							sessionId;
	
	public ListInvokersOperation(String sessionId) {
		super(LIST_INVOKERS);
		this.sessionId = sessionId;
	}

	@Override
	public String toString() {
		SSDContextManager ssdCtx = this.getSSDCtx();
		SSDRootObject root = ssdCtx.getRootObject();
		root.addAttribute(new SSDObjectLeaf(HpiProtocolConstants.OPERATION_SESSION_ID, this.sessionId));
		
		return ssdCtx.toString();
	}

	// GETTERS AND SETTERS //
	public String getSessionId() {
		return sessionId;
	}
	
}
