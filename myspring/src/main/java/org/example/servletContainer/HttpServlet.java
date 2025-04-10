package org.example.servletContainer;

import java.io.IOException;

abstract class HttpServlet implements Servlet{
    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException {
        String method = request.getMethod();

        if ("GET".equalsIgnoreCase(method)){
            doGet(request, response);
        } else if ("POST".equalsIgnoreCase(method)) {
            doPost(request, response);
        } else if ("DELETE".equalsIgnoreCase(method)) {
            doDelete(request, response);
        } else {
            response.write("405 Method Not Allowed");
        }
    }

    protected void doGet(HttpRequest request, HttpResponse response) throws IOException {
        response.write("GET method is not supported.");
    }

    protected void doPost(HttpRequest request, HttpResponse response) throws IOException {
        response.write("POST method is not supported.");
    }

    protected void doDelete(HttpRequest request, HttpResponse response) throws IOException {
        response.write("DELETE method is not supported.");
    }
}
