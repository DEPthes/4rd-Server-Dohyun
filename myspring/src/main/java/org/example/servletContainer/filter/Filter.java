package org.example.servletContainer.filter;

import org.example.servletContainer.HttpRequest;
import org.example.servletContainer.HttpResponse;

import java.io.IOException;

public interface Filter {
    void init();
    void doFilter(HttpRequest request, HttpResponse response, FilterChain chain) throws IOException;
    void destroy();
}
