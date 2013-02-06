package org.hpi.data.factoy;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.hpi.dialogue.protocol.entities.Invoker;
import org.hpi.dialogue.protocol.entities.User;

public class HPIDataFactory {

	private static HPIDataFactory					INSTANCE;
	
	/**
	 * String: invoker.id
	 * Invoker: invoker
	 */
	private Map<String, Invoker>					invokers = new HashMap<String, Invoker>();
	
	/**
	 * String: user.nickname
	 * User: user
	 */
	private Map<String, User>						users = new HashMap<String, User>();
	
	// singleton pattern
	private HPIDataFactory() {
	}
	
	public static HPIDataFactory getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new HPIDataFactory();
		}
		return INSTANCE;
	}
	
	void addInvoker(Invoker invoker) {
		this.invokers.put(invoker.getId(), invoker);
	}
	
	void removeInvoker(String invokerId) {
		this.invokers.remove(invokerId);
	}
	
	void addUser(User user) {
		this.users.put(user.getNickname(), user);
	}
	
	// GETTERS AND SETTERS //
	public Invoker getInvoker(String invokerId) {
		return this.invokers.get(invokerId);
	}
	
	public Collection<Invoker> getInvokers() {
		return this.invokers.values();
	}
	
	public User getUser(String nickname) {
		return this.users.get(nickname);
	}
	
	public Collection<User> getUsers() {
		return this.users.values();
	}
}
