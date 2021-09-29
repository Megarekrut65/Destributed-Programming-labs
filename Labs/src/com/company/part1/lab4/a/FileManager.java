package com.company.part1.lab4.a;

import java.io.*;
import java.util.Objects;

public class FileManager {
    private final String path;

    public FileManager(String path) {
        this.path = path;
    }

    public WriterInfo findBuSurname(String surname) throws ClassNotFoundException {
        WriterInfo res = null;
        try(FileInputStream fileInputStream = new FileInputStream(path)) {
            while (true)
            {
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                WriterInfo writerInfo = (WriterInfo) objectInputStream.readObject();
                if(writerInfo != null &&
                        writerInfo.getSurname().equals(surname)){
                    res = writerInfo;
                    objectInputStream.close();
                    break;
                }
            }
        }catch (IOException e){
            e.printStackTrace();
            System.err.println("Object("+surname+") is not found in file!");
        }
        return res;
    }
    public WriterInfo findByMobile(String mobile) throws ClassNotFoundException {
        WriterInfo res = null;
        try(FileInputStream fileInputStream = new FileInputStream(path)) {
            while (true)
            {
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                WriterInfo writerInfo = (WriterInfo) objectInputStream.readObject();
                if(writerInfo != null &&
                        writerInfo.getMobile().equals(mobile)){
                    res = writerInfo;
                    objectInputStream.close();
                    break;
                }
            }
        }catch (IOException e){
            e.printStackTrace();
            System.err.println("Object("+mobile+") is not found in file!");
        }
        return res;
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
