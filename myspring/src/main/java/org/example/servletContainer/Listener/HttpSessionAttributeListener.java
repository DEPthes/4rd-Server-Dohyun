package org.example.servletContainer.Listener;

import org.example.servletContainer.Session.HttpSession;

public interface HttpSessionAttributeListener {
    void attributeAdded(HttpSession session, String name, Object value);
    void attributeRemoved(HttpSession session, String name);
    void attributeReplaced(HttpSession session, String name, Object oldValue);
}
