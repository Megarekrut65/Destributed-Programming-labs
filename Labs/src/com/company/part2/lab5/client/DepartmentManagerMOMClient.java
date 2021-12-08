package com.company.part2.lab5.client;

import com.company.part2.lab5.Converter;
import com.company.part2.subjectarea.*;
import com.rabbitmq.client.*;

import java.io.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

public class DepartmentManagerMOMClient implements DepartmentManager, AutoCloseable{
    private Connection connection;
    private Channel channel;
    private final String QUEUE_NAME = "DepartmentDatabase";
    public DepartmentManagerMOMClient(String host){
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
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
    private boolean sendCommand(byte[] command){
        final String corrId = UUID.randomUUID().toString();
        try {
            String replyQueueName = channel.queueDeclare().getQueue();
            AMQP.BasicProperties props = new AMQP.BasicProperties
                    .Builder()
                    .correlationId(corrId)
                    .replyTo(replyQueueName)
                    .build();
            channel.basicPublish("", QUEUE_NAME, props, command);
            final BlockingQueue<Boolean> response = new ArrayBlockingQueue<>(1);
            String ctag = channel.basicConsume(replyQueueName, true, (consumerTag, delivery) -> {
                if (delivery.getProperties().getCorrelationId().equals(corrId)) {
                    String ans = new String(delivery.getBody(), StandardCharsets.UTF_8);
                    response.offer(ans.equals(ServerResults.SUCCESSFUL.code()));
                }
            }, consumerTag -> {
            });

            Boolean result = response.take();
            channel.basicCancel(ctag);
            return result;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public List<Group> getGroups(){
        if(sendCommand(Commands.GET_GROUPS.bytes())){
            final String corrId = UUID.randomUUID().toString();
            try {
                String replyQueueName = channel.queueDeclare().getQueue();
                AMQP.BasicProperties props = new AMQP.BasicProperties
                        .Builder()
                        .correlationId(corrId)
                        .replyTo(replyQueueName)
                        .build();
                final BlockingQueue<List<Group>> response = new ArrayBlockingQueue<>(1);
                String ctag = channel.basicConsume(replyQueueName, true, (consumerTag, delivery) -> {
                    if (delivery.getProperties().getCorrelationId().equals(corrId)) {
                        try {
                            response.offer((List<Group>) Converter.getObject(delivery.getBody()));
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }, consumerTag -> {
                });

                List<Group> result = response.take();
                channel.basicCancel(ctag);
                return result;
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
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
