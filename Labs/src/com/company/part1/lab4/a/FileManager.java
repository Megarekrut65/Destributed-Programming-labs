package com.company.part1.lab4.a;

import java.io.*;
import java.util.Objects;

public class FileManager {
    private final String path;

    public FileManager(String path) {
        this.path = path;
    }

    public WriterInfo findMobile(String surname) throws IOException, ClassNotFoundException {
        WriterInfo res = null;
        FileInputStream fileInputStream = new FileInputStream(path);
        while (true)
        {
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            WriterInfo writerInfo = (WriterInfo) objectInputStream.readObject();
            if(writerInfo != null && writerInfo.getSurname().equals(surname)){
                res = writerInfo;
                objectInputStream.close();
                break;
            }
        }
        fileInputStream.close();
        return res;
    }
    public WriterInfo findPerson(String mobile){
        return new WriterInfo("","","");
    }

    public void appendWriter(WriterInfo writerInfo){
        try (ObjectOutputStream outputStream = new ObjectOutputStream(
                new FileOutputStream(path, true))) {
            outputStream.writeObject(writerInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void deleteWriter(WriterInfo writerInfo){

    }
}
