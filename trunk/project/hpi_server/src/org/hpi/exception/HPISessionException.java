/**
 * 
 */
package org.hpi.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jean Villete
 *
 */
public class HPISessionException extends HPIException {
	
	private static final long serialVersionUID = -1286745632007393582L;
	
	private List<String>                messages = new ArrayList<String>();
	
	public HPISessionException() {
	}
	
	public HPISessionException(String message) {
		this.appendMessage(message);
	}
	
	public void appendMessage(String message) {
        this.messages.add(message);
	}
	
}
