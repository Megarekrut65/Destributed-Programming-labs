//package com.company.part2.lab5.server;
//
//import com.company.part2.lab2.DepartmentSqlManager;
//import com.company.part2.subjectarea.*;
//import com.rabbitmq.client.Channel;
//import com.rabbitmq.client.Connection;
//import com.rabbitmq.client.ConnectionFactory;
//
//import java.io.IOException;
//import java.util.ArrayList;
//
//public class DepartmentMOMServer extends DepartmentServer {
//    private final DepartmentSqlManager database;
//    private final String QUEUE_NAME = "DepartmentDatabase";
//    public DepartmentMOMServer() {
//        super();
//        database = new DepartmentSqlManager("localhost", 3306, "department");
//    }
//
//    private boolean work() throws IOException, ClassNotFoundException {
//       /* String code = (String)in.readObject();
//        System.out.println("Command: "+code);
//        var fun = functionMap.get(code);
//        if(fun != null){
//            out.writeObject(ServerResults.SUCCESSFUL.code());
//            return fun.call();
//        }
//        out.writeObject(ServerResults.UNKNOWN_COMMAND.code());*/
//        return false;
//    }
//    public boolean getGroups(){
//        try {
//            var groups = database.getGroups();
//            if(groups == null) groups = new ArrayList<>();
//            System.out.println("Found " + groups.size() + " groups");
//            out.writeObject(groups);
//            return true;
//        }catch (IOException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//    public boolean addGroup(){
//        /*try {
//            Group group = (Group) in.readObject();
//            System.out.print(group);
//            if(database.addGroup(group)){
//                out.writeObject(ServerResults.SUCCESSFUL.code());
//                System.out.println(" was added");
//            }
//            else{
//                out.writeObject(ServerResults.PARAMETERS_ERROR.code());
//                System.out.println(" wasn't added");
//            }
//            return true;
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }*/
//        return false;
//    }
//    public boolean addStudent(){
//       /* try {
//            Student student = (Student) in.readObject();
//            System.out.print(student);
//            if(database.addStudent(student)){
//                out.writeObject(ServerResults.SUCCESSFUL.code());
//                System.out.println(" was added");
//            }
//            else{
//                out.writeObject(ServerResults.PARAMETERS_ERROR.code());
//                System.out.println(" wasn't added");
//            }
//            return true;
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }*/
//        return false;
//    }
//    public boolean deleteStudent(){
//       /*try {
//            int id = (int)in.readObject();
//            int groupId = (int)in.readObject();
//            System.out.print("Id: " + id + ", group id: " + groupId);
//            if(database.deleteStudent(id,groupId)) {
//                out.writeObject(ServerResults.SUCCESSFUL.code());
//                System.out.println(", student was removed");
//            }
//            else{
//                out.writeObject(ServerResults.NOT_FOUND.code());
//                System.out.println(", not found");
//            }
//            return true;
//        }catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }*/
//        return false;
//    }
//    public boolean deleteGroup(){
//        /*try {
//            int id = (int)in.readObject();
//            System.out.print("Id: " + id);
//            if(database.deleteGroup(id)) {
//                out.writeObject(ServerResults.SUCCESSFUL.code());
//                System.out.println(", group was removed");
//            }
//            else{
//                out.writeObject(ServerResults.NOT_FOUND.code());
//                System.out.println(", not found");
//            }
//            return true;
//        }catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }*/
//        return false;
//    }
//    public boolean findStudent(){
//        /*try {
//            int id = (int)in.readObject();
//            int groupId = (int)in.readObject();
//            var student = database.findStudent(id,groupId);
//            System.out.print("Id: " + id + ", group id: " + groupId);
//            if(student != null) {
//                out.writeObject(ServerResults.SUCCESSFUL.code());
//                System.out.println(", found student: " + student.getName());
//                out.writeObject(student);
//            }
//            else{
//                out.writeObject(ServerResults.NOT_FOUND.code());
//                System.out.println(", not found");
//            }
//            return true;
//        }catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }*/
//        return false;
//    }
//    public boolean findGroup(){
//        /*try {
//            int id = (int)in.readObject();
//            var group = database.findGroup(id);
//            System.out.print("Id: " + id);
//            if(group != null) {
//                out.writeObject(ServerResults.SUCCESSFUL.code());
//                System.out.println(", found group: " + group.getName());
//                out.writeObject(group);
//            }
//            else{
//                out.writeObject(ServerResults.NOT_FOUND.code());
//                System.out.println(", not found");
//            }
//            return true;
//        }catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }*/
//        return false;
//    }
//    public boolean getStudents() {
//        /*try {
//            var students = database.getStudents();
//            if(students == null) students = new ArrayList<>();
//            System.out.println("Found " + students.size() + " students");
//            out.writeObject(students);
//            return true;
//        }catch (IOException e) {
//            e.printStackTrace();
//        }*/
//        return false;
//    }
//    public boolean getStudentsInGroup(){
//        /*try {
//            int groupId = (int)in.readObject();
//            var students = database.getStudentsInGroup(groupId);
//            System.out.print("Group id: " + groupId);
//            if(students != null){
//                out.writeObject(ServerResults.SUCCESSFUL.code());
//                System.out.println(", found " + students.size() + " students");
//                out.writeObject(students);
//            }
//            else {
//                out.writeObject(ServerResults.NOT_FOUND.code());
//                System.out.println(", not found");
//            }
//
//            return true;
//        }catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }*/
//        return false;
//    }
//
//
//    public void run() {
//        ConnectionFactory factory = new ConnectionFactory();
//        factory.setHost("localhost");
//
//        try (Connection connection = factory.newConnection();
//                Channel channel = connection.createChannel()) {
//            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
//            channel.queuePurge(QUEUE_NAME);
//            channel.basicQos(1);
//            Object monitor = new Object();
//            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
//                AMQP.BasicProperties replyProps = new AMQP.BasicProperties
//                        .Builder()
//                        .correlationId(delivery.getProperties().getCorrelationId())
//                        .build();
//
//                String response = "";
//
//                try {
//                    String message = new String(delivery.getBody(), "UTF-8");
//                    int n = Integer.parseInt(message);
//
//                    System.out.println(" [.] fib(" + message + ")");
//                    response += fib(n);
//                } catch (RuntimeException e) {
//                    System.out.println(" [.] " + e.toString());
//                } finally {
//                    channel.basicPublish("", delivery.getProperties().getReplyTo(), replyProps, response.getBytes("UTF-8"));
//                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
//                    // RabbitMq consumer worker thread notifies the RPC server owner thread
//                    synchronized (monitor) {
//                        monitor.notify();
//                    }
//                }
//            };
//
//            channel.basicConsume(RPC_QUEUE_NAME, false, deliverCallback, (consumerTag -> { }));
//            // Wait and be prepared to consume the message from RPC client.
//            while (true) {
//                synchronized (monitor) {
//                    try {
//                        monitor.wait();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//    }
//}
