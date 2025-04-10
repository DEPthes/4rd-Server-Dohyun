package org.example;

import org.example.servletContainer.ExampleServelet;
import org.example.servletContainer.ServletContainer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ServletContainer container = new ServletContainer(8080);
        container.addServlet("/example", new ExampleServelet());
        container.start();
    }
}