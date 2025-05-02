package org.example.servletContainer;

import org.example.servletContainer.Session.HttpSession;
import org.example.servletContainer.Session.SessionManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private String method;
    private String path;
    private String body;
    private final Map<String, String> headers = new HashMap<>();
    private HttpSession session;

    public HttpRequest(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line = reader.readLine();

        if (line != null) {
            String[] parts = line.split(" ");

            if (parts.length >= 2) {
                method = parts[0];
                path = parts[1];
            }
        }

        int contentLength = 0;

        // 헤더 읽기
        while ((line = reader.readLine()) != null && !line.isEmpty()) {
            int colonIndex = line.indexOf(":");
            if (colonIndex != -1) {
                String name = line.substring(0, colonIndex).trim();
                String value = line.substring(colonIndex + 1).trim();
                headers.put(name, value);

                if (name.equalsIgnoreCase("Content-Length")) {
                    contentLength = Integer.parseInt(value);
                }
            }
        }

        // 본문 읽기
        if (contentLength > 0) {
            char[] bodyChars = new char[contentLength];
            reader.read(bodyChars, 0, contentLength);
            body = new String(bodyChars);
        } else {
            body = "";
        }
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getBody() {
        return body;
    }

    public String getHeader(String name) {
        return headers.get(name);
    }

    public HttpSession getSession(SessionManager sessionManager) {
        if (session == null) {
            String cookieHeader = getHeader("Cookie");
            String sessionId = null;

            if (cookieHeader != null) {
                for (String cookie : cookieHeader.split(";")) {
                    String[] parts = cookie.trim().split("=");
                    if (parts.length == 2 && parts[0].equals("JSESSIONID")) {
                        sessionId = parts[1];
                        break;
                    }
                }
            }

            session = sessionManager.getSession(sessionId);
        }

        return session;
    }
}
