package com.company.part2.lab5.client;

import com.company.part2.lab5.Converter;
import com.company.part2.subjectarea.*;
import com.rabbitmq.client.*;

import java.io.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

public class DepartmentManagerMOMClient implements DepartmentManager, AutoCloseable{
    private Connection connection;
    private Channel channelTo;
    private Channel parameters;
    private Channel channelFrom;
    private final String QUEUE_NAME_TO = "DepartmentTo";
    private final String QUEUE_NAME_FROM = "DepartmentFrom";
    private final String QUEUE_NAME_PARAMETERS = "DepartmentParameters";
    public DepartmentManagerMOMClient(String host){
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
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
    public void close(){
        try {
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<Group> getGroups(){
        try {
            channelTo.basicPublish("", QUEUE_NAME_TO, null,
                    Commands.GET_GROUPS.bytes());
            Object obj = getObject();
            if(obj != null) return (List<Group>) obj;
        } catch (IOException |ClassCastException e) {
            e.printStackTrace();
        }

        return null;
    }
    @Override
    public List<Student> getStudents(){
        try {
            channelTo.basicPublish("", QUEUE_NAME_TO, null,
                    Commands.GET_STUDENTS.bytes());
            Object obj = getObject();
            if(obj != null) return (List<Student>) obj;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public List<Student> getStudentsInGroup(int groupId){
        try {
            channelTo.basicPublish("", QUEUE_NAME_TO, null,
                    Commands.GET_STUDENTS_IN_GROUP.bytes());
            Object obj = getObject(groupId);
            if(obj != null) return (List<Student>) obj;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    @Override
    public Group findGroup(int id) {
        try {
            channelTo.basicPublish("", QUEUE_NAME_TO, null,
                    Commands.FIND_GROUP.bytes());
            Object obj = getObject(id);
            if(obj != null) return (Group) obj;
        } catch (IOException | ClassCastException ignored) {
        }
        return null;
    }
    @Override
    public Student findStudent(int id, int groupId){
        try {
            channelTo.basicPublish("", QUEUE_NAME_TO, null,
                    Commands.FIND_STUDENT.bytes());
            Object obj = getObject(new Integer[]{id, groupId});
            if(obj != null) return (Student) obj;
        } catch (IOException | ClassCastException ignored) {
        }
        return null;

    }
    @Override
    public boolean deleteGroup(int id){
        /*try {
            out.writeObject(Commands.DELETE_GROUP.code());
            String answer = (String) in.readObject();
            if(answer.equals(ServerResults.SUCCESSFUL.code())) {
                out.writeObject(id);
                answer = (String) in.readObject();
                return answer.equals(ServerResults.SUCCESSFUL.code());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }*/

        return false;
    }
    @Override
    public boolean deleteStudent(int id, int groupId){
        /*try {
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
        }*/

        return false;
    }
    @Override
    public boolean addGroup(Group group){
        /*try {
            out.writeObject(Commands.ADD_GROUP.code());
            String answer = (String) in.readObject();
            if(answer.equals(ServerResults.SUCCESSFUL.code())) {
                out.writeObject(group);
                answer = (String) in.readObject();
                return answer.equals(ServerResults.SUCCESSFUL.code());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }*/

        return false;
    }
    @Override
    public boolean addStudent(Student student){
        /*try {
            out.writeObject(Commands.ADD_STUDENT.code());
            String answer = (String) in.readObject();
            if(answer.equals(ServerResults.SUCCESSFUL.code())) {
                out.writeObject(student);
                answer = (String) in.readObject();
                return answer.equals(ServerResults.SUCCESSFUL.code());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }*/

        return false;
    }
    private Object getObject(Object param){
        final BlockingQueue<Object> objects = new ArrayBlockingQueue<>(1);
        final AtomicReference<String> answer = new AtomicReference<>(), answer2 = new AtomicReference<>();
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            if(answer.get() == null){
                answer.set(new String(delivery.getBody(), StandardCharsets.UTF_8));
                if(!answer.get().equals(ServerResults.SUCCESSFUL.code())){
                    objects.offer(new Object());
                }else{
                    parameters.basicPublish("", QUEUE_NAME_PARAMETERS,
                            null, Converter.getBytes(param));
                }
            }else {
                receiveObject(objects, answer2, delivery);
            }
        };
        return getObject(objects, deliverCallback);
    }

    private Object getObject(BlockingQueue<Object> objects, DeliverCallback deliverCallback) {
        try {
            String tag = channelFrom.basicConsume(QUEUE_NAME_FROM, true, deliverCallback, consumerTag -> { });
            var obj = objects.take();
            channelFrom.basicCancel(tag);
            return obj;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Object getObject(){
        final BlockingQueue<Object> students = new ArrayBlockingQueue<>(1);
        final AtomicReference<String> answer = new AtomicReference<>();
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            receiveObject(students, answer, delivery);
        };
        return getObject(students, deliverCallback);
    }
    private void receiveObject(BlockingQueue<Object> objects, AtomicReference<String> answer, Delivery delivery) throws IOException {
        if(answer.get() == null){
            answer.set(new String(delivery.getBody(), StandardCharsets.UTF_8));
            if(!answer.get().equals(ServerResults.SUCCESSFUL.code())){
                objects.offer(new Object());
            }
        }else {
            try {
                objects.offer(Converter.getObject(delivery.getBody()));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
