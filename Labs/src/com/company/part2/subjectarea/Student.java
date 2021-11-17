package com.company.part2.subjectarea;

import java.util.Objects;

public class Student {
    private String id = "student0";
    private int groupId = 0;
    private String name = "Name";
    private String surname = "surname";
    private int age = 18;
    private int gpa = 100;

    public Student() {
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
    public Student(int id,int groupId, String name, String surname, int age, int gpa) {
        this.groupId = groupId;
        this.id = Integer.toString(id);
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.gpa = gpa;
    }
    public Student(String id, String name, String surname, int age, int gpa) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.gpa = gpa;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getGpa() {
        return gpa;
    }

    public void setGpa(int gpa) {
        this.gpa = gpa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return age == student.age && gpa == student.gpa && id.equals(student.id) && name.equals(student.name) && surname.equals(student.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, age, gpa);
    }

    @Override
    public String toString() {
        return id + ") " + name + " " + surname + ", age: " + age + ", gpa: " + gpa + ", group: " + groupId;
    }
}
