package org.example.servletContainer.filter;

import org.example.servletContainer.HttpRequest;
import org.example.servletContainer.HttpResponse;
import org.example.servletContainer.Servlet;

import java.io.IOException;
import java.util.List;

public class FilterChain {
    private final List<Filter> filters;
    private final Servlet servlet;
    private int index = 0;

    public FilterChain(List<Filter> filters, Servlet servlet) {
        this.filters = filters;
        this.servlet = servlet;
    }

    public void doFilter(HttpRequest request, HttpResponse response) throws IOException {
        if (index < filters.size()) {
            Filter nextFilter = filters.get(index++);
            nextFilter.doFilter(request, response, this);
        } else {
            servlet.service(request, response);
        }
    }
}
