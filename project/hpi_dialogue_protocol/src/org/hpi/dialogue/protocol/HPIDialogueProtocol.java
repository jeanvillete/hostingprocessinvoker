/**
 * 
 */
package org.hpi.dialogue.protocol;

import java.io.Serializable;

import org.simplestructruedata.data.SSDContextManager;
import org.simplestructruedata.entities.SSDObjectNode;

/**
 * @author Jean Villete
 *
 */
public abstract class HPIDialogueProtocol implements Serializable {

	private static final long serialVersionUID = -3778965788982211911L;

	/**
	 * This method must be implemented by the Request and Response classes, that those are the types of available services.
	 * @return
	 */
	public abstract String getServiceType();
	
	/**
	 * This method will be implemented for each concrete class service.
	 * @return
	 */
	public abstract String getServiceName();

	/**
	 * This method must be implemented by the Request and Response classes.
	 * @return
	 */
	public abstract SSDContextManager getSSDServiceMessage();
	
	/**
	 * This method will be implemented for each concrete class service.
	 * @return
	 */
	public abstract SSDObjectNode getSSDParameters();
	
	/**
	 * Method to parse a message and get a specific concrete Response or Request.
	 * @param message
	 * @return
	 */
	public static HPIDialogueProtocol parseMessage(String message) {
		// TODO not implemented yet
		return null;
	}
}
