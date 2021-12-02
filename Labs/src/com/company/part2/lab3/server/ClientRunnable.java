package com.company.part2.lab3.server;

import com.company.part2.lab2.DepartmentSqlManager;
import com.company.part2.lab3.ServerResults;
import com.company.part2.subjectarea.DepartmentServer;
import com.company.part2.subjectarea.Group;
import com.company.part2.subjectarea.Student;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientRunnable extends DepartmentServer implements Runnable {
    private final Socket client;
    private final DepartmentSqlManager database;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    public ClientRunnable(Socket client, DepartmentSqlManager database) {
        super();
        this.client = client;
        this.database = database;
        try {
            out = new ObjectOutputStream(client.getOutputStream());
            in = new ObjectInputStream(client.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private boolean work() throws IOException, ClassNotFoundException {
        String code = (String)in.readObject();
        logln("Command: "+code);
        var fun = functionMap.get(code);
        if(fun != null){
            out.writeObject(ServerResults.SUCCESSFUL.code());
            return fun.call();
        }
        out.writeObject(ServerResults.UNKNOWN_COMMAND.code());
        return false;
    }
    @Override
    protected boolean addGroup(){
        try {
            Group group = (Group) in.readObject();
            log(group.toString());
            if(database.addGroup(group)){
                out.writeObject(ServerResults.SUCCESSFUL.code());
                logln(" was added");
            }
            else{
                out.writeObject(ServerResults.PARAMETERS_ERROR.code());
                logln(" wasn't added");
            }
            return true;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    protected boolean addStudent(){
        try {
            Student student = (Student) in.readObject();
            log(student.toString());
            if(database.addStudent(student)){
                out.writeObject(ServerResults.SUCCESSFUL.code());
                logln(" was added");
            }
            else{
                out.writeObject(ServerResults.PARAMETERS_ERROR.code());
                logln(" wasn't added");
            }
            return true;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    protected boolean deleteStudent(){
        try {
            int id = (int)in.readObject();
            int groupId = (int)in.readObject();
            log("Id: " + id + ", group id: " + groupId);
            if(database.deleteStudent(id,groupId)) {
                out.writeObject(ServerResults.SUCCESSFUL.code());
                logln(", student was removed");
            }
            else{
                out.writeObject(ServerResults.NOT_FOUND.code());
                logln(", not found");
            }
            return true;
        }catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    protected boolean deleteGroup(){
        try {
            int id = (int)in.readObject();
            log("Id: " + id);
            if(database.deleteGroup(id)) {
                out.writeObject(ServerResults.SUCCESSFUL.code());
                logln(", group was removed");
            }
            else{
                out.writeObject(ServerResults.NOT_FOUND.code());
                logln(", not found");
            }
            return true;
        }catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    protected boolean findStudent(){
        try {
            int id = (int)in.readObject();
            int groupId = (int)in.readObject();
            var student = database.findStudent(id,groupId);
            log("Id: " + id + ", group id: " + groupId);
            if(student != null) {
                out.writeObject(ServerResults.SUCCESSFUL.code());
                logln(", found student: " + student.getName());
                out.writeObject(student);
            }
            else{
                out.writeObject(ServerResults.NOT_FOUND.code());
                logln(", not found");
            }
            return true;
        }catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    protected boolean findGroup(){
        try {
            int id = (int)in.readObject();
            var group = database.findGroup(id);
            log("Id: " + id);
            if(group != null) {
                out.writeObject(ServerResults.SUCCESSFUL.code());
                logln(", found group: " + group.getName());
                out.writeObject(group);
            }
            else{
                out.writeObject(ServerResults.NOT_FOUND.code());
                logln(", not found");
            }
            return true;
        }catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    protected boolean getStudents() {
        try {
            var students = database.getStudents();
            if(students == null) students = new ArrayList<>();
            logln("Found " + students.size() + " students");
            out.writeObject(students);
            return true;
        }catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    protected boolean getStudentsInGroup(){
        try {
            int groupId = (int)in.readObject();
            var students = database.getStudentsInGroup(groupId);
            log("Group id: " + groupId);
            if(students != null){
                out.writeObject(ServerResults.SUCCESSFUL.code());
                logln(", found " + students.size() + " students");
                out.writeObject(students);
            }
            else {
                out.writeObject(ServerResults.NOT_FOUND.code());
                logln(", not found");
            }

            return true;
        }catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    protected boolean getGroups(){
        try {
            var groups = database.getGroups();
            if(groups == null) groups = new ArrayList<>();
            logln("Found " + groups.size() + " groups");
            out.writeObject(groups);
            return true;
        }catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public void run() {
        logln(client + " is connected!");
        try {
            while(!Thread.interrupted() && client.isConnected()){
                if(!work()){
                    logln("Error with last command!");
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            logln(client + " disconnect!");
        }
    }
}
