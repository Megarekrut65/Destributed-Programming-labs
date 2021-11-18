package com.company.part2.lab3;

import com.company.part2.lab3.server.DepartmentServer;

public class Main {
    public static void main(String[] args) {
        DepartmentServer server = new DepartmentServer(8085);
        server.run();
    }
}
