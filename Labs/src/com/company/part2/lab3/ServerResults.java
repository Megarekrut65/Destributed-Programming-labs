package com.company.part2.lab3;

public enum ServerResults {
    SUCCESSFUL("0"),
    UNKNOWN_COMMAND("1"),
    NOT_FOUND("2"),
    PARAMETERS_ERROR("3");
    private final String code;
    ServerResults(String code){
        this.code = code;
    }
    public String code(){
        return code;
    }
}
