package org.hpi.dialogue.protocol.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Invoker implements Serializable {

	private static final long serialVersionUID = -2498044589110396765L;
	
	private String					id;
	private String					description;
	private List<Executable>		executables;
	
	public Invoker() {
	}
	
	public Invoker(String id) {
		if (id == null || id.isEmpty()) {
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

	public List<Executable> getExecutables() {
		if (executables == null) {
			executables = new ArrayList<Executable>();
		}
		return executables;
	}

	public void setExecutables(List<Executable> executables) {
		this.executables = executables;
	}
}
