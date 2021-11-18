package com.company.part2.lab3.client;

import com.company.part2.lab3.Commands;
import com.company.part2.lab3.ServerResults;
import com.company.part2.subjectarea.Student;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class DepartmentClient {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    public DepartmentClient(String ip, int port){
        try {
            socket = new Socket(ip, port);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DepartmentClient client = new DepartmentClient("0.0.0.0", 8085);
        client.run();
    }
    public List<Student> getStudents() throws IOException, ClassNotFoundException {
        out.writeObject(Commands.GET_STUDENTS.code());
        String answer = (String) in.readObject();
        if(answer.equals(ServerResults.SUCCESSFUL.code())) {
            return (List<Student>) in.readObject();
        }
        return null;
    }
    public void run(){
        try {
            System.out.println(getStudents());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
