package com.company.part2.lab3;

import com.company.part2.lab3.client.DepartmentManagerClient;
import com.company.part2.subjectarea.DepartmentManagerExamples;

public class MainClient {
    //Start MainServer first
    public static void main(String[] args) {
        DepartmentManagerClient client = new DepartmentManagerClient("0.0.0.0", 8085);
        new DepartmentManagerExamples(client);
    }
}
