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
import org.hpi.dialogue.protocol.response.ServerShutdownResponse;
import org.hpi.dialogue.protocol.service.HPIClientProtocol;

/**
 *
 * @author villjea
 */
public class HPIUiShutdownServer extends HPICommonUi {

    private HPIUiDashBoard          parent;
        
    HPIUiShutdownServer(HPIUiDashBoard parent) {
        super(parent.getForm(), new Form("HPI Shutdown Server"), parent.getDisplay(),
                new TemporaryMessage("Requesting Server", "Wait until server process the Shutdown Request."));
        this.parent = parent;
    }
    
    public void run() {
        Alert a = null;
        try {
            ServerShutdownResponse shutdownResponse = this.parent.getHPIClientProtocol().serverShutdown();
            
            if (shutdownResponse.getStatus().equals(Response.Status.SUCCESS)) {
                HPIUiAlert.show("Shutdown Server Message", shutdownResponse.getMessage(), "Ok", this.parent.getForm());
            } else {
                HPIUiAlert.show("Server Message", shutdownResponse.getMessage(), "Ok", this.parent.getForm());
            }
        } catch (Exception e) {
            HPIUiAlert.show("Processing Error Message", e.getMessage(), "Back", this.parent.getForm());
        }
        a.setTimeout(Alert.FOREVER);
        a.setCommandListener(this);
        getDisplay().setCurrent(a);
    }
    
    public void commandAction(Command c, Displayable d) {
    }
    
}
