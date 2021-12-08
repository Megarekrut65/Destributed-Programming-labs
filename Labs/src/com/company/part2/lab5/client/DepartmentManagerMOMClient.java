package com.company.part2.lab5.client;

import com.company.part2.lab5.Converter;
import com.company.part2.subjectarea.*;
import com.rabbitmq.client.*;

import java.io.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

public class DepartmentManagerMOMClient implements DepartmentManager, AutoCloseable{
    private Connection connection;
    private Channel channelTo;
    private Channel channelFrom;
    private final String QUEUE_NAME_TO = "DepartmentDatabaseTo";
    private final String QUEUE_NAME_FROM = "DepartmentDatabaseFrom";
    public DepartmentManagerMOMClient(String host){
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        try {
            connection = factory.newConnection();
            channelTo = connection.createChannel();
            channelTo.queueDeclare(QUEUE_NAME_TO, false, false, false, null);
            channelFrom = connection.createChannel();
            channelFrom.queueDeclare(QUEUE_NAME_FROM, false, false, false, null);
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }
    private AMQP.BasicProperties getProperties(String id, String replyQueueName) throws IOException {
        return new AMQP.BasicProperties
                .Builder()
                .correlationId(id)
                .replyTo(replyQueueName)
                .build();
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
            final BlockingQueue<List<Group>> groups = new ArrayBlockingQueue<>(1);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
               String answer = new String(delivery.getBody());
               System.out.println(answer);
               if(answer.equals(ServerResults.SUCCESSFUL.code())){
                   DeliverCallback deliverCallback1 = (s, delivery1) -> {
                       try {
                           groups.offer((List<Group>) Converter.getObject(delivery1.getBody()));
                       } catch (ClassNotFoundException e) {
                           e.printStackTrace();
                       }
                   };
                   channelFrom.basicConsume(QUEUE_NAME_FROM, true, deliverCallback1, consumerTag1 -> { });
               }else groups.offer(new ArrayList<>());
            };
            channelFrom.basicConsume(QUEUE_NAME_FROM, true, deliverCallback, consumerTag -> { });
            return groups.take();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }
    @Override
    public List<Student> getStudents(){
       /* try {
            out.writeObject(Commands.GET_STUDENTS.code());
            String answer = (String) in.readObject();
            if(answer.equals(ServerResults.SUCCESSFUL.code())) {
                return (List<Student>) in.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }*/
        return null;
    }
    @Override
    public List<Student> getStudentsInGroup(int groupId){
        /*try {
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
        }*/

        return null;
    }
    @Override
    public Group findGroup(int id) {
        /*try {
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
*/
        return null;
    }
    @Override
    public Student findStudent(int id, int groupId){
        /*try {
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
        }*/

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
}
