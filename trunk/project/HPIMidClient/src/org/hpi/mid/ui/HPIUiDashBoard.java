/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpi.mid.ui;

import java.util.Date;
import javax.microedition.lcdui.*;
import org.hpi.dialogue.protocol.entities.User;
import org.hpi.dialogue.protocol.response.LoginResponse;

/**
 *
 * @author villjea
 */
public class HPIUiDashBoard implements CommandListener {

    private User            user;
    private StringItem      sessionStatus, sessionId, userLogged;
    private Display         display;
    private Form            form;
    private HPIMidClient    parent;
    private Command         commandBack;
    
    HPIUiDashBoard(HPIMidClient parent, LoginResponse loginResponse) {
        this.parent = parent;
        this.display = Display.getDisplay(parent);
        
        this.sessionStatus = new StringItem("Session started at: ",  new Date().toString());
        this.sessionId = new StringItem("Session Id: ",  loginResponse.getSessionId());
        this.userLogged = new StringItem("User logged: ", parent.getUser().getNickname());
        this.commandBack = new Command("Close Session", Command.BACK, 0);
        
        this.form = new Form("HPI Client");
        this.form.append(this.sessionStatus);
        this.form.append(this.sessionId);
        this.form.append(this.userLogged);
        this.form.addCommand(this.commandBack);
        this.form.setCommandListener(this);
        
        this.display.setCurrent(this.form);
    }
    
    public void commandAction(Command c, Displayable d) {
    }
    
}
