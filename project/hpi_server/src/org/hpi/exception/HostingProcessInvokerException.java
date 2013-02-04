/**
 * 
 */
package org.hpi.exception;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author villjea
 *
 */
public class HostingProcessInvokerException extends RuntimeException {
	
	private static final long serialVersionUID = -8622467051630489773L;
	
	private List<String>                messages = new ArrayList<String>();
	
	public HostingProcessInvokerException() {
	}
	
	public HostingProcessInvokerException(String message) {
		this.appendMessage(message);
	}
	
	public HostingProcessInvokerException(Exception e) {
		super(e.getMessage(), e);
	}
	
	public void appendMessage(String message) {
        this.messages.add(message);
	}
	
	public String getMessage() {
        StringBuffer mensagem = new StringBuffer();
		Iterator<String> iter = this.messages.iterator();
		while(iter.hasNext()){
	        String msg = iter.next();
	        mensagem.append(msg);
	        if(iter.hasNext()){
                mensagem.append("\n");                          
	        }
		}
		return mensagem.toString();
	}
}
