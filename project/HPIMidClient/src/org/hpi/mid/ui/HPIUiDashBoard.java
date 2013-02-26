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
import org.hpi.dialogue.protocol.response.LoginResponse;
import org.hpi.dialogue.protocol.response.Response;
import org.hpi.dialogue.protocol.service.HPIClientProtocol;

/**
 *
 * @author villjea
 */
public class HPIUiDashBoard extends HPICommonUi {

    private StringItem      sessionStatus, sessionId, userLogged;
    private Command         cmdBack, cmdListInvokers, cmdShutdownServer;
    private LoginResponse   loginResponse;
    private User            userLoggedU;
    private HPIMidClient    parent;
    
    HPIUiDashBoard(HPIMidClient parent, Form parentForm) {
        super(parentForm, new Form("HPI Client"), Display.getDisplay(parent),
                new TemporaryMessage("Requestiing Server", "Wait until server checks your credentials."));
        
        this.parent = parent;
        this.notify();
    }
    
    public void commandAction(Command c, Displayable d) {
        if (c == this.cmdBack) {
            this.parent.switchDisplayable();
        } else if (c == this.cmdListInvokers) {
            
        } else if (c == this.cmdShutdownServer) {
            // new HPIUiShutdownServer(this.getForm());
        }
    }
    
    void validate() {
        if (!HPIUtil.isStringOk(this.parent.getServerAddress().getString()) ||
                !HPIUtil.isStringOk(this.parent.getPortNumber().getString()) ||
                !HPIUtil.isStringOk(this.parent.getUser().getString()) ||
                !HPIUtil.isStringOk(this.parent.getPassword().getString())) {
            throw new RuntimeException("All fiedls are required!");
        }
    }
    
    public void run() {
        try {
            this.wait();
            
            this.validate();

            String serverAddress = this.parent.getServerAddress().getString();
            int portNumber = Integer.parseInt(this.parent.getPortNumber().getString());
            HPIClientProtocol clientProtocol = new HPIClientProtocol(serverAddress, portNumber);

            String nickname = this.parent.getUser().getString();
            String password = this.parent.getPassword().getString();
            this.userLoggedU = new User(nickname, password);

            this.loginResponse = clientProtocol.doLogin(this.userLoggedU);

            if (loginResponse.getStatus().equals(Response.Status.SUCCESS)) {
                this.sessionStatus = new StringItem("Session started at: ",  new Date().toString());
                this.sessionId = new StringItem("Session Id: ",  this.loginResponse.getSessionId());
                this.userLogged = new StringItem("User logged: ", this.userLoggedU.getNickname());

                this.cmdBack = new Command("Close Session", Command.EXIT, 2);
                this.cmdListInvokers = new Command("List Invokers", Command.ITEM, 1);
                this.cmdShutdownServer = new Command("Shutdown Server", Command.ITEM, 2);

                this.getForm().append(this.sessionStatus);
                this.getForm().append(this.sessionId);
                this.getForm().append(this.userLogged);

                this.getForm().addCommand(this.cmdBack);
                this.getForm().addCommand(this.cmdListInvokers);
                this.getForm().addCommand(this.cmdShutdownServer);

                this.getForm().setCommandListener(this);

                this.getDisplay().setCurrent(this.getForm());
            } else {
                throw new RuntimeException(loginResponse.getMessage());
            }
        } catch (Exception e) {
            Alert a = new Alert("Server Message", e.getMessage(), null, AlertType.ERROR);
            a.setTimeout(Alert.FOREVER);
            a.setCommandListener(this.parent);
            getDisplay().setCurrent(a);
        }
    }
}