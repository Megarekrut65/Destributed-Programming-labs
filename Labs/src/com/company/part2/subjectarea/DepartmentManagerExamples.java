package com.company.part2.subjectarea;

public class DepartmentManagerExamples {
    private final DepartmentManager manager;

    public DepartmentManagerExamples(DepartmentManager manager) {
        this.manager = manager;
        System.out.println(manager.getGroups());
        System.out.println(manager.getStudents());
        addGroups();
        System.out.println(manager.getGroups());
        addStudents();
        System.out.println(manager.getStudents());
        System.out.println("\nStudents in group 3");
        System.out.println(manager.getStudentsInGroup(3));
        findGroup();
        findStudent();
        deleteStudents();
        System.out.println(manager.getStudents());
        deleteGroups();
        System.out.println(manager.getGroups());
        manager.close();
    }
    private void findStudent(){
        System.out.println("\nFinding student with id 6 in group with id 3");
        System.out.println(manager.findStudent(6,3));
    }
    private void findGroup(){
        System.out.println("\nFinding group with id 5");
        System.out.println(manager.findGroup(5));
    }
    private void addGroups(){
        System.out.println("\nAdding 3 groups");
        manager.addGroup(new Group(3,"T-9", 1, "full-time"));
        manager.addGroup(new Group(4,"MK-1", 2, "full-time"));
        manager.addGroup(new Group(5,"TL-14", 3, "full-time"));
    }
    private void addStudents(){
        System.out.println("\nAdding 4 students");
        manager.addStudent(new Student(5,3,"Karl", "III", 28, 89));
        manager.addStudent(new Student(6,3,"Ludovic", "IV", 21, 68));
        manager.addStudent(new Student(7,4,"Elizabet", "II", 12, 100));
        manager.addStudent(new Student(8,5,"August", "V", 31, 76));
    }
    private void deleteStudents(){
        System.out.println("\nRemoving 4 students");
        manager.deleteStudent(5,3);
        manager.deleteStudent(6,3);
        manager.deleteStudent(7,4);
        manager.deleteStudent(8,5);
    }
    private void deleteGroups(){
        System.out.println("\nRemoving 3 groups");
        manager.deleteGroup(3);
        manager.deleteGroup(4);
        manager.deleteGroup(5);
    }
}
