package org.hpi.dialogue.protocol.entities;

public class Parameter {
	
    private static final long serialVersionUID = 8577776317880486807L;

    private String				key;
    private String				value;

    public Parameter() {
    }

    public Parameter(String key, String value) {
        if (key == null || key.length() < 1 || value == null || value.length() < 1) {
            throw new IllegalArgumentException("Parameter cann't be null or empty.");
        }
        this.key = key;
        this.value = value;
    }

    // GETTERS AND SETTERS //
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
}
