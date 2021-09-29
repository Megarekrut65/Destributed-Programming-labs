package com.company.part1.lab4.a;


import java.util.ArrayList;

public class WriterReaderProgram {

    public static void main(String[] args){
        AuthorManager authorManager = new AuthorManager("Files/authors.bin");
        AuthorsGenerator generator = new AuthorsGenerator(500);
        authorManager.clearData();
        authorManager.append(generator.getAuthors());
        ArrayList<String> toFindMobile = generator.getAuthorsSurnames(100);
        ArrayList<String> toFindName = generator.getAuthorsMobiles(100);
        ArrayList<AuthorInfo> toAdd = generator.getToAdd(200);
        ArrayList<AuthorInfo> toRemove = generator.getSomeAuthors(150);
        WriteReadLocker locker = new WriteReadLocker();
        AdderRemover adderRemover = new AdderRemover(authorManager,locker,toRemove, toAdd);
        MobileFinder mobileFinder = new MobileFinder(authorManager,locker,toFindMobile);
        SurnameNameFinder surnameNameFinder = new SurnameNameFinder(authorManager,locker,toFindName);
    }
}
