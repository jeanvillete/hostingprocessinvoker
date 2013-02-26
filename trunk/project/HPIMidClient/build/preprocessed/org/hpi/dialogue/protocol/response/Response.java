/**
 * 
 */
package org.hpi.dialogue.protocol.response;

import org.hpi.dialogue.protocol.HPIDialogueProtocol;
import org.hpi.dialogue.protocol.common.HPIDialogueConstants;
import org.simplestructruedata.data.SSDContextManager;
import org.simplestructruedata.entities.SSDObjectLeaf;
import org.simplestructruedata.entities.SSDObjectNode;

/**
 * @author Jean Villete
 *
 */
public abstract class Response extends HPIDialogueProtocol {

    private static final long serialVersionUID = -6766462095419348960L;

    public static class Status {
        
        public static final String SUCCESS = "SUCCESS";
        public static final String FAIL = "FAIL";
        
        private Status(String value) {
            this.value = value;
        }
        
        public boolean equals(Object obj) {
            return this.value.equals(obj);
        }
        
        private String value;
        
        public String getValue() {
            return this.value;
        }
        
        public static Status valueOf(String value) {
            if (value.equals(SUCCESS)) {
                return new Status(SUCCESS);
            } else if (value.equals(FAIL)) {
                return new Status(FAIL);
            } else throw new RuntimeException("Unknow the value");
        }
    }

    private String					message;
    private Status					status;

    public Response(String message, Status status) {
        super();
        this.message = message;
        this.status = status;
    }

    public String getServiceType() {
        return HPIDialogueConstants.RESPONSE_SERVICE;
    }

    public SSDContextManager getSSDServiceMessage() {
        SSDContextManager ssdCtx = SSDContextManager.build();
        SSDObjectNode hpiMessage = new SSDObjectNode(HPIDialogueConstants.HPI_MESSAGE);
        hpiMessage.addAttribute(new SSDObjectLeaf(HPIDialogueConstants.SERVICE_TYPE, this.getServiceType()));
        hpiMessage.addAttribute(new SSDObjectLeaf(HPIDialogueConstants.SERVICE_NAME, this.getServiceName()));
        hpiMessage.addAttribute(new SSDObjectLeaf(HPIDialogueConstants.MESSAGE, this.getMessage()));
        hpiMessage.addAttribute(new SSDObjectLeaf(HPIDialogueConstants.STATUS, this.getStatus().toString()));
        hpiMessage.addAttribute(this.getSSDParameters());
        ssdCtx.getRootObject().addAttribute(hpiMessage);
        return ssdCtx;
    }

    // GETTERS AND SETTERS //
    public String getMessage() {
        return message;
    }
    public Status getStatus() {
        return status;
    }
	
}
