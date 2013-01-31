package org.hostingprocessinvoker.entities;

import java.util.ArrayList;
import java.util.List;

public class Executable {

	private String					canonicalPath;
	private List<Parameter>			parameters;
	
	// GETTERS AND SETTERS //
	public String getCanonicalPath() {
		return canonicalPath;
	}
	public void setCanonicalPath(String canonicalPath) {
		this.canonicalPath = canonicalPath;
	}
	public List<Parameter> getParameters() {
		if (parameters == null) {
			parameters = new ArrayList<Parameter>();
		}
		return parameters;
	}
	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}
	
}
