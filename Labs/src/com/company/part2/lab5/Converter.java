package com.company.part2.lab5;

import java.io.*;

public class Converter {
    public static byte[] getBytes(Object obj) throws IOException {
        if(obj == null) return null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        oos.flush();
        return bos.toByteArray();
    }
    public static Object getObject(byte[] bytes) throws IOException, ClassNotFoundException {
        if(bytes == null || bytes.length == 0) return null;
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        ObjectInputStream is = new ObjectInputStream(in);
        return is.readObject();
    }
}
