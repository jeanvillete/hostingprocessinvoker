package org.hpi.dialogue.protocol.request;

import org.hpi.dialogue.protocol.common.HPIDialogueConstants;
import org.simplestructruedata.entities.SSDObjectLeaf;
import org.simplestructruedata.entities.SSDObjectNode;

public class LogoffRequest extends SessionedRequest {

    private static final long serialVersionUID = 7946461900230061913L;

    public LogoffRequest(String sessionId) {
        super(sessionId);
    }

    public String getServiceName() {
        return HPIDialogueConstants.LOGOFF_SERVICE;
    }

    public SSDObjectNode getSSDParameters() {
        SSDObjectNode parameters = new SSDObjectNode(HPIDialogueConstants.PARAMETERS);
        parameters.addAttribute(new SSDObjectLeaf(HPIDialogueConstants.SESSION_ID, this.getSessionId()));
        return parameters;
    }

}
