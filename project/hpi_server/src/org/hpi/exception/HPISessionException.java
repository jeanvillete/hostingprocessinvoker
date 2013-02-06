/**
 * 
 */
package org.hpi.exception;


/**
 * @author Jean Villete
 *
 */
public class HPISessionException extends HPIException {
	
	private static final long serialVersionUID = -3756455779607693382L;

	public HPISessionException() {
	}
	
	public HPISessionException(String message) {
		this.appendMessage(message);
	}
	
	public void appendMessage(String message) {
		this.appendMessage(message);
	}
	
}
