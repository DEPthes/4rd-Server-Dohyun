package org.example.servletContainer.Session;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {
    private final Map<String, HttpSessionImpl> sessions = new ConcurrentHashMap<>();
    private final long timeout = 30 * 60 * 1000L; // 30분

    public HttpSession getSession(String id) {
        if (id != null && sessions.containsKey(id)) {
            HttpSessionImpl session = sessions.get(id);
            if (session.isValid() && System.currentTimeMillis() - session.getLastAccessedTime() < timeout) {
                session.touch();
                return session;
            }
        }

        // 새 세션 생성
        String newId = UUID.randomUUID().toString();
        HttpSessionImpl newSession = new HttpSessionImpl(newId);
        sessions.put(newId, newSession);
        return newSession;
    }
}
