package com.company.part2.lab5.client;

import com.company.part2.subjectarea.Group;

public class ClientMain {
    public static void main(String[] args) {
        try(DepartmentManagerMOMClient client = new DepartmentManagerMOMClient("localhost")){
            System.out.println(client.getGroups());
            System.out.println(client.getStudents());
            System.out.println(client.addGroup(new Group(3,"T-9", 1, "full-time")));
            System.out.println(client.getGroups());
            System.out.println(client.deleteGroup(3));
            System.out.println(client.getGroups());
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Client finished work");
        }

    }
}
