/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpi.mid.ui;

import java.util.Date;
import javax.microedition.lcdui.*;
import javax.microedition.midlet.MIDlet;
import org.hpi.dialogue.protocol.common.HPIUtil;
import org.hpi.dialogue.protocol.entities.User;
import org.hpi.dialogue.protocol.response.ExecuteInvokerResponse;
import org.hpi.dialogue.protocol.response.LoginResponse;
import org.hpi.dialogue.protocol.response.Response;
import org.hpi.dialogue.protocol.response.ServerShutdownResponse;
import org.hpi.dialogue.protocol.service.HPIClientProtocol;

/**
 *
 * @author villjea
 */
public class HPIUiStatusExecuteInvoker extends HPICommonUi {

    private HPIUiConsultInvoker          parent;
        
    HPIUiStatusExecuteInvoker(HPIUiConsultInvoker parent) {
        super(parent.getForm(), new Form("HPI Execution Invoker: " + parent.getInvokerId()), parent.getDisplay(),
                new TemporaryMessage("Requesting Server", "Wait until server process the Request to execute the invoker: " + parent.getInvokerId()));
        this.parent = parent;
    }
    
    public void run() {
        Alert a = null;
        try {
            HPIClientProtocol hpiClientProtocol = this.parent.getParent().getParent().getHPIClientProtocol();
            LoginResponse loginResponse = this.parent.getParent().getParent().getLoginResponse();
            
            ExecuteInvokerResponse executeInvokerResponse = hpiClientProtocol.executeInvoker(loginResponse.getSessionId(), this.parent.getInvokerId());
            if (executeInvokerResponse.getStatus().equals(Response.Status.SUCCESS)) {
                HPIUiAlert.show("Execution Invoker Response", executeInvokerResponse.getMessage(), "Ok", this.parent.getForm());
            } else {
                HPIUiAlert.show("Error on execution Invoker", executeInvokerResponse.getMessage(), "Ok", this.parent.getForm());
            }
        } catch (Exception e) {
            HPIUiAlert.show("Server Message", e.getMessage(), "Done", this.parent.getForm());
        }
    }
    
    public void commandAction(Command c, Displayable d) {
        if (c == Alert.DISMISS_COMMAND) {
            this.getDisplay().setCurrent(this.parent.getForm());
        }
    }
    
}
