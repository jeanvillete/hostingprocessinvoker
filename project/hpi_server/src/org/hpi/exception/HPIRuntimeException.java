/**
 * 
 */
package org.hpi.exception;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Jean Villete
 *
 */
public class HPIRuntimeException extends RuntimeException {
	
	private static final long serialVersionUID = 5127528292122947329L;
	
	private List<String>                messages = new ArrayList<String>();
	
	public HPIRuntimeException() {
	}
	
	public HPIRuntimeException(String message) {
		this.appendMessage(message);
	}
	
	public HPIRuntimeException(Exception e) {
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
