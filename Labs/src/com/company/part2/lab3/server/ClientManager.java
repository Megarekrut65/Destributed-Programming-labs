package com.company.part2.lab3.server;

import com.company.part2.lab2.DepartmentSqlManager;
import com.company.part2.lab3.Commands;
import com.company.part2.lab3.ServerResults;

import java.io.*;
import java.net.Socket;

public class ClientManager implements Runnable{
    private final Socket client;
    private final DepartmentSqlManager database;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    public ClientManager(Socket client,DepartmentSqlManager database) {
        this.client = client;
        this.database = database;
    }
    private boolean work() throws IOException, ClassNotFoundException {
        String code = (String)in.readObject();
        if(code.equals(Commands.ADD_GROUP.code())) return add_group();
        if(code.equals(Commands.ADD_STUDENT.code())) return add_student();
        if(code.equals(Commands.DELETE_GROUP.code())) return delete_group();
        if(code.equals(Commands.DELETE_STUDENT.code())) return delete_student();
        if(code.equals(Commands.FIND_GROUP.code())) return find_group();
        if(code.equals(Commands.FIND_STUDENT.code())) return find_student();
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
    private boolean get_students(){
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
        try {
            var bufferedInputStream = new BufferedInputStream(client.getInputStream());
            var bufferedOutputStream = new BufferedOutputStream(client.getOutputStream());
            while(!Thread.interrupted() && client.isConnected()){
                in = new ObjectInputStream(bufferedInputStream);
                out = new ObjectOutputStream(bufferedOutputStream);
                work();
            }
            bufferedInputStream.close();
            bufferedOutputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
