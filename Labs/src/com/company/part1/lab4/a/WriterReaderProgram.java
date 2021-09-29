package com.company.part1.lab4.a;


public class WriterReaderProgram {

    public static void main(String[] args){
        WriterManager writerManager = new WriterManager("Files/test.bin");
//        writerManager.append(new WriterInfo("Kolyan", "Ernest", "+39037371312"));
//        writerManager.append(new WriterInfo("Miko", "Karter", "+3903434312"));
//        writerManager.append(new WriterInfo("Tanos", "Woldigot", "+3932322435"));
        System.out.println(writerManager.readAll());
        WriterInfo writerInfo = writerManager.findBuSurname("Karter");
        if(writerInfo != null) {
            System.out.println(writerInfo);
            writerManager.deleteWriter(writerInfo);
        }
    }
}
