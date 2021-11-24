package com.company.part2.lab2;


import com.company.part2.subjectarea.DepartmentManagerExamples;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        DepartmentSqlManager manager = new DepartmentSqlManager("localhost", 3306, "department");
        new DepartmentManagerExamples(manager);
        try {
            manager.stop();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
