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
    
    private Form            form, parentForm;
    private Display         display;
    
    
    public HPICommonUi(Form parentForm, Form form, Display display) {
        this(parentForm, form, display,  null);
    }
    
    public HPICommonUi(Form parentForm, Form form, Display display, TemporaryMessage tempMessage) {
        this.parentForm = parentForm;
        this.form = form;
        this.display = display;
        
        if (tempMessage != null) {
            HPIUiAlert.show(tempMessage.getTitle(), tempMessage.getText());
        }
        
        this.start();
    }
    
    public Form getForm() {
        return this.form;
    }
    public Form getParentForm() {
        return this.parentForm;
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