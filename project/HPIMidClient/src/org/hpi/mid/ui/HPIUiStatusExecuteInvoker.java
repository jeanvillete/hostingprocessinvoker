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
                a = new Alert("Execution Invoker Response", executeInvokerResponse.getMessage(), null, AlertType.INFO);
            } else {
                a = new Alert("Error on execution Invoker", executeInvokerResponse.getMessage(), null, AlertType.ERROR);
            }
        } catch (Exception e) {
            a = new Alert("Processing Error Message", e.getMessage(), null, AlertType.ERROR);
        }
        a.setTimeout(Alert.FOREVER);
        a.setCommandListener(this);
        getDisplay().setCurrent(a);
    }
    
    public void commandAction(Command c, Displayable d) {
        if (c == Alert.DISMISS_COMMAND) {
            this.getDisplay().setCurrent(this.parent.getForm());
        }
    }
    
}
