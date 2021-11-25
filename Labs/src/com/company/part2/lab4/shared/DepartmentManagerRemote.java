package com.company.part2.lab4.shared;

import com.company.part2.subjectarea.Group;
import com.company.part2.subjectarea.Student;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface DepartmentManagerRemote extends Remote {
    boolean addStudent(Student student) throws RemoteException;
    boolean addGroup(Group group)throws RemoteException;
    boolean deleteStudent(int id, int groupId)throws RemoteException;
    boolean deleteGroup(int id)throws RemoteException;
    Student findStudent(int id, int groupId)throws RemoteException;
    Group findGroup(int id)throws RemoteException;
    List<Student> getStudentsInGroup(int groupId)throws RemoteException;
    List<Student> getStudents()throws RemoteException;
    List<Group> getGroups()throws RemoteException;
}
