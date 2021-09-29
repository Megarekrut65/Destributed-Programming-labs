package com.company.part1.lab4.a;

import java.io.*;
import java.util.Objects;

public class FileManager {
    private final String path;

    public FileManager(String path) {
        this.path = path;
    }

    public WriterInfo findMobile(String surname){
        WriterInfo res = null;
        try(ObjectInputStream inputStream = new ObjectInputStream(
                new FileInputStream(path))) {
            try {
                WriterInfo writerInfo;
                while ((writerInfo = (WriterInfo)inputStream.readObject()) != null){
                    System.out.println(writerInfo);
                    if(Objects.equals(writerInfo.getSurname(), surname)){
                        res = writerInfo;
                        break;
                    }
                }
            }catch (StreamCorruptedException e){
                e.printStackTrace();
                System.out.println("IDN");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
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
