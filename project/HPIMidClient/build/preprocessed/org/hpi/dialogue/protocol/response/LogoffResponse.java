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
public class LogoffResponse extends Response {

    private static final long serialVersionUID = 7568235549525882362L;

    public LogoffResponse(String message, Status status) {
        super(message, status);
    }

    public String getServiceName() {
        return HPIDialogueConstants.LOGOFF_SERVICE;
    }

    public SSDObjectNode getSSDParameters() {
        return new SSDObjectNode(HPIDialogueConstants.PARAMETERS);
    }
}
