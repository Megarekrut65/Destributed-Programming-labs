package com.company.part1.lab4.a;

import java.util.ArrayList;

public class AuthorsGenerator {
    private final ArrayList<AuthorInfo> authors;

    public AuthorsGenerator(int size) {
        this.authors = generate(size);
    }
    private static ArrayList<AuthorInfo> generate(int size){
        ArrayList<AuthorInfo> res = new ArrayList<>();
        for(int i = 0; i < size; i++){
            res.add(AuthorInfo.rand());
        }
        return res;
    }

    public ArrayList<AuthorInfo> getAuthors() {
        return authors;
    }
    public ArrayList<String> getAuthorsSurnames(int size){
        ArrayList<String> res = new ArrayList<>();
        for (int i = 0; i < size; i++){
            int index = (int)(Math.random()*size);
            res.add(authors.get(index).getSurname());
        }
        return res;
    }
    public ArrayList<String> getAuthorsMobiles(int size){
        ArrayList<String> res = new ArrayList<>();
        for (int i = 0; i < size; i++){
            int index = (int)(Math.random()*size);
            res.add(authors.get(index).getMobile());
        }
        return res;
    }
    public ArrayList<AuthorInfo> getSomeAuthors(int size){
        ArrayList<AuthorInfo> res = new ArrayList<>();
        for (int i = 0; i < size; i++){
            int index = (int)(Math.random()*size);
            res.add(authors.get(index));
        }
        return res;
    }
    public ArrayList<AuthorInfo> getToAdd(int size){
        return generate(size);
    }
}
