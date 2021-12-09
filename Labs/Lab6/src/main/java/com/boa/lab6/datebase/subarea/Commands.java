package com.boa.lab6.datebase.subarea;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public enum Commands {
    GET_GROUPS("get-groups"),
    GET_STUDENTS("get-students"),
    GET_STUDENTS_IN_GROUP("get-students-group"),
    ADD_GROUP("add-group"),
    ADD_STUDENT("add-student"),
    DELETE_GROUP("delete-group"),
    DELETE_STUDENT("delete-student"),
    FIND_GROUP("find-group"),
    FIND_STUDENT("find-student");
    private final String code;
    Commands(String code){
        this.code = code;
    }
    public String code(){
        return code;
    }
    public byte[] bytes(){
        return code.getBytes(StandardCharsets.UTF_8);
    }
}
