package org.hpi.protocol.response;

import java.util.ArrayList;
import java.util.List;

import org.hpi.protocol.HpiProtocolConstants;
import org.simplestructruedata.data.SSDContextManager;
import org.simplestructruedata.data.SSDContextManager.SSDRootObject;
import org.simplestructruedata.entities.SSDObjectLeaf;
import org.simplestructruedata.entities.SSDObjectNode;

public abstract class Response {

	private String				goal;
	private String				statusCode;
	private String				message;
	
	public Response(String goal, String statusCode, String message) {
		this.goal = goal;
		this.statusCode = statusCode;
		this.message = message;
	}

	public static Response build(String dataOperation) {
		SSDContextManager ssdCtx = SSDContextManager.build(dataOperation);
		SSDRootObject root = ssdCtx.getRootObject();
		
		// getting goal to mount the Response
		SSDObjectNode ssdResponse = root.getNode(HpiProtocolConstants.RESPONSE_TITLE);
		String goal = ssdResponse.getLeaf(HpiProtocolConstants.RESPONSE_GOAL).getValue();
		String statusCode = ssdResponse.getLeaf(HpiProtocolConstants.RESPONSE_STATUS_CODE).getValue();
		String message = ssdResponse.getLeaf(HpiProtocolConstants.RESPONSE_MESSAGE).getValue();
		
		if (goal.equals(ServerRunningResponse.SERVER_RUNNING)) {
			return new ServerRunningResponse(statusCode, message);
		} else if (goal.equals(LoginResponse.LOGIN)) {
			String session_id = root.getNode(HpiProtocolConstants.RESPONSE_LOGIN).getLeaf(HpiProtocolConstants.RESPONSE_LOGIN_SESSION_ID).getValue();
			return new LoginResponse(statusCode, message, session_id);
		} else if (goal.equals(ListInvokersResponse.LIST_INVOKERS)) {
			List<String> listInvokers = new ArrayList<String>();
			for (int i = 0; i < root.getArray(HpiProtocolConstants.RESPONSE_INVOKERS).getSize(); i++) {
				SSDObjectLeaf ssdInvoker = root.getArray(HpiProtocolConstants.RESPONSE_INVOKERS).getLeaf(i);
				listInvokers.add(ssdInvoker.getValue());
			}
			return new ListInvokersResponse(statusCode, message, listInvokers);
		} else if (goal.equals(ExecuteInvokerResponse.EXECUTE_INVOKER)) {
			return new ExecuteInvokerResponse(statusCode, message);
		} else throw new IllegalStateException("Unknow the goal value. goal: " + goal);
	}
	
	public SSDContextManager getSSDCtx() {
		SSDContextManager ssdCtx = SSDContextManager.build();
		SSDRootObject root = ssdCtx.getRootObject();
		SSDObjectNode response = new SSDObjectNode(HpiProtocolConstants.RESPONSE_TITLE);
		response.addAttribute(new SSDObjectLeaf(HpiProtocolConstants.RESPONSE_GOAL, this.goal));
		response.addAttribute(new SSDObjectLeaf(HpiProtocolConstants.RESPONSE_STATUS_CODE, this.statusCode));
		response.addAttribute(new SSDObjectLeaf(HpiProtocolConstants.RESPONSE_MESSAGE, this.message));
		root.addAttribute(response);
		
		return ssdCtx;
	}
	
	@Override
	public String toString() {
		return this.getSSDCtx().toString();
	}
	
	// GETTERS AND SETTERS //
	public String getGoal() {
		return goal;
	}
	public void setGoal(String goal) {
		this.goal = goal;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
