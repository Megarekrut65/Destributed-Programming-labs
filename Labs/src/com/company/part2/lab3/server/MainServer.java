package com.company.part2.lab3.server;


public class MainServer {
    public static void main(String[] args) {
        DepartmentSocketServer server = new DepartmentSocketServer(8085);
        server.run();
    }
}
