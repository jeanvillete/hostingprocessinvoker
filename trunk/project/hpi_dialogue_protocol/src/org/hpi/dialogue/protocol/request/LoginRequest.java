package org.hpi.dialogue.protocol.request;

public class LoginRequest extends Request {

	private static final long serialVersionUID = -3484304702803121861L;
	
	private String				nickname;
	private String				passphrase;
	
	public LoginRequest(String nickname, String passphrase) {
		super();
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
