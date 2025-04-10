package org.example.servletContainer;

import java.io.IOException;

public interface Servlet {
    public void service(HttpRequest request, HttpResponse response) throws IOException;
}
