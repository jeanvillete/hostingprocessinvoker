/**
 * 
 */
package org.hpi.entities;

import org.com.tatu.helper.parameter.Parameter;

/**
 * @author Jean Villete
 *
 */
public class User {

	private String					nickname;
	private String					passphrase;
	
	public User(String nickname, String passphrase) {
		Parameter.check(nickname, passphrase).notNull().notEmpty();
		
		this.nickname = nickname;
		this.passphrase = passphrase;
	}
	
	// GETTERS AND SETTERS //
	public String getNickname() {
		return nickname;
	}
	public String getPassphrase() {
		return passphrase;
	}
	
}
