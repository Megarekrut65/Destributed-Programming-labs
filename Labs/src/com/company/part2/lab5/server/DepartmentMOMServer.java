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
    private final String QUEUE_NAME_TO = "DepartmentFrom";
    private final String QUEUE_NAME_FROM = "DepartmentTo";
    private final String QUEUE_NAME_PARAMETERS = "DepartmentParameters";
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
        try {
            Group group = (Group) getObject();
            log(""+group);
            if(group != null && database.addGroup(group)){
                channelTo.basicPublish("", QUEUE_NAME_TO, null,
                        ServerResults.SUCCESSFUL.bytes());
                logln(" was added");
                channelTo.basicPublish("", QUEUE_NAME_TO, null,
                        Converter.getBytes(true));
            }
            else{
                channelTo.basicPublish("", QUEUE_NAME_TO, null,
                        ServerResults.PARAMETERS_ERROR.bytes());
                logln(" wasn't added");
            }
            return true;
        }catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    protected boolean addStudent(){
        try {
            Student student = (Student) getObject();
            log(""+student);
            if(student != null && database.addStudent(student)){
                channelTo.basicPublish("", QUEUE_NAME_TO, null,
                        ServerResults.SUCCESSFUL.bytes());
                logln(" was added");
                channelTo.basicPublish("", QUEUE_NAME_TO, null,
                        Converter.getBytes(true));
            }
            else{
                channelTo.basicPublish("", QUEUE_NAME_TO, null,
                        ServerResults.PARAMETERS_ERROR.bytes());
                logln(" wasn't added");
            }
            return true;
        }catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    protected boolean deleteStudent(){
        try {
            Integer[] ids = (Integer[]) getObject();
            if(ids == null) ids = new Integer[]{-1,-1};
            int id = ids[0];
            int groupId = ids[1];
            log("Id: " + id + ", group id: " + groupId);
            if(database.deleteStudent(id,groupId)) {
                channelTo.basicPublish("", QUEUE_NAME_TO, null,
                        ServerResults.SUCCESSFUL.bytes());
                logln(", student was removed");
                channelTo.basicPublish("", QUEUE_NAME_TO, null,
                        Converter.getBytes(true));
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
    protected boolean deleteGroup(){
        try {
            Integer id = (Integer) getObject();
            if(id == null) id = -1;
            log("Id: " + id);
            if(database.deleteGroup(id)) {
                channelTo.basicPublish("", QUEUE_NAME_TO, null,
                        ServerResults.SUCCESSFUL.bytes());
                channelTo.basicPublish("", QUEUE_NAME_TO, null,
                        Converter.getBytes(true));
                logln(", group was removed");
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
    protected boolean findStudent(){
        try {
            Integer[] ids = (Integer[]) getObject();
            if(ids == null) ids = new Integer[]{-1,-1};
            int id = ids[0];
            int groupId = ids[1];
            var student = database.findStudent(id,groupId);
            log("Id: " + id + ", group id: " + groupId);
            if(student != null) {
                channelTo.basicPublish("", QUEUE_NAME_TO, null,
                        ServerResults.SUCCESSFUL.bytes());
                logln(", found student: " + student.getName());
                channelTo.basicPublish("", QUEUE_NAME_TO, null,
                        Converter.getBytes(student));
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
    private Object getObject(){
        final BlockingQueue<Object> objects = new ArrayBlockingQueue<>(1);
        DeliverCallback deliverCallback = (s, delivery) -> {
            try {
                objects.offer(Converter.getObject(delivery.getBody()));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        };
        try {
            String tag = parameters.basicConsume(QUEUE_NAME_PARAMETERS, true, deliverCallback, consumerTag -> { });
            Object obj = objects.take();
            parameters.basicCancel(tag);
            return obj;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected boolean findGroup(){
        try {
            Integer id = (Integer) getObject();
            if(id == null) id = -1;
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
            Integer grId = (Integer) getObject();
            if(grId == null) grId = -1;
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
            channelFrom.basicConsume(QUEUE_NAME_FROM, true, deliverCallback, consumerTag -> { });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
