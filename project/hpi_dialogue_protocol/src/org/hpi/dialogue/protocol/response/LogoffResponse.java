/**
 * 
 */
package org.hpi.dialogue.protocol.response;

/**
 * @author villjea
 *
 */
public class LogoffResponse extends Response {

	private static final long serialVersionUID = 7568235549525882362L;

	public LogoffResponse(String message, Status status) {
		super(message, status);
	}

}
