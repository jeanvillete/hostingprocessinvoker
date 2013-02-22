package org.hpi.dialogue.protocol.request;

import org.hpi.dialogue.protocol.common.HPIDialogueConstants;
import org.hpi.dialogue.protocol.entities.User;
import org.simplestructruedata.entities.SSDObjectLeaf;
import org.simplestructruedata.entities.SSDObjectNode;

public class LoginRequest extends Request {
	
	private static final long serialVersionUID = 2289874317473885068L;
	
	private User 					user;
	
	public LoginRequest(User user) {
		super();
		this.user = user;
	}

	@Override
	public String getServiceName() {
		return HPIDialogueConstants.LOGIN_SERVICE;
	}
	
	@Override
	public SSDObjectNode getSSDParameters() {
		SSDObjectNode parameters = new SSDObjectNode(HPIDialogueConstants.PARAMETERS);
		parameters.addAttribute(new SSDObjectLeaf(HPIDialogueConstants.NICKNAME, this.getUser().getNickname()));
		parameters.addAttribute(new SSDObjectLeaf(HPIDialogueConstants.PASSPHRASE, this.getUser().getPassphrase()));
		return parameters;
	}
	
	// GETTERS AND SETTERS //
	public User getUser() {
		return user;
	}

}
