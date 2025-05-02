package org.example.servletContainer.filter;

import org.example.servletContainer.HttpRequest;
import org.example.servletContainer.HttpResponse;

import java.io.IOException;

public class LoggingFitter implements Filter {
    @Override
    public void init() {
        System.out.println("[LoggingFilter] 초기화됨");
    }

    @Override
    public void doFilter(HttpRequest request, HttpResponse response, FilterChain chain) throws IOException {
        System.out.println("[요청 로그] " + request.getMethod() + " " + request.getPath());
        chain.doFilter(request, response);
        System.out.println("[응답 로그] 응답 완료");
    }

    @Override
    public void destroy() {
        System.out.println("[LoggingFilter] 종료됨");
    }
}
