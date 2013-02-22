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
	
	public enum Status {
		SUCCESS,
		FAIL
	}
	
	private String					message;
	private Status					status;
	
	public Response(String message, Status status) {
		super();
		this.message = message;
		this.status = status;
	}
	
	@Override
	public String getServiceType() {
		return HPIDialogueConstants.RESPONSE_SERVICE;
	}
	
	@Override
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
