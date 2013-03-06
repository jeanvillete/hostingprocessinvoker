/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpi.mid.ui;

import java.util.Date;
import javax.microedition.lcdui.*;

/**
 *
 * @author carrefour
 */
public class HPIUiAlert {
    
    private Display         display;
    private Form            form;
    
    private static HPIUiAlert INSTANCE = null;
    
    private HPIUiAlert(Display display){
        this.display = display;
    }
    
    public static void start(Display display) {
        if (INSTANCE == null) {
            INSTANCE = new HPIUiAlert(display);
        }
    }
    
    public static void show(String title, String message) {
        show(title, message, null, null);
    }
    
    public static void show(String title, String message, String buttonMessage, final Form nextDisplayable) {
        INSTANCE.form = new Form(title);
        INSTANCE.form.append(new StringItem("Message: ",  message));
        if (buttonMessage != null) {
            INSTANCE.form.addCommand(new Command(buttonMessage, Command.ITEM, 1));
        }
        if (nextDisplayable != null) {

            INSTANCE.form.setCommandListener(
               new CommandListener() {
                public void commandAction(Command c, Displayable d) {
                    INSTANCE.display.setCurrent(nextDisplayable);
                }
            });
        }
        INSTANCE.display.setCurrent(INSTANCE.form);
    }
}
