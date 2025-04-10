package org.example.servletContainer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpRequest {
    private String method;
    private String path;
    private String body;

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

        // 헤더 읽기
        int contentLength = 0;
        while ((line = reader.readLine()) != null && !line.isEmpty()) {
            String[] headerParts = line.split(": ");
            if (headerParts.length == 2 && headerParts[0].equalsIgnoreCase("Content-Length")) {
                contentLength = Integer.parseInt(headerParts[1]);
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
}
