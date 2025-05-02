package org.example;

import org.example.servletContainer.ExampleServelet;
import org.example.servletContainer.Listener.HttpSessionAttributeListener;
import org.example.servletContainer.Listener.HttpSessionListener;
import org.example.servletContainer.Listener.ListenerRegistry;
import org.example.servletContainer.ServletContainer;
import org.example.servletContainer.Session.HttpSession;
import org.example.servletContainer.filter.LoggingFitter;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        try {
            ServletContainer container = new ServletContainer(8080);
            container.addServlet("/example", new ExampleServelet());
            container.addFilter(new LoggingFitter());

            // 리스너 등록
            ListenerRegistry.addSessionListener(new HttpSessionListener() {
                @Override
                public void sessionCreated(HttpSession session) {
                    System.out.println("[세션 생성] ID: " + session.getId());
                }

                @Override
                public void sessionDestroyed(HttpSession session) {
                    System.out.println("[세션 종료] ID: " + session.getId());
                }
            });

            ListenerRegistry.addAttributeListener(new HttpSessionAttributeListener() {
                @Override
                public void attributeAdded(HttpSession session, String name, Object value) {
                    System.out.println("[속성 추가] " + name + " = " + value);
                }

                @Override
                public void attributeRemoved(HttpSession session, String name) {
                    System.out.println("[속성 제거] " + name);
                }

                @Override
                public void attributeReplaced(HttpSession session, String name, Object oldValue) {
                    System.out.println("[속성 변경] " + name + " (기존 값: " + oldValue + ")");
                }
            });

            container.start();
        } catch (Exception e) {
            e.printStackTrace(); // <- 예외 출력
        }
    }
}