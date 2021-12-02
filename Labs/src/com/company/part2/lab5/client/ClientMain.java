package com.company.part2.lab5.client;

public class ClientMain {
    public static void main(String[] args) {
        DepartmentManagerMOMClient client = new DepartmentManagerMOMClient("localhost");
        System.out.println(client.getGroups());
    }
}
