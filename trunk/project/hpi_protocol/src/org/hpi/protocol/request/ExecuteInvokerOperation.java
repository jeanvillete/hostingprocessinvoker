/**
 * 
 */
package org.hpi.protocol.request;

import org.hpi.protocol.HpiProtocolConstants;
import org.simplestructruedata.data.SSDContextManager;
import org.simplestructruedata.data.SSDContextManager.SSDRootObject;
import org.simplestructruedata.entities.SSDObjectLeaf;

/**
 * @author carrefour
 *
 */
public class ExecuteInvokerOperation extends Operation {

	public static final String				EXECUTE_INVOKER = "execute_invoker";
	
	private String							invokerId;
	
	public ExecuteInvokerOperation(String invokerId) {
		super(EXECUTE_INVOKER);
		this.invokerId = invokerId;
	}

	@Override
	public String toString() {
		SSDContextManager ssdCtx = this.getSSDCtx();
		SSDRootObject root = ssdCtx.getRootObject();
		root.addAttribute(new SSDObjectLeaf(HpiProtocolConstants.OPERATION_EXECUTE_INVOKER_ID_INVOKER, this.invokerId));
		
		return ssdCtx.toString();
	}

	// GETTERS AND SETTERS //
	public String getInvokerId() {
		return invokerId;
	}
}
