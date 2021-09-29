package com.company.part1.lab4.a;

import java.io.*;
import java.util.ArrayList;

public class AuthorManager {
    private final String path;

    public AuthorManager(String path) {
        this.path = path;
    }

    public AuthorInfo findBySurname(String surname) {
        AuthorInfo res = null;
        try(FileInputStream fileInputStream = new FileInputStream(path)) {
            while (true)
            {
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                AuthorInfo authorInfo = (AuthorInfo) objectInputStream.readObject();
                if(authorInfo != null &&
                        authorInfo.getSurname().equals(surname)){
                    res = authorInfo;
                    objectInputStream.close();
                    break;
                }
            }
        }catch (IOException | ClassNotFoundException e){
            System.err.println("Object("+surname+") is not found in file!");
        }
        return res;
    }
    public AuthorInfo findByMobile(String mobile) {
        AuthorInfo res = null;
        try(FileInputStream fileInputStream = new FileInputStream(path)) {
            while (true)
            {
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                AuthorInfo authorInfo = (AuthorInfo) objectInputStream.readObject();
                if(authorInfo != null &&
                        authorInfo.getMobile().equals(mobile)){
                    res = authorInfo;
                    objectInputStream.close();
                    break;
                }
            }
        }catch (IOException | ClassNotFoundException e){
            System.err.println("Object("+mobile+") is not found in file!");
        }
        return res;
    }
    public void append(ArrayList<AuthorInfo> authorsInfo){
        for(var author:authorsInfo){
            append(author);
        }
    }
    public void append(AuthorInfo authorInfo){
        try (ObjectOutputStream outputStream = new ObjectOutputStream(
                new FileOutputStream(path, true))) {
            outputStream.writeObject(authorInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void clearData(){
        try {
            new FileOutputStream(path).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<AuthorInfo> readAll(){
        ArrayList<AuthorInfo> infoList = new ArrayList<>();
        try(FileInputStream fileInputStream = new FileInputStream(path)) {
            while (true)
            {
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                AuthorInfo authorInfo = (AuthorInfo) objectInputStream.readObject();
                if(authorInfo != null){
                    infoList.add(authorInfo);
                }else{
                    break;
                }
            }
        }catch (IOException | ClassNotFoundException ignored){

        }
        return infoList;
    }
    public void deleteAuthor(AuthorInfo author){
        ArrayList<AuthorInfo> infoList = new ArrayList<>();
        try(FileInputStream fileInputStream = new FileInputStream(path)) {
            while (true)
            {
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                AuthorInfo authorInfo = (AuthorInfo) objectInputStream.readObject();
                if(authorInfo != null && !authorInfo.equals(author)){
                    infoList.add(authorInfo);
                }else if(authorInfo == null){
                    break;
                }
            }
        }catch (IOException | ClassNotFoundException ignored){
        }
        clearData();
        for(var info: infoList){
            append(info);
        }
    }
}
