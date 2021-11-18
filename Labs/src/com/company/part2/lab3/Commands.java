package com.company.part2.lab3;

public enum Commands {
    GET_GROUPS("get-groups"),
    GET_GROUP("get-group"),
    GET_STUDENTS("get-students"),
    GET_STUDENT("get-student"),
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

}
