package org.example.servletContainer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public class HttpResponse {
    private OutputStream outputStream;
    private ByteArrayOutputStream buffer = new ByteArrayOutputStream();

    public HttpResponse(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void write(String data) throws IOException {
        buffer.write(data.getBytes());
    }

    public void send() throws IOException {
        PrintWriter writer = new PrintWriter(outputStream);
        writer.print("HTTP/1.1 200 OK\r\n");
        writer.print("Content-Length: " + buffer.size() + "\r\n");
        writer.print("Content-Type: text/plain; charset=UTF-8\r\n");
        writer.print("\r\n");
        writer.flush();
        outputStream.write(buffer.toByteArray());
        outputStream.flush();
        buffer.reset();
    }
}
