package org.hpi.dialogue.protocol.entities;

import java.util.Vector;

public class Executable {

    private static final long serialVersionUID = -7347067363383932224L;

    private String                      canonicalPath;
    private Vector			parameters;

    // GETTERS AND SETTERS //
    public String getCanonicalPath() {
        return canonicalPath;
    }
    public void setCanonicalPath(String canonicalPath) {
        this.canonicalPath = canonicalPath;
    }
    public Vector getParameters() {
        if (parameters == null) {
            parameters = new Vector();
        }
        return parameters;
    }
    public void setParameters(Vector parameters) {
        this.parameters = parameters;
    }
	
}
