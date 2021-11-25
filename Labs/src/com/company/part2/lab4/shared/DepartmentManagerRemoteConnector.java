package com.company.part2.lab4.shared;

import com.company.part2.lab4.shared.DepartmentManagerRemote;
import com.company.part2.subjectarea.DepartmentManager;
import com.company.part2.subjectarea.Group;
import com.company.part2.subjectarea.Student;

import java.rmi.RemoteException;
import java.util.List;

public class DepartmentManagerRemoteConnector implements DepartmentManager {
    private final DepartmentManagerRemote managerRemote;

    public DepartmentManagerRemoteConnector(DepartmentManagerRemote managerRemote) {
        this.managerRemote = managerRemote;
    }

    @Override
    public boolean addStudent(Student student) {
        try {
            return managerRemote.addStudent(student);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean addGroup(Group group) {
        try {
            return managerRemote.addGroup(group);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteStudent(int id, int groupId) {
        try {
            return managerRemote.deleteStudent(id,groupId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteGroup(int id) {
        try {
            return managerRemote.deleteGroup(id);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Student findStudent(int id, int groupId) {
        try {
            return managerRemote.findStudent(id,groupId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Group findGroup(int id) {
        try {
            return managerRemote.findGroup(id);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Student> getStudentsInGroup(int groupId) {
        try {
            return managerRemote.getStudentsInGroup(groupId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Student> getStudents() {
        try {
            return managerRemote.getStudents();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Group> getGroups() {
        try {
            return managerRemote.getGroups();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }
}
