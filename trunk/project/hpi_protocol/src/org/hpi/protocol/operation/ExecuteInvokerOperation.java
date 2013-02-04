/**
 * 
 */
package org.hpi.protocol.operation;

import org.hpi.protocol.HpiProtocolConstants;
import org.simplestructruedata.data.SSDContextManager;
import org.simplestructruedata.data.SSDContextManager.SSDRootObject;
import org.simplestructruedata.entities.SSDObjectLeaf;

/**
 * @author Jean Villete
 *
 */
public class ExecuteInvokerOperation extends Operation {

	public static final String				EXECUTE_INVOKER = "execute_invoker";
	
	private String							invokerId;
	private String							sessionId;
	
	public ExecuteInvokerOperation(String invokerId, String sessionId) {
		super(EXECUTE_INVOKER);
		this.invokerId = invokerId;
		this.sessionId = sessionId;
	}

	@Override
	public String toString() {
		SSDContextManager ssdCtx = this.getSSDCtx();
		SSDRootObject root = ssdCtx.getRootObject();
		root.addAttribute(new SSDObjectLeaf(HpiProtocolConstants.OPERATION_EXECUTE_INVOKER_ID_INVOKER, this.invokerId));
		root.addAttribute(new SSDObjectLeaf(HpiProtocolConstants.OPERATION_SESSION_ID, this.sessionId));
		
		return ssdCtx.toString();
	}

	// GETTERS AND SETTERS //
	public String getInvokerId() {
		return invokerId;
	}

	public String getSessionId() {
		return sessionId;
	}
}
