package org.hpi.mid.ui;


import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import org.hpi.dialogue.protocol.common.HPIUtil;
import org.hpi.dialogue.protocol.entities.User;
import org.hpi.dialogue.protocol.response.LoginResponse;
import org.hpi.dialogue.protocol.response.Response;
import org.hpi.dialogue.protocol.service.HPIClientProtocol;

/**
 * @author carrefour
 */
public class HPIMidClient extends MIDlet implements CommandListener {
    
    private boolean midletPaused = false;
    private Command exitCommand;
    private Command itemCommand;
    private Form form;
    

    private TextField serverAddress, portNumber, user, password;

    public TextField getPassword() {
        return password;
    }

    public void setPassword(TextField password) {
        this.password = password;
    }

    public TextField getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(TextField portNumber) {
        this.portNumber = portNumber;
    }

    public TextField getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(TextField serverAddress) {
        this.serverAddress = serverAddress;
    }

    public TextField getUser() {
        return user;
    }

    public void setUser(TextField user) {
        this.user = user;
    }
    
    /**
     * The HelloMIDlet constructor.
     */
    public HPIMidClient() {
    }

    /**
     * Initializes the application. It is called only once when the MIDlet is
     * started. The method is called before the
     * <code>startMIDlet</code> method.
     */
    private void initialize() {                                         
                                           
    }                            

    /**
     * Performs an action assigned to the Mobile Device - MIDlet Started point.
     */
    public void startMIDlet() {                                      
        switchDisplayable(null, getForm());                                        
    }                             
    
    /**
     * Performs an action assigned to the Mobile Device - MIDlet Resumed point.
     */
    public void resumeMIDlet() {                                       
    }                              

    /**
     * Switches a current displayable in a display. The
     * <code>display</code> instance is taken from
     * <code>getDisplay</code> method. This method is used by all actions in the
     * design for switching displayable.
     *
     * @param alert the Alert which is temporarily set to the display; if
     * <code>null</code>, then
     * <code>nextDisplayable</code> is set immediately
     * @param nextDisplayable the Displayable to be set
     */
    public void switchDisplayable(Alert alert, Displayable nextDisplayable) {                                            
        Display display = getDisplay();                                               
        if (alert == null) {
            display.setCurrent(nextDisplayable);
        } else {
            display.setCurrent(alert, nextDisplayable);
        }                                             
    }                                   
    
    public void switchDisplayable() {
        switchDisplayable(null, getForm());
    }
    
    /**
     * Called by a system to indicated that a command has been invoked on a
     * particular displayable.
     *
     * @param command the Command that was invoked
     * @param displayable the Displayable where the command was invoked
     */
    public void commandAction(Command command, Displayable displayable) {                                               
        if (displayable == form) {                                           
            if (command == exitCommand) {                                         
                exitMIDlet();                                           
            } else if (command == itemCommand) {
                new HPIUiDashBoard(this);
            }
        }
        if (command == Alert.DISMISS_COMMAND) {
            switchDisplayable(null, getForm());
        }
    }                               
    
    /**
     * Returns an initialized instance of exitCommand component.
     *
     * @return the initialized component instance
     */
    public Command getExitCommand() {
        if (exitCommand == null) {                                 
            // write pre-init user code here
            exitCommand = new Command("Exit", Command.EXIT, 0);                                   
            // write post-init user code here
        }                         
        return exitCommand;
    }
    
    /**
     * Returns an initialized instance of form component.
     *
     * @return the initialized component instance
     */
    public Form getForm() {
        if (form == null) {
            this.serverAddress = new TextField("Server Address", "", 32, TextField.ANY);
            this.portNumber = new TextField("Port Number", "4444", 32, TextField.NUMERIC);
            this.user = new TextField("User", "", 32, TextField.ANY);
            this.password = new TextField("Password", "", 32, TextField.PASSWORD);
            
            form = new Form("HPI Client", 
                new Item[]{
                    this.serverAddress,
                    this.portNumber, 
                    this.user,
                    this.password
                }
            );
            form.addCommand(getExitCommand());
            form.addCommand(getItemCommand());
            form.setCommandListener(this);                                  
        }                         
        return form;
    }
    
    /**
     * Returns an initialized instance of itemCommand component.
     *
     * @return the initialized component instance
     */
    public Command getItemCommand() {
        if (itemCommand == null) {                                 
            // write pre-init user code here
            itemCommand = new Command("Connect", "Connect", Command.ITEM, 0);                                   
            // write post-init user code here
        }                         
        return itemCommand;
    }

    /**
     * Returns a display instance.
     *
     * @return the display instance.
     */
    public Display getDisplay() {
        return Display.getDisplay(this);
    }

    /**
     * Exits MIDlet.
     */
    public void exitMIDlet() {
        switchDisplayable(null, null);
        destroyApp(true);
        notifyDestroyed();
    }

    /**
     * Called when MIDlet is started. Checks whether the MIDlet have been
     * already started and initialize/starts or resumes the MIDlet.
     */
    public void startApp() {
        if (midletPaused) {
            resumeMIDlet();
        } else {
            initialize();
            startMIDlet();
        }
        midletPaused = false;
    }

    /**
     * Called when MIDlet is paused.
     */
    public void pauseApp() {
        midletPaused = true;
    }

    /**
     * Called to signal the MIDlet to terminate.
     *
     * @param unconditional if true, then the MIDlet has to be unconditionally
     * terminated and all resources has to be released.
     */
    public void destroyApp(boolean unconditional) {
    }
}
