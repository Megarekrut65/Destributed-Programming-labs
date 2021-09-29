package com.company.part1.lab4.a;

import java.io.Serializable;

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

    @Override
    public boolean equals(Object obj) {
        AuthorInfo other = (AuthorInfo) obj;
        return name.equals(other.name) && surname.equals(other.surname) && mobile.equals(other.mobile);
    }
}
