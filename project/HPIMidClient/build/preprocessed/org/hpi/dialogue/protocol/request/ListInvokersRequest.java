package org.hpi.dialogue.protocol.request;

import org.hpi.dialogue.protocol.common.HPIDialogueConstants;
import org.simplestructruedata.entities.SSDObjectLeaf;
import org.simplestructruedata.entities.SSDObjectNode;

public class ListInvokersRequest extends SessionedRequest {

    private static final long serialVersionUID = -8634182405507253147L;

    public ListInvokersRequest(String sessionId) {
        super(sessionId);
    }

    public String getServiceName() {
        return HPIDialogueConstants.LIST_INVOKERS_SERVICE;
    }

    public SSDObjectNode getSSDParameters() {
        SSDObjectNode parameters = new SSDObjectNode(HPIDialogueConstants.PARAMETERS);
        parameters.addAttribute(new SSDObjectLeaf(HPIDialogueConstants.SESSION_ID, this.getSessionId()));
        return parameters;
    }

}
