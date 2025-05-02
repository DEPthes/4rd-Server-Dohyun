package org.example.servletContainer.Session;

public interface HttpSession {
    String getId();
    void setAttribute(String name, Object value);
    Object getAttribute(String name);
    void invalidate();
    long getLastAccessedTime();
    void removeAttribute(String name);
}
