package com.company.part2.subjectarea;

import java.util.HashMap;
import java.util.Map;

public abstract class DepartmentServer {
    protected abstract boolean addGroup();
    protected abstract boolean addStudent();
    protected abstract boolean deleteStudent();
    protected abstract boolean deleteGroup();
    protected abstract boolean findStudent();
    protected abstract boolean findGroup();
    protected abstract boolean getStudents();
    protected abstract boolean getStudentsInGroup();
    protected abstract boolean getGroups();
    protected final Map<String, Function> functionMap;
    public DepartmentServer(){
        functionMap = getMap();
    }
    public Map<String, Function> getMap(){
        var map = new HashMap<String, Function>();
        map.put(Commands.ADD_GROUP.code(), this::addGroup);
        map.put(Commands.ADD_STUDENT.code(), this::addStudent);
        map.put(Commands.DELETE_GROUP.code(), this::deleteGroup);
        map.put(Commands.DELETE_STUDENT.code(), this::deleteStudent);
        map.put(Commands.FIND_GROUP.code(), this::findGroup);
        map.put(Commands.FIND_STUDENT.code(), this::findStudent);
        map.put(Commands.GET_GROUPS.code(), this::getGroups);
        map.put(Commands.GET_STUDENTS.code(), this::getStudents);
        map.put(Commands.GET_STUDENTS_IN_GROUP.code(), this::getStudentsInGroup);
        return map;
    }
    protected void log(String message){
        System.out.print(message);
    }
    protected void logln(String message){
        System.out.println(message);
    }
    public interface Function{
        boolean call();
    }
}
