/**
 * 
 */
package org.hpi.dialogue.protocol.response;

import org.hpi.dialogue.protocol.common.HPIDialogueConstants;
import org.simplestructruedata.entities.SSDObjectLeaf;
import org.simplestructruedata.entities.SSDObjectNode;

/**
 * @author Jean Villete
 *
 */
public class LoginResponse extends Response {

    private static final long serialVersionUID = 4706991214044107045L;

    private String					sessionId;

    public LoginResponse(String sessionId, String message, Status status) {
        super(message, status);
        this.sessionId = sessionId;
    }

    public String getServiceName() {
        return HPIDialogueConstants.LOGIN_SERVICE;
    }

    public SSDObjectNode getSSDParameters() {
        SSDObjectNode parameters = new SSDObjectNode(HPIDialogueConstants.PARAMETERS);
        parameters.addAttribute(new SSDObjectLeaf(HPIDialogueConstants.SESSION_ID, this.getSessionId()));
        return parameters;
    }

    // GETTERS AND SETTERS //
    public String getSessionId() {
        return sessionId;
    }

}
