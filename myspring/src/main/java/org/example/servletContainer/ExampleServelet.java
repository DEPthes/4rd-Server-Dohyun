package org.example.servletContainer;

import java.io.IOException;

public class ExampleServelet extends HttpServlet {
    @Override
    protected void doGet(HttpRequest request, HttpResponse response) throws IOException {
        response.write("Hello from GET!");
    }

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) throws IOException {
        String body = request.getBody();
        response.write("Received POST with body: " + body);
    }

    @Override
    protected void doDelete(HttpRequest request, HttpResponse response) throws IOException {
        response.write("Resource DELETE!");
    }
}
