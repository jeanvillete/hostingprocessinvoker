/**
 * 
 */
package org.hpi.dialogue.protocol.response;

import org.hpi.dialogue.protocol.HPIDialogueProtocol;

/**
 * @author villjea
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
	
	// GETTERS AND SETTERS //
	public String getMessage() {
		return message;
	}
	public Status getStatus() {
		return status;
	}
	
}
