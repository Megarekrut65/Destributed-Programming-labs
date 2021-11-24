package com.company.part2.subjectarea;

import java.util.List;

public interface DepartmentManager {
    boolean addStudent(Student student);
    boolean addGroup(Group group);
    boolean deleteStudent(int id, int groupId);
    boolean deleteGroup(int id);
    Student findStudent(int id, int groupId);
    Group findGroup(int id);
    List<Student> getStudentsInGroup(int groupId);
    List<Student> getStudents();
    List<Group> getGroups();
}
