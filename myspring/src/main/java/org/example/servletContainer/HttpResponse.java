package org.example.servletContainer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

public class HttpResponse {
    private OutputStream outputStream;
    private ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    private final Map<String, String> headers = new LinkedHashMap<>();

    public HttpResponse(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void write(String data) throws IOException {
        buffer.write(data.getBytes());
    }

    // 새로운 메서드: 헤더 추가
    public void setHeader(String name, String value) {
        headers.put(name, value);
    }

    public void send() throws IOException {
        PrintWriter writer = new PrintWriter(outputStream);
        writer.print("HTTP/1.1 200 OK\r\n");

        // 기본 헤더
        writer.print("Content-Length: " + buffer.size() + "\r\n");
        writer.print("Content-Type: text/plain; charset=UTF-8\r\n");

        // 추가 헤더 출력
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            writer.print(entry.getKey() + ": " + entry.getValue() + "\r\n");
        }

        writer.print("\r\n");
        writer.flush();

        outputStream.write(buffer.toByteArray());
        outputStream.flush();
        buffer.reset();
    }
}
