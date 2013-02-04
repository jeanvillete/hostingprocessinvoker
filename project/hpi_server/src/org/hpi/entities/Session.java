/**
 * 
 */
package org.hpi.entities;

import org.com.tatu.helper.parameter.Parameter;

/**
 * @author Jean Villete
 *
 */
public class Session {

	private User				user;
	private String				ip_client;
	private String				mac_client;
	private String				session_id;
	
	public Session(User user, String ip_client, String mac_client) {
		super();
		Parameter.check(user).notNull();
		Parameter.check(ip_client, mac_client).notNull().notEmpty();
		
		this.user = user;
		this.ip_client = ip_client;
		this.mac_client = mac_client;
		this.session_id = "" + System.currentTimeMillis() + "" + ip_client.hashCode() + "" + mac_client.hashCode();
	}

	// GETTERS AND SETTERS //
	public User getUser() {
		return user;
	}

	public String getIp_client() {
		return ip_client;
	}

	public String getMac_client() {
		return mac_client;
	}

	public String getSession_id() {
		return session_id;
	}
}
