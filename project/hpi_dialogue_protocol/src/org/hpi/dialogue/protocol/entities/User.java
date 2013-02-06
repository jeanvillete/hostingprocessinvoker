/**
 * 
 */
package org.hpi.dialogue.protocol.entities;

import java.io.Serializable;

/**
 * @author Jean Villete
 *
 */
public class User implements Serializable {

	private static final long serialVersionUID = 2175609209630322409L;
	
	private String					nickname;
	private String					passphrase;
	
	public User(String nickname, String passphrase) {
		if (nickname == null || nickname.isEmpty() || passphrase == null || passphrase.isEmpty()) {
			throw new IllegalArgumentException("Parameter cann't be null or empty.");
		}
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
