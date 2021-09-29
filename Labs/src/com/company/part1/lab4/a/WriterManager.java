package com.company.part1.lab4.a;

import java.io.*;
import java.util.ArrayList;

public class WriterManager {
    private final String path;

    public WriterManager(String path) {
        this.path = path;
    }

    public WriterInfo findBuSurname(String surname) {
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
        }catch (IOException | ClassNotFoundException e){
            System.err.println("Object("+surname+") is not found in file!");
        }
        return res;
    }
    public WriterInfo findByMobile(String mobile) {
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
        }catch (IOException | ClassNotFoundException e){
            System.err.println("Object("+mobile+") is not found in file!");
        }
        return res;
    }

    public void append(WriterInfo writerInfo){
        try (ObjectOutputStream outputStream = new ObjectOutputStream(
                new FileOutputStream(path, true))) {
            outputStream.writeObject(writerInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void clearData(){
        try {
            new FileOutputStream(path).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<WriterInfo> readAll(){
        ArrayList<WriterInfo> infoList = new ArrayList<>();
        try(FileInputStream fileInputStream = new FileInputStream(path)) {
            while (true)
            {
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                WriterInfo writerInfo = (WriterInfo) objectInputStream.readObject();
                if(writerInfo != null){
                    infoList.add(writerInfo);
                }else{
                    break;
                }
            }
        }catch (IOException | ClassNotFoundException ignored){

        }
        return infoList;
    }
    public void deleteWriter(WriterInfo writer){
        ArrayList<WriterInfo> infoList = new ArrayList<>();
        try(FileInputStream fileInputStream = new FileInputStream(path)) {
            while (true)
            {
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                WriterInfo writerInfo = (WriterInfo) objectInputStream.readObject();
                if(writerInfo != null && !writerInfo.equals(writer)){
                    infoList.add(writerInfo);
                }else if(writerInfo == null){
                    break;
                }
            }
        }catch (IOException | ClassNotFoundException ignored){
        }
        clearData();
        System.out.println(infoList);
        for(var info: infoList){
            append(info);
        }
    }
}
