package com.company.part1.lab4.a;


public class WriterReaderProgram {

    public static void main(String[] args){
        AuthorManager authorManager = new AuthorManager("Files/test.bin");
//        authorManager.append(new AuthorInfo("Kolyan", "Ernest", "+39037371312"));
//        authorManager.append(new AuthorInfo("Miko", "Karter", "+3903434312"));
//        authorManager.append(new AuthorInfo("Tanos", "Woldigot", "+3932322435"));
        System.out.println(authorManager.readAll());
        AuthorInfo authorInfo = authorManager.findBuSurname("Karter");
        if(authorInfo != null) {
            System.out.println(authorInfo);
            authorManager.deleteAuthor(authorInfo);
        }
    }
}
