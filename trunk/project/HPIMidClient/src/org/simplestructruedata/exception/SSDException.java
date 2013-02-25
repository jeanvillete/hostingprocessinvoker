package org.simplestructruedata.exception;

import java.util.Enumeration;
import java.util.Vector;

public class SSDException extends RuntimeException {
	
    private static final long serialVersionUID = 2966485954767238164L;

    private Vector 			messages = new Vector();

    public SSDException() { }

    public SSDException(String message) {
            this.appendMessage(message);
    }

    public SSDException(Exception e) {
            this.appendMessage(e.getMessage());
    }

    public void appendMessage(String message) {
            this.messages.addElement(message);
    }
	
    public String getMessage() {
        StringBuffer mensagem = new StringBuffer();
        Enumeration enumeration = this.messages.elements();
    	while(enumeration.hasMoreElements()){
            String msg = (String) enumeration.nextElement();
            mensagem.append(msg);
            if(enumeration.hasMoreElements()){
                mensagem.append("\n");    			
            }
    	}
        return mensagem.toString();
    }

}
