package com.company.part2.lab3.server;

import com.company.part2.lab2.DepartmentSqlManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DepartmentSocketServer {
    private ServerSocket serverSocket;
    private final Executor executor;
    private final DepartmentSqlManager database;
    public DepartmentSocketServer(int port) {
        executor = Executors.newSingleThreadExecutor();
        database = new DepartmentSqlManager("localhost", 3306, "department");
        try {
            serverSocket = new ServerSocket(port, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void run() {
        while (true){
            try {
                var client = serverSocket.accept();
                executor.execute(new ClientRunnable(client, database));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
