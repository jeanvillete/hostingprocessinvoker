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
import org.hpi.dialogue.protocol.response.ListInvokersResponse;
import org.hpi.dialogue.protocol.response.LoginResponse;
import org.hpi.dialogue.protocol.response.Response;
import org.hpi.dialogue.protocol.response.ServerShutdownResponse;
import org.hpi.dialogue.protocol.service.HPIClientProtocol;

/**
 *
 * @author villjea
 */
public class HPIUiListInvokers extends HPICommonUi {
    
    private HPIUiDashBoard          parent;
    private List                    list;
    private Command                 cmdBack, cmdConsultInvoker;
    
    HPIUiListInvokers(HPIUiDashBoard parent) {
        super(parent.getForm(), new Form("HPI List Invokers"), parent.getDisplay(),
                new TemporaryMessage("Requesting Server", "Wait until server process the request and answer the List Invokers."));
        this.parent = parent;
    }
    
    public void run() {
        try {
            ListInvokersResponse listInvokersResponse = this.parent.getHPIClientProtocol().listInvokers(this.parent.getLoginResponse().getSessionId());
            if (listInvokersResponse.getStatus().equals(Response.Status.SUCCESS)) {
                int listSize = listInvokersResponse.getListInvokers().size();
                
                String[] idsInvokers = null;
                if (listSize == 0) {
                    idsInvokers = new String[]{"The server has not found no invoker."};
                } else {
                    idsInvokers = new String[listSize];
                    for (int i = 0; i < listSize; i++) {
                        Invoker invoker = (Invoker) listInvokersResponse.getListInvokers().elementAt(i);
                        idsInvokers[i] = invoker.getId();
                    }
                }
                
                Image[] imageArray = null;
                
                this.cmdBack = new Command("Back", Command.EXIT, 2);
                this.cmdConsultInvoker = new Command("Consult Invoker", Command.ITEM, 1);
                
                this.list = new List("List Invokers", Choice.IMPLICIT, idsInvokers, imageArray);
                this.list.setCommandListener(this);
                this.list.addCommand(this.cmdBack);
                this.list.addCommand(this.cmdConsultInvoker);
                
                this.getDisplay().setCurrent(this.list);
            } else {
                throw new RuntimeException(listInvokersResponse.getMessage());
            }
        } catch (Exception e) {
            Alert a = new Alert("Processing Error Message", e.getMessage(), null, AlertType.ERROR);
            a.setTimeout(Alert.FOREVER);
            a.setCommandListener(this);
            this.getDisplay().setCurrent(a);
        }
    }
    
    public void commandAction(Command c, Displayable d) {
        if (c == this.cmdBack || c == Alert.DISMISS_COMMAND) {
            this.getDisplay().setCurrent(this.parent.getForm());
        } else if (c == this.cmdConsultInvoker) {
            new HPIUiConsultInvoker(this);
        }
    }
    
    public HPIUiDashBoard getParent() {
        return this.parent;
    }
    
    public List getFormList() {
        return this.list;
    }
            
}
