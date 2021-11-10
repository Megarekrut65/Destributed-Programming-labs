package com.company.part2.lab1;

import java.util.Random;

public class Generator {
    public static String randString(int size){
        Random random = new Random();
        char[] word = new char[size];
        word[0] = (char)('A' + random.nextInt(26));
        for(int j = 1; j < word.length; j++)
        {
            word[j] = (char)('a' + random.nextInt(26));
        }
        return new String(word);
    }
    public static String randString(){
        return randString((int) (Math.random() * 10 + 1));
    }
    public static Student randStudent(){
        Random random = new Random();
        Student student = new Student();
        student.setId("student"+ randString(5) + random.nextInt(100000));
        student.setName(randString());
        student.setSurname(randString());
        student.setAge(random.nextInt(15) + 10);
        student.setGpa(random.nextInt(100));
        return student;
    }
    public static String randStudyForm(){
        int index = (int)(Math.random() * 2);
        if(index == 2) return "full-time";
        return "external";
    }
    public static Group randGroup(){
        Random random = new Random();
        Group group = new Group();
        group.setStudyForm(randStudyForm());
        group.setName("group-"+ randString(2) + random.nextInt(10000));
        group.setCourse(random.nextInt(5) + 1);
        var students = group.getStudents();
        int size = random.nextInt(15) + 10;
        for(int i = 0; i < size; i++){
            students.add(randStudent());
        }
        return group;
    }
    public static TrainingDepartment randDepartment(){
        Random random = new Random();
        TrainingDepartment trainingDepartment = new TrainingDepartment();
        int size = random.nextInt(5) + 5;
        var groups = trainingDepartment.getGroups();
        for(int i = 0; i < size; i++){
            groups.add(randGroup());
        }
        return trainingDepartment;
    }
}
