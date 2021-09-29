package com.company.part1.lab4.a;

import java.io.*;

public class WriterReaderProgram {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        FileManager fileManager = new FileManager("Files/test.bin");
//        fileManager.appendWriter(new WriterInfo("Kolyan", "Ernest", "+39037371312"));
//        fileManager.appendWriter(new WriterInfo("Miko", "Karter", "+3903434312"));
//        fileManager.appendWriter(new WriterInfo("Tanos", "Woldigot", "+3932322435"));
        WriterInfo writerInfo = fileManager.findBuSurname("K33rter");
        if(writerInfo != null) System.out.println(writerInfo);
        writerInfo = fileManager.findByMobile("Woldigot");
        if(writerInfo != null) System.out.println(writerInfo);
    }
}
