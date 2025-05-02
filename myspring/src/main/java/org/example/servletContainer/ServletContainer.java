package org.example.servletContainer;

import org.example.servletContainer.Session.HttpSession;
import org.example.servletContainer.Session.SessionManager;
import org.example.servletContainer.filter.Filter;
import org.example.servletContainer.filter.FilterChain;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServletContainer {
    private Map<String, Servlet> servletMapping = new HashMap<>();
    private int port;
    private final List<Filter> filters = new ArrayList<>();
    private final ExecutorService threadPool = Executors.newFixedThreadPool(10);
    private final SessionManager sessionManager = new SessionManager();

    public ServletContainer(int port) {
        this.port = port;
    }

    public void addServlet(String path, Servlet servlet) {
        servletMapping.put(path, servlet);
    }

    public void addFilter(Filter filter) {
        filters.add(filter);
        filter.init(); // 필터 초기화 호출
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("서블릿 컨테이너가 포트 " + port + "에서 시작되었습니다.");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            for (Filter filter : filters) {
                filter.destroy(); // 종료 시 정리 작업
            }
            threadPool.shutdown();
            System.out.println("서블릿 컨테이너 종료됨");
        }));

        while (true) {
            Socket socket = serverSocket.accept();
            threadPool.submit(() -> {
                try (socket) {
                    HttpRequest request = new HttpRequest(socket.getInputStream());
                    HttpResponse response = new HttpResponse(socket.getOutputStream());

                    HttpSession session = request.getSession(sessionManager);
                    if (session != null && request.getHeader("Cookie") == null) {
                        response.setHeader("Set-Cookie", "JSESSIONID=" + session.getId() + "; Path=/; HttpOnly");
                    }

                    Servlet servlet = servletMapping.get(request.getPath());
                    if (servlet != null) {
                        FilterChain chain = new FilterChain(filters, servlet);
                        chain.doFilter(request, response);
                    } else {
                        response.write("404 Not Found");
                        response.send();
                    }
                    response.send();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
