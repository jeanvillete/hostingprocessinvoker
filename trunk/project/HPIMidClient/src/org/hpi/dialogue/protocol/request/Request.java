/**
 * 
 */
package org.hpi.dialogue.protocol.request;

import org.hpi.dialogue.protocol.HPIDialogueProtocol;
import org.hpi.dialogue.protocol.common.HPIDialogueConstants;
import org.simplestructruedata.data.SSDContextManager;
import org.simplestructruedata.entities.SSDObjectLeaf;
import org.simplestructruedata.entities.SSDObjectNode;

/**
 * @author Jean Villete
 *
 */
public abstract class Request extends HPIDialogueProtocol {

    private static final long serialVersionUID = -7993633865449661875L;

    public String getServiceType() {
        return HPIDialogueConstants.REQUEST_SERVICE;
    }

    public SSDContextManager getSSDServiceMessage() {
        SSDContextManager ssdCtx = SSDContextManager.build();
        SSDObjectNode hpiMessage = new SSDObjectNode(HPIDialogueConstants.HPI_MESSAGE);
        hpiMessage.addAttribute(new SSDObjectLeaf(HPIDialogueConstants.SERVICE_TYPE, this.getServiceType()));
        hpiMessage.addAttribute(new SSDObjectLeaf(HPIDialogueConstants.SERVICE_NAME, this.getServiceName()));
        hpiMessage.addAttribute(this.getSSDParameters());
        ssdCtx.getRootObject().addAttribute(hpiMessage);
        return ssdCtx;
    }

}
