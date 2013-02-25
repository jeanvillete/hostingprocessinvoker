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
public class ServerShutdownResponse extends Response {

    private static final long serialVersionUID = -9158103958729952002L;

    public ServerShutdownResponse(String message, Status status) {
        super(message, status);
    }

    public String getServiceName() {
        return HPIDialogueConstants.SERVER_SHUTDOWN_SERVICE;
    }

    public SSDObjectNode getSSDParameters() {
        return new SSDObjectNode(HPIDialogueConstants.PARAMETERS);
    }

}
