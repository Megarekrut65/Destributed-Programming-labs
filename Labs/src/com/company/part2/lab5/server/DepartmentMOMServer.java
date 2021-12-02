package com.company.part2.lab5.server;

import com.company.part2.lab2.DepartmentSqlManager;
import com.company.part2.lab5.Converter;
import com.company.part2.subjectarea.ServerResults;
import com.company.part2.subjectarea.*;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

public class DepartmentMOMServer extends DepartmentServer {
    private final DepartmentSqlManager database;
    private final String QUEUE_NAME = "DepartmentDatabase";
    private Connection connection;
    private Channel channel;
    private String currentReply;
    private AMQP.BasicProperties currentProps;
    public DepartmentMOMServer() {
        super();
        database = new DepartmentSqlManager("localhost", 3306, "department");
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected boolean getGroups(){
        try {
            var groups = database.getGroups();
            if(groups == null) groups = new ArrayList<>();
            log("Found " + groups.size() + " groups");
            channel.basicPublish("", currentReply, currentProps, Converter.getBytes(groups));
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
    @Override
    protected boolean findGroup(){
        /*try {
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
        }*/
        return false;
    }
    @Override
    protected boolean getStudents() {
        /*try {
            var students = database.getStudents();
            if(students == null) students = new ArrayList<>();
            System.out.println("Found " + students.size() + " students");
            out.writeObject(students);
            return true;
        }catch (IOException e) {
            e.printStackTrace();
        }*/
        return false;
    }
    @Override
    protected boolean getStudentsInGroup(){
        /*try {
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
        }*/
        return false;
    }


    public void run() {
        try {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.queuePurge(QUEUE_NAME);
            channel.basicQos(1);
            Object monitor = new Object();
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                AMQP.BasicProperties replyProps = new AMQP.BasicProperties
                        .Builder()
                        .correlationId(delivery.getProperties().getCorrelationId())
                        .build();

                String code = "";
                try {
                    code =  new String(delivery.getBody(), StandardCharsets.UTF_8);
                    logln("Command: "+code);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                } finally {
                    var fun = functionMap.get(code);
                    String answer;
                    if(fun != null){
                       answer = ServerResults.SUCCESSFUL.code();
                    }else answer = ServerResults.UNKNOWN_COMMAND.code();
                    channel.basicPublish("", delivery.getProperties().getReplyTo(), replyProps, answer.getBytes(StandardCharsets.UTF_8));
                    currentProps = replyProps;
                    currentReply = delivery.getProperties().getReplyTo();
                    fun.call();
                    synchronized (monitor) {
                        monitor.notify();
                    }
                }
            };
            channel.basicConsume(QUEUE_NAME, false, deliverCallback, (consumerTag -> { }));
            while (true) {
                synchronized (monitor) {
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
