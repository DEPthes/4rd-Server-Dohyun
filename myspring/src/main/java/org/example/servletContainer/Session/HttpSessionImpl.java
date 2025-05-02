package org.example.servletContainer.Session;

import org.example.servletContainer.Listener.ListenerRegistry;

import java.util.HashMap;
import java.util.Map;

public class HttpSessionImpl implements HttpSession{
    private final String id;
    private final Map<String, Object> attributes = new HashMap<>();
    private long lastAccessedTime = System.currentTimeMillis();
    private boolean valid = true;

    public HttpSessionImpl(String id) {
        this.id = id;
        ListenerRegistry.notifySessionCreated(this);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    public boolean isValid() {
        return valid;
    }

    @Override
    public long getLastAccessedTime() {
        return lastAccessedTime;
    }

    @Override
    public void setAttribute(String name, Object value) {
        Object oldValue = attributes.put(name, value);
        if (oldValue == null) {
            ListenerRegistry.notifyAttributeAdded(this, name, value);
        } else {
            ListenerRegistry.notifyAttributeReplaced(this, name, oldValue);
        }
    }

    @Override
    public void removeAttribute(String name) {
        Object oldValue = attributes.remove(name);
        if (oldValue != null) {
            ListenerRegistry.notifyAttributeRemoved(this, name);
        }
    }

    public void invalidate() {
        valid = false;
        ListenerRegistry.notifySessionDestroyed(this);
    }

    public void touch() {
        lastAccessedTime = System.currentTimeMillis();
    }
}
