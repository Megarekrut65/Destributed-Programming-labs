package com.company.part2.lab5.client;

import com.company.part2.subjectarea.DepartmentManagerExamples;

public class ClientMain {
    public static void main(String[] args) {
        new DepartmentManagerExamples(new DepartmentManagerMOMClient("localhost"));
    }
}
