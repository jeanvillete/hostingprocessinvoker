package org.hpi.dialogue.protocol.response;

import java.util.List;

import org.hpi.dialogue.protocol.common.HPIDialogueConstants;
import org.hpi.dialogue.protocol.entities.Executable;
import org.hpi.dialogue.protocol.entities.Invoker;
import org.hpi.dialogue.protocol.entities.Parameter;
import org.simplestructruedata.entities.SSDObjectArray;
import org.simplestructruedata.entities.SSDObjectLeaf;
import org.simplestructruedata.entities.SSDObjectNode;

public class ListInvokersResponse extends Response {

	private static final long serialVersionUID = -5143130097598698256L;
	
	private List<Invoker>					listInvokers;
	
	public ListInvokersResponse(List<Invoker> listInvokers, String message, Status status) {
		super(message, status);
		this.listInvokers = listInvokers;
	}

	@Override
	public String getServiceName() {
		return HPIDialogueConstants.LIST_INVOKERS_SERVICE;
	}
	
	@Override
	public SSDObjectNode getSSDParameters() {
		SSDObjectNode parameters = new SSDObjectNode(HPIDialogueConstants.PARAMETERS);
		SSDObjectArray listInvokers = new SSDObjectArray(HPIDialogueConstants.LIST_INVOKERS);
		if (this.listInvokers != null && this.listInvokers.size() > 0) {
			for (Invoker invoker : this.listInvokers) {
				SSDObjectNode ssdInvoker = new SSDObjectNode(HPIDialogueConstants.INVOKER);
				ssdInvoker.addAttribute(new SSDObjectLeaf(HPIDialogueConstants.ID, invoker.getId()));
				ssdInvoker.addAttribute(new SSDObjectLeaf(HPIDialogueConstants.DESCRIPTION, invoker.getDescription()));
				
				SSDObjectArray ssdExecutables = new SSDObjectArray(HPIDialogueConstants.EXECUTABLES);
				if (invoker.getExecutables() != null && invoker.getExecutables().size() > 0) {
					for (Executable executable : invoker.getExecutables()) {
						SSDObjectNode ssdExecutable = new SSDObjectNode(HPIDialogueConstants.EXECUTABLE);
						ssdExecutable.addAttribute(new SSDObjectLeaf(HPIDialogueConstants.CANONICAL_PATH, executable.getCanonicalPath()));
						
						SSDObjectArray ssdParemeters = new SSDObjectArray(HPIDialogueConstants.PARAMETERS);
						if (executable.getParameters() != null && executable.getParameters().size() > 0) {
							for (Parameter parameter : executable.getParameters()) {
								SSDObjectNode ssdParemeter = new SSDObjectNode(HPIDialogueConstants.PARAMETER);
								ssdParemeter.addAttribute(new SSDObjectLeaf(HPIDialogueConstants.KEY, parameter.getKey()));
								ssdParemeter.addAttribute(new SSDObjectLeaf(HPIDialogueConstants.VALUE, parameter.getValue()));

								ssdParemeters.addElement(ssdParemeter);
							}
						}
						
						ssdExecutable.addAttribute(ssdParemeters);
						ssdExecutables.addElement(ssdExecutable);
					}
				}
				
				ssdInvoker.addAttribute(ssdExecutables);
				listInvokers.addElement(ssdInvoker);
			}
		}
		parameters.addAttribute(listInvokers);
		return parameters;
	}

	// GETTERS AND SETTERS //
	public List<Invoker> getListInvokers() {
		return listInvokers;
	}


}
