package com.company.part2.lab3.server;

import com.company.part2.lab2.DepartmentSqlManager;
import com.company.part2.lab3.Commands;
import com.company.part2.lab3.ServerResults;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ClientManager implements Runnable{
    private final Socket client;
    private final DepartmentSqlManager database;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Map<String, Function> functionMap;
    public ClientManager(Socket client,DepartmentSqlManager database) {
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
        map.put(Commands.ADD_GROUP.code(), this::add_group);
        map.put(Commands.ADD_STUDENT.code(), this::add_student);
        map.put(Commands.DELETE_GROUP.code(), this::delete_group);
        map.put(Commands.DELETE_STUDENT.code(), this::delete_student);
        map.put(Commands.FIND_GROUP.code(), this::find_group);
        map.put(Commands.FIND_STUDENT.code(), this::find_student);
        map.put(Commands.GET_GROUP.code(), this::get_group);
        map.put(Commands.GET_STUDENT.code(), this::get_student);
        map.put(Commands.GET_GROUPS.code(), this::get_groups);
        map.put(Commands.GET_STUDENTS.code(), this::get_students);
        map.put(Commands.GET_STUDENTS_IN_GROUP.code(), this::get_students_in_group);
        return map;
    }
    private boolean work() throws IOException, ClassNotFoundException {
        String code = (String)in.readObject();
        System.out.println(code);
        var fun = functionMap.get(code);
        if(fun != null){
            out.writeObject(ServerResults.SUCCESSFUL.code());
            return fun.call();
        }
        out.writeObject(ServerResults.UNKNOWN_COMMAND.code());
        return false;
    }
    private boolean add_group(){
        return false;
    }
    private boolean add_student(){

        return false;
    }
    private boolean delete_student(){

        return false;
    }
    private boolean delete_group(){

        return false;
    }
    private boolean find_student(){

        return false;
    }
    private boolean find_group(){

        return false;
    }
    private boolean get_students() {
        try {
            var students = database.getStudents();
            out.writeObject(students);
            return true;
        }catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    private boolean get_students_in_group(){

        return false;
    }
    private boolean get_student(){

        return false;
    }
    private boolean get_groups(){

        return false;
    }
    private boolean get_group(){

        return false;
    }
    @Override
    public void run() {
        System.out.println(client + " is connected!");
        try {
            while(!Thread.interrupted() && client.isConnected()){
                work();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(client + " disconnect!");
        }
    }
    interface Function{
        boolean call();
    }
}
