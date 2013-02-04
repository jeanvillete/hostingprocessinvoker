package org.hpi.entities;

import java.util.ArrayList;
import java.util.List;

import org.com.tatu.helper.parameter.Parameter;

public class Invoker {

	private String					id;
	private String					description;
	private List<Executable>		executables;
	
	public Invoker() {
	}
	
	public Invoker(String id) {
		Parameter.check(id).notNull().notEmpty();
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
