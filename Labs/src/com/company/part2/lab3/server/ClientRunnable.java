package com.company.part2.lab3.server;

import com.company.part2.lab2.DepartmentSqlManager;
import com.company.part2.lab3.Commands;
import com.company.part2.lab3.ServerResults;
import com.company.part2.subjectarea.Group;
import com.company.part2.subjectarea.Student;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClientRunnable implements Runnable{
    private final Socket client;
    private final DepartmentSqlManager database;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private final Map<String, Function> functionMap;
    public ClientRunnable(Socket client, DepartmentSqlManager database) {
        this.client = client;
        this.database = database;
        functionMap = createMap();
        try {
            out = new ObjectOutputStream(client.getOutputStream());
            in = new ObjectInputStream(client.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private Map<String,Function> createMap(){
        var map = new HashMap<String,Function>();
        map.put(Commands.ADD_GROUP.code(), this::addGroup);
        map.put(Commands.ADD_STUDENT.code(), this::addStudent);
        map.put(Commands.DELETE_GROUP.code(), this::deleteGroup);
        map.put(Commands.DELETE_STUDENT.code(), this::deleteStudent);
        map.put(Commands.FIND_GROUP.code(), this::findGroup);
        map.put(Commands.FIND_STUDENT.code(), this::findStudent);
        map.put(Commands.GET_GROUPS.code(), this::getGroups);
        map.put(Commands.GET_STUDENTS.code(), this::getStudents);
        map.put(Commands.GET_STUDENTS_IN_GROUP.code(), this::getStudentsInGroup);
        return map;
    }
    private boolean work() throws IOException, ClassNotFoundException {
        String code = (String)in.readObject();
        System.out.println("Command: "+code);
        var fun = functionMap.get(code);
        if(fun != null){
            out.writeObject(ServerResults.SUCCESSFUL.code());
            return fun.call();
        }
        out.writeObject(ServerResults.UNKNOWN_COMMAND.code());
        return false;
    }
    private boolean addGroup(){
        try {
            Group group = (Group) in.readObject();
            System.out.print(group);
            if(database.addGroup(group)){
                out.writeObject(ServerResults.SUCCESSFUL.code());
                System.out.println(" was added");
            }
            else{
                out.writeObject(ServerResults.PARAMETERS_ERROR.code());
                System.out.println(" wasn't added");
            }
            return true;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
    private boolean addStudent(){
        try {
            Student student = (Student) in.readObject();
            System.out.print(student);
            if(database.addStudent(student)){
                out.writeObject(ServerResults.SUCCESSFUL.code());
                System.out.println(" was added");
            }
            else{
                out.writeObject(ServerResults.PARAMETERS_ERROR.code());
                System.out.println(" wasn't added");
            }
            return true;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
    private boolean deleteStudent(){
        try {
            int id = (int)in.readObject();
            int groupId = (int)in.readObject();
            System.out.print("Id: " + id + ", group id: " + groupId);
            if(database.deleteStudent(id,groupId)) {
                out.writeObject(ServerResults.SUCCESSFUL.code());
                System.out.println(", student was removed");
            }
            else{
                out.writeObject(ServerResults.NOT_FOUND.code());
                System.out.println(", not found");
            }
            return true;
        }catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
    private boolean deleteGroup(){
        try {
            int id = (int)in.readObject();
            System.out.print("Id: " + id);
            if(database.deleteGroup(id)) {
                out.writeObject(ServerResults.SUCCESSFUL.code());
                System.out.println(", group was removed");
            }
            else{
                out.writeObject(ServerResults.NOT_FOUND.code());
                System.out.println(", not found");
            }
            return true;
        }catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
    private boolean findStudent(){
        try {
            int id = (int)in.readObject();
            int groupId = (int)in.readObject();
            var student = database.findStudent(id,groupId);
            System.out.print("Id: " + id + ", group id: " + groupId);
            if(student != null) {
                out.writeObject(ServerResults.SUCCESSFUL.code());
                System.out.println(", found student: " + student.getName());
                out.writeObject(student);
            }
            else{
                out.writeObject(ServerResults.NOT_FOUND.code());
                System.out.println(", not found");
            }
            return true;
        }catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
    private boolean findGroup(){
        try {
            int id = (int)in.readObject();
            var group = database.findGroup(id);
            System.out.print("Id: " + id);
            if(group != null) {
                out.writeObject(ServerResults.SUCCESSFUL.code());
                System.out.println(", found group: " + group.getName());
                out.writeObject(group);
            }
            else{
                out.writeObject(ServerResults.NOT_FOUND.code());
                System.out.println(", not found");
            }
            return true;
        }catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
    private boolean getStudents() {
        try {
            var students = database.getStudents();
            if(students == null) students = new ArrayList<>();
            System.out.println("Found " + students.size() + " students");
            out.writeObject(students);
            return true;
        }catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    private boolean getStudentsInGroup(){
        try {
            int groupId = (int)in.readObject();
            var students = database.getStudentsInGroup(groupId);
            System.out.print("Group id: " + groupId);
            if(students != null){
                out.writeObject(ServerResults.SUCCESSFUL.code());
                System.out.println(", found " + students.size() + " students");
                out.writeObject(students);
            }
            else {
                out.writeObject(ServerResults.NOT_FOUND.code());
                System.out.println(", not found");
            }

            return true;
        }catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
    private boolean getGroups(){
        try {
            var groups = database.getGroups();
            if(groups == null) groups = new ArrayList<>();
            System.out.println("Found " + groups.size() + " groups");
            out.writeObject(groups);
            return true;
        }catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public void run() {
        System.out.println(client + " is connected!");
        try {
            while(!Thread.interrupted() && client.isConnected()){
                if(!work()){
                    System.err.println("Error with last command!");
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(client + " disconnect!");
        }
    }
    interface Function{
        boolean call();
    }
}
