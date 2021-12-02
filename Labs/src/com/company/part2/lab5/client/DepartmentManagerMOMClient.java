package com.company.part2.lab5.client;

import com.company.part2.subjectarea.Commands;
import com.company.part2.subjectarea.DepartmentManager;
import com.company.part2.subjectarea.Group;
import com.company.part2.subjectarea.Student;
import com.rabbitmq.client.*;

import java.io.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

public class DepartmentManagerMOMClient implements DepartmentManager {
    private Connection connection;
    private Channel channel;
    private final String QUEUE_NAME = "DepartmentDatabase";
    public DepartmentManagerMOMClient(String host){
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
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
    private byte[] getBytes(Object obj) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        oos.flush();
        return bos.toByteArray();
    }
    private Object getObject(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        ObjectInputStream is = new ObjectInputStream(in);
        return is.readObject();
    }
    @Override
    public List<Group> getGroups(){
        final String corrId = UUID.randomUUID().toString();
        try {
            String replyQueueName = channel.queueDeclare().getQueue();
            var props = getProperties(corrId, replyQueueName);
            channel.basicPublish("", QUEUE_NAME, props,
                    Commands.GET_GROUPS.bytes());
            final BlockingQueue<List<Group>> response = new ArrayBlockingQueue<>(1);
            String ctag = channel.basicConsume(replyQueueName, true, (consumerTag, delivery) -> {
                if (delivery.getProperties().getCorrelationId().equals(corrId)) {
                    var body = delivery.getBody();
                    try {
                        response.offer((List<Group>)getObject(body));
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
