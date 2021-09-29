package com.company.part1.lab4.a;

import java.io.Serializable;

public class WriterInfo implements Serializable {
    private final String name;
    private final String surname;
    private final String mobile;

    public WriterInfo(String name, String surname, String mobile) {
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
        return "WriterInfo{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", mobile='" + mobile + '\'' +
                '}';
    }
}
