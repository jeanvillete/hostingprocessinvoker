/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpi.mid.ui;

import java.util.Date;
import javax.microedition.lcdui.*;
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
    
    HPIUiDashBoard(HPIMidClient parent) {
        super(parent, new Form("HPI Client"), new TemporaryMessage("Requestiing Server", "Wait until server checks your credentials."));
    }
    
    public void commandAction(Command c, Displayable d) {
        if (c == this.cmdBack) {
            HPIMidClient parent = (HPIMidClient) this.getParent();
            parent.switchDisplayable(null, parent.getForm());
        }
    }
    
    void validate() {
        if (!HPIUtil.isStringOk(this.getConcretParent().getServerAddress().getString()) ||
                !HPIUtil.isStringOk(this.getConcretParent().getPortNumber().getString()) ||
                !HPIUtil.isStringOk(this.getConcretParent().getUser().getString()) ||
                !HPIUtil.isStringOk(this.getConcretParent().getPassword().getString())) {
            throw new RuntimeException("All fiedls are required!");
        }
    }
    
    private HPIMidClient getConcretParent() {
        return (HPIMidClient) this.getParent();
    }
    
    public void run() {
        try {
            this.validate();

            String serverAddress = this.getConcretParent().getServerAddress().getString();
            int portNumber = Integer.parseInt(this.getConcretParent().getPortNumber().getString());
            HPIClientProtocol clientProtocol = new HPIClientProtocol(serverAddress, portNumber);

            String nickname = this.getConcretParent().getUser().getString();
            String password = this.getConcretParent().getPassword().getString();
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
            a.setCommandListener(this.getConcretParent());
            getDisplay().setCurrent(a);
        }
    }
}
