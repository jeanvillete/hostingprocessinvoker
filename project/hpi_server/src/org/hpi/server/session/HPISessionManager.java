/**
 * 
 */
package org.hpi.server.session;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.com.tatu.helper.parameter.Parameter;
import org.hpi.dialogue.protocol.entities.User;
import org.hpi.exception.HPISessionException;

/**
 * @author Jean Villete
 *
 */
public class HPISessionManager extends Thread {

	private static HPISessionManager						INSTANCE;
	
	private long											keepSessionAlive; // miliseconds value
	private List<HPISessionManager.AliveSession>			listAliveSessions = new ArrayList<HPISessionManager.AliveSession>();
	
	private HPISessionManager(int keepSessionAlive) {
		if (keepSessionAlive < 1) {
			throw new IllegalArgumentException("The argument keepSessionAlive must be greater than 0.");
		}
		this.keepSessionAlive = keepSessionAlive;
		this.start();
	}
	
	public static void startup(int keepSessionAlive) {
		if (INSTANCE == null) {
			INSTANCE = new HPISessionManager(keepSessionAlive);
		}
	}
	
	public static HPISessionManager getInstance() {
		if (INSTANCE == null) {
			throw new IllegalStateException("None instance was created, so do it before.");
		}
		return INSTANCE;
	}
	
	@Override
	public void run() {
		try {
			boolean running = true;
			while (running) {
				this.checkRemoveSession();
				Thread.sleep(this.keepSessionAlive);
			}
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void checkRemoveSession() {
		Date now = new Date();
		for (int i = 0; i < this.listAliveSessions.size(); i++) {
			HPISessionManager.AliveSession aliveSession = this.listAliveSessions.get(i);
			if ((aliveSession.getDate().getTime() + this.keepSessionAlive) < now.getTime()) {
				this.listAliveSessions.remove(i);
			}
		}
	}
	
	public HPISession newSession(User user, String remoteAddress) {
		HPISession session = new HPISession(user, remoteAddress);
		this.listAliveSessions.add(new AliveSession(session));
		return session;
	}
	
	public HPISession updateSession(String session_id) throws HPISessionException {
		Parameter.check(session_id).notNull().notEmpty();
		for (HPISessionManager.AliveSession aliveSession : this.listAliveSessions) {
			if (aliveSession.getSession().getSession_id().equals(session_id)) {
				aliveSession.updateSessionDate();
				return aliveSession.getSession();
			}
		}
		throw new HPISessionException("Session has not been updated. There's no alive session to this session_id: " + session_id);
	}
	
	public boolean deleteSession(String session_id) {
		Parameter.check(session_id).notNull().notEmpty();
		for (int i = 0; i < this.listAliveSessions.size(); i++) {
			HPISessionManager.AliveSession aliveSession = this.listAliveSessions.get(i);
			if (aliveSession.getSession().getSession_id().equals(session_id)) {
				return this.listAliveSessions.remove(i) != null;
			}
		}
		return false;
	}
	
	private class AliveSession {
		
		private Date			date;
		private HPISession			session;
		
		AliveSession(HPISession session) {
			this.date = new Date();
			this.session = session;
		}
		
		void updateSessionDate() {
			this.date = new Date();
		}

		// GETTERS AND SETTERS //
		Date getDate() {
			return date;
		}

		HPISession getSession() {
			return session;
		}
	}
}
