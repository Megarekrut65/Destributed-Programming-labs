package com.company.part2.lab5.client;

public class ClientMain {
    public static void main(String[] args) {
        try(DepartmentManagerMOMClient client = new DepartmentManagerMOMClient("localhost")){
            System.out.println(client.getGroups());
            System.out.println(client.getStudents());
            System.out.println(client.getStudentsInGroup(1));
            System.out.println(client.getStudentsInGroup(10));
        }catch (Exception ignored){
            System.out.println("Client finished work");
        }

    }
}
