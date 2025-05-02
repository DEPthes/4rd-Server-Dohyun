package org.example.servletContainer.Listener;

import org.example.servletContainer.Session.HttpSession;

import java.util.ArrayList;
import java.util.List;

public class ListenerRegistry {
    private static final List<HttpSessionListener> sessionListeners = new ArrayList<>();
    private static final List<HttpSessionAttributeListener> attributeListeners = new ArrayList<>();

    public static void addSessionListener(HttpSessionListener listener) {
        sessionListeners.add(listener);
    }

    public static void addAttributeListener(HttpSessionAttributeListener listener) {
        attributeListeners.add(listener);
    }

    public static void notifySessionCreated(HttpSession session) {
        for (HttpSessionListener listener : sessionListeners) {
            listener.sessionCreated(session);
        }
    }

    public static void notifySessionDestroyed(HttpSession session) {
        for (HttpSessionListener listener : sessionListeners) {
            listener.sessionDestroyed(session);
        }
    }

    public static void notifyAttributeAdded(HttpSession session, String name, Object value) {
        for (HttpSessionAttributeListener listener : attributeListeners) {
            listener.attributeAdded(session, name, value);
        }
    }

    public static void notifyAttributeRemoved(HttpSession session, String name) {
        for (HttpSessionAttributeListener listener : attributeListeners) {
            listener.attributeRemoved(session, name);
        }
    }

    public static void notifyAttributeReplaced(HttpSession session, String name, Object oldValue) {
        for (HttpSessionAttributeListener listener : attributeListeners) {
            listener.attributeReplaced(session, name, oldValue);
        }
    }
}

