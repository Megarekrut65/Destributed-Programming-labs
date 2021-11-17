package com.company.part2.lab2;


import com.company.part2.subjectarea.Group;
import com.company.part2.subjectarea.Student;

import java.sql.SQLException;

public class Main {
    private static DepartmentSqlManager manager;
    public static void main(String[] args) {
        manager = new DepartmentSqlManager("localhost", 3306, "department");
        manager.showGroups();
        manager.showStudents();
        addGroups();
        manager.showGroups();
        addStudents();
        manager.showStudents();
        manager.showStudents(3);
        findGroup();
        findStudent();
        deleteStudents();
        manager.showStudents();
        deleteGroups();
        manager.showGroups();
        try {
            manager.stop();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void findStudent(){
        System.out.println("\nFinding student with id 6 in group with id 3");
        System.out.println(manager.findStudent(6,3));
    }
    private static void findGroup(){
        System.out.println("\nFinding group with id 5");
        System.out.println(manager.findGroup(5));
    }
    private static void addGroups(){
        System.out.println("\nAdding 3 groups");
        manager.addGroup(new Group(3,"T-9", 1, "full-time"));
        manager.addGroup(new Group(4,"MK-1", 2, "full-time"));
        manager.addGroup(new Group(5,"TL-14", 3, "full-time"));
    }
    private static void addStudents(){
        System.out.println("\nAdding 4 students");
        manager.addStudent(new Student(5,3,"Karl", "III", 28, 89));
        manager.addStudent(new Student(6,3,"Ludovic", "IV", 21, 68));
        manager.addStudent(new Student(7,4,"Elizabet", "II", 12, 100));
        manager.addStudent(new Student(8,5,"August", "V", 31, 76));
    }
    private static void deleteStudents(){
        System.out.println("\nRemoving 4 students");
        manager.deleteStudent(5,3);
        manager.deleteStudent(6,3);
        manager.deleteStudent(7,4);
        manager.deleteStudent(8,5);
    }
    private static void deleteGroups(){
        System.out.println("\nRemoving 3 groups");
        manager.deleteGroup(3);
        manager.deleteGroup(4);
        manager.deleteGroup(5);
    }
}
