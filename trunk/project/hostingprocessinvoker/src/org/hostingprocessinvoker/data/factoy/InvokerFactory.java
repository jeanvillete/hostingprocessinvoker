package org.hostingprocessinvoker.data.factoy;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.hostingprocessinvoker.entities.Invoker;

public class InvokerFactory {

	private static InvokerFactory					INSTANCE;
	
	/**
	 * String: invoker.id
	 * Invoker: invoker
	 */
	private Map<String, Invoker>					invokers = new HashMap<String, Invoker>();
	
	// singleton pattern
	private InvokerFactory() {
	}
	
	public static InvokerFactory getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new InvokerFactory();
		}
		return INSTANCE;
	}
	
	void addInvoker(Invoker invoker) {
		this.invokers.put(invoker.getId(), invoker);
	}
	
	void removeInvoker(String invokerId) {
		this.invokers.remove(invokerId);
	}
	
	// GETTERS AND SETTERS //
	public Collection<Invoker> getInvokers() {
		return this.invokers.values();
	}
	
}
