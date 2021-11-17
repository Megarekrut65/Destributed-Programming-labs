package com.company.part2.lab2;


import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        DepartmentSqlManager manager = new DepartmentSqlManager("localhost", 3306, "department");
        manager.showGroups();
        manager.deleteGroup(3);
        manager.showGroups();
        manager.showStudents();
        manager.deleteStudent(5,2);
        manager.showStudents();
        try {
            manager.stop();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
