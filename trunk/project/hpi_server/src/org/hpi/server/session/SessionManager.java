/**
 * 
 */
package org.hpi.server.session;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.com.tatu.helper.parameter.Parameter;
import org.hpi.entities.Session;

/**
 * @author Jean Villete
 *
 */
public class SessionManager extends Thread {

	private long										keepSessionAlive; // miliseconds value
	private List<SessionManager.AliveSession>			listAliveSessions = new ArrayList<SessionManager.AliveSession>();
	private static SessionManager						INSTANCE;
	
	private SessionManager(int keepSessionAlive) {
		if (keepSessionAlive < 1) {
			throw new IllegalArgumentException("The argument keepSessionAlive must be greater than 0.");
		}
		this.keepSessionAlive = keepSessionAlive;
		this.start();
	}
	
	public static void startup(int keepSessionAlive) {
		if (INSTANCE == null) {
			INSTANCE = new SessionManager(keepSessionAlive);
		}
	}
	
	public static SessionManager getInstance() {
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
			SessionManager.AliveSession aliveSession = this.listAliveSessions.get(i);
			if ((aliveSession.getDate().getTime() + this.keepSessionAlive) < now.getTime()) {
				this.listAliveSessions.remove(i);
			}
		}
	}
	
	public void newSession(Session session) {
		Parameter.check(session).notNull();
		this.listAliveSessions.add(new AliveSession(session));
	}
	
	public Session getSession(String session_id) {
		for (int i = 0; i < this.listAliveSessions.size(); i++) {
			SessionManager.AliveSession aliveSession = this.listAliveSessions.get(i);
			if (aliveSession.getSession().getSession_id().equals(session_id)) {
				return aliveSession.getSession();
			}
		}
		return null;
	}
	
	public void updateSession(String session_id) {
		Parameter.check(session_id).notNull().notEmpty();
		for (SessionManager.AliveSession aliveSession : this.listAliveSessions) {
			if (aliveSession.getSession().getSession_id().equals(session_id)) {
				aliveSession.updateSessionDate();
				return;
			}
		}
		throw new IllegalArgumentException("Session has not been updated. There's no alive session to this session_id: " + session_id);
	}
	
	public void deleteSession(String session_id) {
		Parameter.check(session_id).notNull().notEmpty();
		for (int i = 0; i < this.listAliveSessions.size(); i++) {
			SessionManager.AliveSession aliveSession = this.listAliveSessions.get(i);
			if (aliveSession.getSession().getSession_id().equals(session_id)) {
				this.listAliveSessions.remove(i);
				return;
			}
		}
		throw new IllegalArgumentException("Session has not been deleted. There's no alive session to this session_id: " + session_id);
	}
	
	private class AliveSession {
		
		private Date			date;
		private Session			session;
		
		AliveSession(Session session) {
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

		Session getSession() {
			return session;
		}
	}
}
