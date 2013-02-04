package org.hpi.protocol.operation;

import org.hpi.protocol.HpiProtocolConstants;
import org.simplestructruedata.data.SSDContextManager;
import org.simplestructruedata.data.SSDContextManager.SSDRootObject;
import org.simplestructruedata.entities.SSDObjectLeaf;
import org.simplestructruedata.entities.SSDObjectNode;

public abstract class Operation {

	private String				operationName;

	public Operation(String operationName) {
		this.operationName = operationName;
	}
	
	public static Operation build(String dataOperation) {
		SSDContextManager ssdCtx = SSDContextManager.build(dataOperation);
		SSDRootObject root = ssdCtx.getRootObject();
		
		// getting the operation title to infer what is the operation to perform
		String operationTitle = root.getLeaf(HpiProtocolConstants.OPERATION_TITLE).getValue();
		
		// checking what the operation implemention it's being requesting, and returning it
		if (operationTitle.equals(LoginOperation.DO_LOGIN)) {
			SSDObjectNode login = root.getNode(HpiProtocolConstants.OPERATION_DO_LOGIN_LOGIN);
			String nickname = login.getLeaf(HpiProtocolConstants.OPERATION_DO_LOGIN_NICKNAME).getValue();
			String passphrase = login.getLeaf(HpiProtocolConstants.OPERATION_DO_LOGIN_PASSPHRASE).getValue();
			return new LoginOperation(nickname, passphrase);
		} else if (operationTitle.equals(LogoffOperation.DO_LOGOFF)) {
			return new LogoffOperation(root.getLeaf(HpiProtocolConstants.OPERATION_SESSION_ID).getValue());
		} else if (operationTitle.equals(ListInvokersOperation.LIST_INVOKERS)) {
			return new ListInvokersOperation(root.getLeaf(HpiProtocolConstants.OPERATION_SESSION_ID).getValue());
		} else if (operationTitle.equals(ExecuteInvokerOperation.EXECUTE_INVOKER)) {
			String sessionId = root.getLeaf(HpiProtocolConstants.OPERATION_SESSION_ID).getValue();
			String invokerId = root.getLeaf(HpiProtocolConstants.OPERATION_EXECUTE_INVOKER_ID_INVOKER).getValue();
			return new ExecuteInvokerOperation(invokerId, sessionId);
		} else if (operationTitle.equals(ShutdownServerOperation.SHUTDOWN_SERVER)) {
			return new ShutdownServerOperation();
		} else throw new IllegalStateException("Unknow the current value of operationTitle. operationTitle: " + operationTitle);
	}

	public SSDContextManager getSSDCtx() {
		SSDContextManager ssdCtx = SSDContextManager.build();
		SSDRootObject root = ssdCtx.getRootObject();
		root.addAttribute(new SSDObjectLeaf(HpiProtocolConstants.OPERATION_TITLE, this.operationName));

		return ssdCtx;
	}
	
	@Override
	public String toString() {
		return this.getSSDCtx().toString();
	}
	
	// GETTERS AND SETTERS //
	public String getOperationName() {
		return operationName;
	}
	
}
