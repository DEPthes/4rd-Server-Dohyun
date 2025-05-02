package org.example.servletContainer.Listener;

import org.example.servletContainer.Session.HttpSession;

public interface HttpSessionListener {
    void sessionCreated(HttpSession session);
    void sessionDestroyed(HttpSession session);
}
