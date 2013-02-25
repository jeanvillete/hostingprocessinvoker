package org.hpi.dialogue.protocol.entities;

import java.util.Vector;

public class Invoker {

    private static final long serialVersionUID = -2498044589110396765L;

    private String					id;
    private String					description;
    private Vector                      		executables;

    public Invoker() {
    }

    public Invoker(String id) {
        if (id == null || id.length() < 1) {
            throw new IllegalArgumentException("Parameter cann't be null or empty.");
        }
        this.id = id;
    }

    // GETTERS AND SETTERS //
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Vector getExecutables() {
        if (executables == null) {
            executables = new Vector();
        }
        return executables;
    }

    public void setExecutables(Vector executables) {
        this.executables = executables;
    }
}
