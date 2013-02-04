package org.hpi.protocol.response;

import java.util.List;

import org.hpi.protocol.HpiProtocolConstants;
import org.simplestructruedata.data.SSDContextManager;
import org.simplestructruedata.data.SSDContextManager.SSDRootObject;
import org.simplestructruedata.entities.SSDObjectArray;
import org.simplestructruedata.entities.SSDObjectLeaf;

public class ListInvokersResponse extends Response {

	public static final String					LIST_INVOKERS = "list_invokers";
	
	private List<String>						listInvokers;
	
	public ListInvokersResponse(String statusCode, String message, List<String> listInvokers) {
		super(LIST_INVOKERS, statusCode, message);
		this.listInvokers = listInvokers;
	}
	
	@Override
	public String toString() {
		SSDContextManager ssdCtx = this.getSSDCtx();
		SSDRootObject root = ssdCtx.getRootObject();
		SSDObjectArray invokers = new SSDObjectArray(HpiProtocolConstants.RESPONSE_INVOKERS);
		if (this.listInvokers != null) {
			for (String invoker : this.listInvokers) {
				invokers.addElement(new SSDObjectLeaf(invoker, invoker));
			}
		}
		root.addAttribute(invokers);
		
		return ssdCtx.toString();
	}

	// GETTERS AND SETTERS //
	public List<String> getListInvokers() {
		return listInvokers;
	}

}
