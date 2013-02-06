package org.hpi.dialogue.protocol.request;

import org.hpi.dialogue.protocol.entities.User;

public class LoginRequest extends Request {

	private static final long serialVersionUID = 2289874317473885068L;
	
	private User 					user;
	
	public LoginRequest(User user) {
		super();
		this.user = user;
	}

	// GETTERS AND SETTERS //
	public User getUser() {
		return user;
	}
	
}
