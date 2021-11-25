package com.company.part2.lab4.server;

import com.company.part2.lab2.DepartmentSqlManager;
import com.company.part2.lab4.shared.DepartmentManagerRemote;
import com.company.part2.subjectarea.DepartmentManager;
import com.company.part2.subjectarea.Group;
import com.company.part2.subjectarea.Student;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class DepartmentManagerRMIServer extends UnicastRemoteObject implements DepartmentManagerRemote {
    private final DepartmentManager database;

    public DepartmentManagerRMIServer(int port) throws RemoteException {
        database = new DepartmentSqlManager("localhost", port, "department");
    }

    @Override
    public boolean addStudent(Student student) throws RemoteException {
        return database.addStudent(student);
    }

    @Override
    public boolean addGroup(Group group) throws RemoteException {
        return database.addGroup(group);
    }

    @Override
    public boolean deleteStudent(int id, int groupId) throws RemoteException {
        return database.deleteStudent(id,groupId);
    }

    @Override
    public boolean deleteGroup(int id) throws RemoteException {
        return database.deleteGroup(id);
    }

    @Override
    public Student findStudent(int id, int groupId) throws RemoteException {
        return database.findStudent(id,groupId);
    }

    @Override
    public Group findGroup(int id) throws RemoteException {
        return database.findGroup(id);
    }

    @Override
    public List<Student> getStudentsInGroup(int groupId) throws RemoteException {
        return database.getStudentsInGroup(groupId);
    }

    @Override
    public List<Student> getStudents() throws RemoteException {
        return database.getStudents();
    }

    @Override
    public List<Group> getGroups() throws RemoteException {
        return database.getGroups();
    }
}
