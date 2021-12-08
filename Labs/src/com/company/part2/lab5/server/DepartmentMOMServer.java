package com.company.part2.lab5.server;

import com.company.part2.lab2.DepartmentSqlManager;
import com.company.part2.lab5.Converter;
import com.company.part2.subjectarea.ServerResults;
import com.company.part2.subjectarea.*;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

public class DepartmentMOMServer extends DepartmentServer {
    private final DepartmentSqlManager database;
    private Connection connection;
    private Channel channelTo;
    private Channel parameters;
    private Channel channelFrom;
    private final String QUEUE_NAME_TO = "DepartmentDatabaseFrom";
    private final String QUEUE_NAME_FROM = "DepartmentDatabaseTo";
    private final String QUEUE_NAME_PARAMETERS = "DepartmentDatabaseParameters";
    private String currentTag;
    public DepartmentMOMServer() {
        super();
        database = new DepartmentSqlManager("localhost", 3306, "department");
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try {
            connection = factory.newConnection();
            channelTo = connection.createChannel();
            channelTo.queueDeclare(QUEUE_NAME_TO, false, false, false, null);
            channelFrom = connection.createChannel();
            channelFrom.queueDeclare(QUEUE_NAME_FROM, false, false, false, null);
            parameters = connection.createChannel();
            parameters.queueDeclare(QUEUE_NAME_PARAMETERS, false, false, false, null);
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected boolean getGroups(){
        try {
            var groups = database.getGroups();
            if(groups == null) groups = new ArrayList<>();
            logln("Found " + groups.size() + " groups");
            channelTo.basicPublish("", QUEUE_NAME_TO, null,
                    Converter.getBytes(groups));
            return true;
        }catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    protected boolean addGroup(){
        /*try {
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
        }*/
        return false;
    }
    @Override
    protected boolean addStudent(){
       /* try {
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
        }*/
        return false;
    }
    @Override
    protected boolean deleteStudent(){
       /*try {
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
        }*/
        return false;
    }
    @Override
    protected boolean deleteGroup(){
        /*try {
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
        }*/
        return false;
    }
    @Override
    protected boolean findStudent(){
        /*try {
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
        }*/
        return false;
    }
    private int getId(){
        final BlockingQueue<Integer> groupId = new ArrayBlockingQueue<>(1);
        DeliverCallback deliverCallback = (s, delivery) -> {
            try {
                groupId.offer((Integer) Converter.getObject(delivery.getBody()));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        };
        try {
            String tag = parameters.basicConsume(QUEUE_NAME_PARAMETERS, true, deliverCallback, consumerTag -> { });
            int id = groupId.take();
            parameters.basicCancel(tag);
            return id;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return -1;
    }
    @Override
    protected boolean findGroup(){
        try {
            int id = getId();
            var group = database.findGroup(id);
            log("Id: " + id);
            if(group != null) {
                channelTo.basicPublish("", QUEUE_NAME_TO, null,
                        ServerResults.SUCCESSFUL.bytes());
                logln(", found group: " + group.getName());
                channelTo.basicPublish("", QUEUE_NAME_TO, null,
                        Converter.getBytes(group));
            }
            else{
                channelTo.basicPublish("", QUEUE_NAME_TO, null,
                        ServerResults.NOT_FOUND.bytes());
                logln(", not found");
            }
            return true;
        }catch (IOException e) {
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
            channelTo.basicPublish("", QUEUE_NAME_TO, null,
                    Converter.getBytes(students));
            return true;
        }catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    protected boolean getStudentsInGroup(){
        try {
            int grId = getId();
            var students = database.getStudentsInGroup(grId);
            log("Group id: " + grId);
            if(students != null){
                channelTo.basicPublish("", QUEUE_NAME_TO, null,
                        ServerResults.SUCCESSFUL.bytes());
                logln(", found " + students.size() + " students");
                channelTo.basicPublish("", QUEUE_NAME_TO, null,
                        Converter.getBytes(students));
            }
            else {
                channelTo.basicPublish("", QUEUE_NAME_TO, null,
                        ServerResults.NOT_FOUND.bytes());
                logln(", not found");
            }
            return true;
        }catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private DeliverCallback getCommandCallBack(){
        return  (consumerTag, delivery) -> {
            String command = new String(delivery.getBody(), StandardCharsets.UTF_8);
            logln("Command: "+command);
            var fun = functionMap.get(command);
            if(fun != null){
                channelTo.basicPublish("",QUEUE_NAME_TO,  null, ServerResults.SUCCESSFUL.bytes());
                fun.call();
            }
            else channelTo.basicPublish("",QUEUE_NAME_TO,  null, ServerResults.UNKNOWN_COMMAND.bytes());
        };
    }
    public void run() {
        DeliverCallback deliverCallback = getCommandCallBack();
        try {
            currentTag = channelFrom.basicConsume(QUEUE_NAME_FROM, true, deliverCallback, consumerTag -> { });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
