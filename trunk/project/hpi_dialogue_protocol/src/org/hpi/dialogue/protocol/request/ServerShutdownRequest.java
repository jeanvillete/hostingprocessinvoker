package org.hpi.dialogue.protocol.request;

import org.hpi.dialogue.protocol.common.HPIDialogueConstants;
import org.simplestructruedata.entities.SSDObjectNode;

public class ServerShutdownRequest extends Request {

	private static final long serialVersionUID = 6846531282892118159L;

	@Override
	public String getServiceName() {
		return HPIDialogueConstants.SERVER_SHUTDOWN_SERVICE;
	}
	
	@Override
	public SSDObjectNode getSSDParameters() {
		return new SSDObjectNode(HPIDialogueConstants.PARAMETERS);
	}
}
