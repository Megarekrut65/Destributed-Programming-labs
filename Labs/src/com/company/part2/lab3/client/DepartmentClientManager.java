package com.company.part2.lab3.client;

import com.company.part2.lab3.Commands;
import com.company.part2.lab3.ServerResults;
import com.company.part2.subjectarea.DepartmentManager;
import com.company.part2.subjectarea.Group;
import com.company.part2.subjectarea.Student;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class DepartmentClientManager implements DepartmentManager {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    public DepartmentClientManager(String ip, int port){
        try {
            socket = new Socket(ip, port);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<Group> getGroups(){
        try {
            out.writeObject(Commands.GET_GROUPS.code());
            String answer = (String) in.readObject();
            if(answer.equals(ServerResults.SUCCESSFUL.code())) {
                return (List<Group>) in.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public List<Student> getStudents(){
        try {
            out.writeObject(Commands.GET_STUDENTS.code());
            String answer = (String) in.readObject();
            if(answer.equals(ServerResults.SUCCESSFUL.code())) {
                return (List<Student>) in.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public List<Student> getStudentsInGroup(int groupId){
        try {
            out.writeObject(Commands.GET_STUDENTS_IN_GROUP.code());
            String answer = (String) in.readObject();
            if(answer.equals(ServerResults.SUCCESSFUL.code())) {
                out.writeObject(groupId);
                answer = (String) in.readObject();
                if(answer.equals(ServerResults.SUCCESSFUL.code()))
                    return (List<Student>) in.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
    @Override
    public Group findGroup(int id) {
        try {
            out.writeObject(Commands.FIND_GROUP.code());
            String answer = (String) in.readObject();
            if(answer.equals(ServerResults.SUCCESSFUL.code())) {
                out.writeObject(id);
                answer = (String) in.readObject();
                if(answer.equals(ServerResults.SUCCESSFUL.code()))
                    return (Group) in.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
    @Override
    public Student findStudent(int id, int groupId){
        try {
            out.writeObject(Commands.FIND_STUDENT.code());
            String answer = (String) in.readObject();
            if(answer.equals(ServerResults.SUCCESSFUL.code())) {
                out.writeObject(id);
                out.writeObject(groupId);
                answer = (String) in.readObject();
                if(answer.equals(ServerResults.SUCCESSFUL.code()))
                    return (Student) in.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
    @Override
    public boolean deleteGroup(int id){
        try {
            out.writeObject(Commands.DELETE_GROUP.code());
            String answer = (String) in.readObject();
            if(answer.equals(ServerResults.SUCCESSFUL.code())) {
                out.writeObject(id);
                answer = (String) in.readObject();
                return answer.equals(ServerResults.SUCCESSFUL.code());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }
    @Override
    public boolean deleteStudent(int id, int groupId){
        try {
            out.writeObject(Commands.DELETE_STUDENT.code());
            String answer = (String) in.readObject();
            if(answer.equals(ServerResults.SUCCESSFUL.code())) {
                out.writeObject(id);
                out.writeObject(groupId);
                answer = (String) in.readObject();
                return answer.equals(ServerResults.SUCCESSFUL.code());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }
    @Override
    public boolean addGroup(Group group){
        try {
            out.writeObject(Commands.ADD_GROUP.code());
            String answer = (String) in.readObject();
            if(answer.equals(ServerResults.SUCCESSFUL.code())) {
                out.writeObject(group);
                answer = (String) in.readObject();
                return answer.equals(ServerResults.SUCCESSFUL.code());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }
    @Override
    public boolean addStudent(Student student){
        try {
            out.writeObject(Commands.ADD_STUDENT.code());
            String answer = (String) in.readObject();
            if(answer.equals(ServerResults.SUCCESSFUL.code())) {
                out.writeObject(student);
                answer = (String) in.readObject();
                return answer.equals(ServerResults.SUCCESSFUL.code());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }
}
