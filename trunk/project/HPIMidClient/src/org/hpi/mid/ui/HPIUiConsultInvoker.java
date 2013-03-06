/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpi.mid.ui;

import java.util.Date;
import javax.microedition.lcdui.*;
import javax.microedition.midlet.MIDlet;
import org.hpi.dialogue.protocol.common.HPIUtil;
import org.hpi.dialogue.protocol.entities.Invoker;
import org.hpi.dialogue.protocol.entities.User;
import org.hpi.dialogue.protocol.response.*;
import org.hpi.dialogue.protocol.service.HPIClientProtocol;

/**
 *
 * @author villjea
 */
public class HPIUiConsultInvoker extends HPICommonUi {
    
    private HPIUiListInvokers           parent;
    private Command                     cmdBack, cmdExecuteInvoker;
    private StringItem                  lblInvokerId, lblInvokerDescription;
    private String                      invokerId;
    
    HPIUiConsultInvoker(HPIUiListInvokers parent) {
        super(parent.getForm(), new Form("HPI Consult Invoker"), parent.getDisplay(),
                new TemporaryMessage("Requesting Server", "Wait until server process the request and answer the Consult Invoker."));
        this.parent = parent;
    }
    
    public void run() {
        try {
            LoginResponse loginResponse = this.parent.getParent().getLoginResponse();
            this.invokerId = this.parent.getFormList().getString(this.parent.getFormList().getSelectedIndex());
            HPIClientProtocol clientProtocol = this.parent.getParent().getHPIClientProtocol();
            
            DescribeInvokerResponse describeInvokerResponse = clientProtocol.describeInvoker(loginResponse.getSessionId(), invokerId);
            if (describeInvokerResponse.getStatus().equals(Response.Status.SUCCESS)) {
                Invoker invoker = describeInvokerResponse.getInvoker();
                
                this.lblInvokerId = new StringItem("Invoker Id: ",  invoker.getId());
                this.lblInvokerDescription = new StringItem("Invoker Description: ",  invoker.getDescription());
                
                this.cmdBack = new Command("Back", Command.EXIT, 1);
                this.cmdExecuteInvoker = new Command("Execute Invoker", Command.ITEM, 1);
                
                this.getForm().append(this.lblInvokerId);
                this.getForm().append(this.lblInvokerDescription);
                this.getForm().addCommand(this.cmdBack);
                this.getForm().addCommand(this.cmdExecuteInvoker);
                
                this.getForm().setCommandListener(this);
                this.getDisplay().setCurrent(this.getForm());
            } else {
                throw new RuntimeException(describeInvokerResponse.getMessage());
            }
        } catch (Exception e) {
            HPIUiAlert.show("Server Message", e.getMessage(), "Done", this.parent.getForm());
        }
    }
    
    public void commandAction(Command c, Displayable d) {
        if (c == this.cmdBack) {
            this.getDisplay().setCurrent(this.parent.getFormList());
        } else if (c == this.cmdExecuteInvoker) {
            new HPIUiStatusExecuteInvoker(this);
        }
    }
    
    public HPIUiListInvokers getParent() {
        return this.parent;
    }
    
    public String getInvokerId() {
        return this.invokerId;
    }
    
}
