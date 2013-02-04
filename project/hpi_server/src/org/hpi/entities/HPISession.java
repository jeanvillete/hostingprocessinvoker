/**
 * 
 */
package org.hpi.entities;

import org.com.tatu.helper.parameter.Parameter;

/**
 * @author Jean Villete
 *
 */
public class HPISession {

	private User				user;
	private String				remoteAddress;
	private String				session_id;
	
	public HPISession(User user, String	remoteAddress) {
		super();
		Parameter.check(user).notNull();
		Parameter.check(remoteAddress).notNull().notEmpty();
		
		this.user = user;
		this.remoteAddress = remoteAddress;
		this.session_id = "" + System.currentTimeMillis() + "" + remoteAddress.hashCode();
	}

	// GETTERS AND SETTERS //
	public User getUser() {
		return user;
	}

	public String getSession_id() {
		return session_id;
	}

	public String getRemoteAddress() {
		return remoteAddress;
	}
}
