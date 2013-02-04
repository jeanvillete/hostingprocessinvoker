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
public class LogoffOperation extends Operation {

	public static final String				DO_LOGOFF = "do_logoff";
	
	private String							sessionId;
	
	public LogoffOperation(String sessionId) {
		super(DO_LOGOFF);
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
