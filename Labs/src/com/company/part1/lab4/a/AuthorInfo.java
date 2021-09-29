package com.company.part1.lab4.a;
import java.io.Serializable;
import java.util.Random;

public class AuthorInfo implements Serializable {
    private final String name;
    private final String surname;
    private final String mobile;

    public AuthorInfo(String name, String surname, String mobile) {
        this.name = name;
        this.surname = surname;
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getMobile() {
        return mobile;
    }

    @Override
    public String toString() {
        return "AuthorInfo{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", mobile='" + mobile + '\'' +
                '}';
    }
    private static String randString(){
        Random random = new Random();
        char[] word = new char[random.nextInt(8)+3];
        char[] first = new char[1];
        first[0] = (char)('A' + random.nextInt(26));
        for(int j = 0; j < word.length; j++)
        {
            word[j] = (char)('a' + random.nextInt(26));
        }
        return new String(first) + new String(word);
    }
    private static String randMobile(){
        StringBuilder first = new StringBuilder("+380");
        for(int j = 0; j < 9; j++)
        {
           first.append((int) (Math.random() * 9));
        }
        return first.toString();
    }
    public static AuthorInfo rand(){
        return new AuthorInfo(randString(),randString(),randMobile());
    }
    @Override
    public boolean equals(Object obj) {
        AuthorInfo other = (AuthorInfo) obj;
        return name.equals(other.name) && surname.equals(other.surname) && mobile.equals(other.mobile);
    }
}
