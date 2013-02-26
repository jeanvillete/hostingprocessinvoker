/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpi.mid.ui;

import javax.microedition.lcdui.*;
import javax.microedition.midlet.MIDlet;

/**
 *
 * @author villjea
 */
public abstract class HPICommonUi extends Thread implements CommandListener {
    
    private Form            form;
    private MIDlet          parent;
    private Display         display;
    
    
    public HPICommonUi(MIDlet parent, Form form) {
        this(parent, form, null);
    }
    
    public HPICommonUi(MIDlet parent, Form form, TemporaryMessage tempMessage) {
        this.parent = parent;
        this.form = form;
        this.display = Display.getDisplay(parent);
        
        if (tempMessage != null) {
            Alert a = new Alert(tempMessage.getTitle(), tempMessage.getText(), null, AlertType.INFO);
            a.setTimeout(Alert.FOREVER);
            getDisplay().setCurrent(a);
        }
        
        this.start();
    }
    
    public Form getForm() {
        return this.form;
    }
    public MIDlet getParent() {
        return this.parent;
    }
    public Display getDisplay() {
        return this.display;
    }
}

class TemporaryMessage {
    private String title;
    private String text;
    public TemporaryMessage(String title, String text) {
        this.title = title;
        this.text = text;
    }
    public String getTitle() {
        return this.title;
    }
    public String getText() {
        return this.text;
    }
}