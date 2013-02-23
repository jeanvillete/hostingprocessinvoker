/**
 * 
 */
package org.hpi.dialogue.protocol;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hpi.dialogue.protocol.common.HPIDialogueConstants;
import org.hpi.dialogue.protocol.entities.Executable;
import org.hpi.dialogue.protocol.entities.Invoker;
import org.hpi.dialogue.protocol.entities.Parameter;
import org.hpi.dialogue.protocol.entities.User;
import org.hpi.dialogue.protocol.request.DescribeInvokerRequest;
import org.hpi.dialogue.protocol.request.ExecuteInvokerRequest;
import org.hpi.dialogue.protocol.request.ListInvokersRequest;
import org.hpi.dialogue.protocol.request.LoginRequest;
import org.hpi.dialogue.protocol.request.LogoffRequest;
import org.hpi.dialogue.protocol.request.ServerShutdownRequest;
import org.hpi.dialogue.protocol.response.DescribeInvokerResponse;
import org.hpi.dialogue.protocol.response.ExecuteInvokerResponse;
import org.hpi.dialogue.protocol.response.ListInvokersResponse;
import org.hpi.dialogue.protocol.response.LoginResponse;
import org.hpi.dialogue.protocol.response.LogoffResponse;
import org.hpi.dialogue.protocol.response.Response;
import org.hpi.dialogue.protocol.response.ServerShutdownResponse;
import org.simplestructruedata.data.SSDContextManager;
import org.simplestructruedata.data.SSDContextManager.SSDRootObject;
import org.simplestructruedata.entities.SSDObjectArray;
import org.simplestructruedata.entities.SSDObjectNode;

/**
 * @author Jean Villete
 *
 */
public abstract class HPIDialogueProtocol implements Serializable {

	private static final long serialVersionUID = -3778965788982211911L;

	/**
	 * This method must be implemented by the Request and Response classes, that those are the types of available services.
	 * @return
	 */
	public abstract String getServiceType();
	
	/**
	 * This method will be implemented for each concrete class service.
	 * @return
	 */
	public abstract String getServiceName();

	/**
	 * This method must be implemented by the Request and Response classes.
	 * @return
	 */
	public abstract SSDContextManager getSSDServiceMessage();
	
	/**
	 * This method will be implemented for each concrete class service.
	 * @return
	 */
	public abstract SSDObjectNode getSSDParameters();
	
	/**
	 * Method to parse a message and get a specific concrete Response or Request.
	 * @param message
	 * @return
	 */
	public static HPIDialogueProtocol parseMessage(String message) {
		SSDContextManager ssdCtx = SSDContextManager.build(message);
		SSDRootObject root = ssdCtx.getRootObject();

		SSDObjectNode ssdHpiMessage = root.getNode(HPIDialogueConstants.HPI_MESSAGE);
		SSDObjectNode parameters = ssdHpiMessage.getNode(HPIDialogueConstants.PARAMETERS);
		String serviceType = ssdHpiMessage.getLeaf(HPIDialogueConstants.SERVICE_TYPE).getValue();
		String serviceName = ssdHpiMessage.getLeaf(HPIDialogueConstants.SERVICE_NAME).getValue();
		
		if (serviceType.equals(HPIDialogueConstants.RESPONSE_SERVICE)) {
			String responseMessage = ssdHpiMessage.getLeaf(HPIDialogueConstants.MESSAGE).getValue();
			Response.Status responseStatus = Response.Status.valueOf(ssdHpiMessage.getLeaf(HPIDialogueConstants.STATUS).getValue());
			
			if (serviceName.equals(HPIDialogueConstants.LOGIN_SERVICE)) {
				return new LoginResponse(parameters.getLeaf(HPIDialogueConstants.SESSION_ID).getValue(), responseMessage, responseStatus);
			} else if (serviceName.equals(HPIDialogueConstants.SERVER_SHUTDOWN_SERVICE)) {
				return new ServerShutdownResponse(responseMessage, responseStatus);
			} else if (serviceName.equals(HPIDialogueConstants.LIST_INVOKERS_SERVICE)) {
				List<Invoker> listInvokers = new ArrayList<Invoker>();
				
				SSDObjectArray ssdListInvokers = parameters.getArray(HPIDialogueConstants.LIST_INVOKERS);
				for (int i = 0; i < ssdListInvokers.getSize(); i ++) {
					SSDObjectNode ssdInvoker = ssdListInvokers.getNode(i);
					Invoker invoker = new Invoker();
					invoker.setId(ssdInvoker.getLeaf(HPIDialogueConstants.ID).getValue());
					invoker.setDescription(ssdInvoker.getLeaf(HPIDialogueConstants.DESCRIPTION).getValue());
					
					SSDObjectArray ssdExecutables = ssdInvoker.getArray(HPIDialogueConstants.EXECUTABLES);
					for (int e = 0; e < ssdExecutables.getSize(); e ++) {
						SSDObjectNode ssdExecutable = ssdExecutables.getNode(e);
						Executable executable = new Executable();
						executable.setCanonicalPath(ssdExecutable.getLeaf(HPIDialogueConstants.CANONICAL_PATH).getValue());
						
						SSDObjectArray ssdParameters = ssdExecutable.getArray(HPIDialogueConstants.PARAMETERS);
						for (int p = 0; p < ssdParameters.getSize(); p ++) {
							SSDObjectNode ssdParameter = ssdParameters.getNode(p);
							Parameter parameter = new Parameter();
							parameter.setKey(ssdParameter.getLeaf(HPIDialogueConstants.KEY).getValue());
							parameter.setValue(ssdParameter.getLeaf(HPIDialogueConstants.VALUE).getValue());
							
							executable.getParameters().add(parameter);
						}
						
						invoker.getExecutables().add(executable);
					}
					
					listInvokers.add(invoker);
				}
				
				return new ListInvokersResponse(listInvokers, responseMessage, responseStatus);
			} else if (serviceName.equals(HPIDialogueConstants.DESCRIBE_INVOKER_SERVICE)) {
				SSDObjectNode ssdInvoker = parameters.getNode(HPIDialogueConstants.INVOKER);
				Invoker invoker = new Invoker();
				invoker.setId(ssdInvoker.getLeaf(HPIDialogueConstants.ID).getValue());
				invoker.setDescription(ssdInvoker.getLeaf(HPIDialogueConstants.DESCRIPTION).getValue());
				
				SSDObjectArray ssdExecutables = ssdInvoker.getArray(HPIDialogueConstants.EXECUTABLES);
				for (int e = 0; e < ssdExecutables.getSize(); e ++) {
					SSDObjectNode ssdExecutable = ssdExecutables.getNode(e);
					Executable executable = new Executable();
					executable.setCanonicalPath(ssdExecutable.getLeaf(HPIDialogueConstants.CANONICAL_PATH).getValue());
					
					SSDObjectArray ssdParameters = ssdExecutable.getArray(HPIDialogueConstants.PARAMETERS);
					for (int p = 0; p < ssdParameters.getSize(); p ++) {
						SSDObjectNode ssdParameter = ssdParameters.getNode(p);
						Parameter parameter = new Parameter();
						parameter.setKey(ssdParameter.getLeaf(HPIDialogueConstants.KEY).getValue());
						parameter.setValue(ssdParameter.getLeaf(HPIDialogueConstants.VALUE).getValue());
						
						executable.getParameters().add(parameter);
					}
					
					invoker.getExecutables().add(executable);
				}
				
				return new DescribeInvokerResponse(invoker, responseMessage, responseStatus);
			} else if (serviceName.equals(HPIDialogueConstants.LOGOFF_SERVICE)) {
				return new LogoffResponse(responseMessage, responseStatus);
			} else if (serviceName.equals(HPIDialogueConstants.EXECUTE_INVOKER_SERVICE)) {
				return new ExecuteInvokerResponse(responseMessage, responseStatus);
			} else throw new RuntimeException("Unknow value to " + HPIDialogueConstants.SERVICE_NAME + ", the value is: " + serviceName);
		} else if (serviceType.equals(HPIDialogueConstants.REQUEST_SERVICE)) {
			if (serviceName.equals(HPIDialogueConstants.LOGIN_SERVICE)) {
				return new LoginRequest(
					new User(
						parameters.getLeaf(HPIDialogueConstants.NICKNAME).getValue(),
						parameters.getLeaf(HPIDialogueConstants.PASSPHRASE).getValue()));
			} else if (serviceName.equals(HPIDialogueConstants.SERVER_SHUTDOWN_SERVICE)) {
				return new ServerShutdownRequest();
			} else if (serviceName.equals(HPIDialogueConstants.LIST_INVOKERS_SERVICE)) {
				return new ListInvokersRequest(parameters.getLeaf(HPIDialogueConstants.SESSION_ID).getValue());
			} else if (serviceName.equals(HPIDialogueConstants.DESCRIBE_INVOKER_SERVICE)) {
				return new DescribeInvokerRequest(parameters.getLeaf(HPIDialogueConstants.SESSION_ID).getValue(), parameters.getLeaf(HPIDialogueConstants.INVOKER_ID).getValue());
			} else if (serviceName.equals(HPIDialogueConstants.LOGOFF_SERVICE)) {
				return new LogoffRequest(parameters.getLeaf(HPIDialogueConstants.SESSION_ID).getValue());
			} else if (serviceName.equals(HPIDialogueConstants.EXECUTE_INVOKER_SERVICE)) {
				return new ExecuteInvokerRequest(parameters.getLeaf(HPIDialogueConstants.SESSION_ID).getValue(), parameters.getLeaf(HPIDialogueConstants.INVOKER_ID).getValue());
			} else throw new RuntimeException("Unknow value to " + HPIDialogueConstants.SERVICE_NAME + ", the value is: " + serviceName);
		} else throw new RuntimeException("Unknow value to " + HPIDialogueConstants.SERVICE_TYPE + ", the value is: " + serviceType);
	}
}
