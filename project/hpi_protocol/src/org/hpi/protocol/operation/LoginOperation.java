/**
 * 
 */
package org.hpi.protocol.operation;

import org.hpi.protocol.HpiProtocolConstants;
import org.simplestructruedata.data.SSDContextManager;
import org.simplestructruedata.data.SSDContextManager.SSDRootObject;
import org.simplestructruedata.entities.SSDObjectLeaf;
import org.simplestructruedata.entities.SSDObjectNode;

/**
 * @author Jean Villete
 *
 */
public class LoginOperation extends Operation {

	public static final String				DO_LOGIN = "do_login";
	
	private String							nickname;
	private String							passphrase;
	
	public LoginOperation(String nickname, String passphrase) {
		super(DO_LOGIN);
		this.nickname = nickname;
		this.passphrase = passphrase;
	}

	@Override
	public String toString() {
		SSDContextManager ssdCtx = this.getSSDCtx();
		SSDRootObject root = ssdCtx.getRootObject();
		SSDObjectNode login = new SSDObjectNode(HpiProtocolConstants.OPERATION_DO_LOGIN_LOGIN);
		login.addAttribute(new SSDObjectLeaf(HpiProtocolConstants.OPERATION_DO_LOGIN_NICKNAME, this.nickname));
		login.addAttribute(new SSDObjectLeaf(HpiProtocolConstants.OPERATION_DO_LOGIN_PASSPHRASE, this.passphrase));
		root.addAttribute(login);
		
		return ssdCtx.toString();
	}

	// GETTERS AND SETTERS //
	public String getNickname() {
		return nickname;
	}

	public String getPassphrase() {
		return passphrase;
	}
	
}
