package org.example.servletContainer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ServletContainer {
    private Map<String, Servlet> servletMapping = new HashMap<>();
    private int port;

    public ServletContainer(int port) {
        this.port = port;
    }

    public void addServlet(String path, Servlet servlet) {
        servletMapping.put(path, servlet);
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("서블릿 컨테이너가 포트 " + port + "에서 시작되었습니다.");
        while (true) {
            Socket socket = serverSocket.accept();
            new Thread(() -> {
                try {
                    HttpRequest request = new HttpRequest(socket.getInputStream());
                    HttpResponse response = new HttpResponse(socket.getOutputStream());

                    Servlet servlet = servletMapping.get(request.getPath());
                    if (servlet != null) {
                        servlet.service(request, response);
                    } else {
                        response.write("404 Not Found");
                    }
                    response.send();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
