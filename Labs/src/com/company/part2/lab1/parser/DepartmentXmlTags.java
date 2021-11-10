package com.company.part2.lab1.parser;

public enum DepartmentXmlTags {
    TRAINING_DEPARTMENT("training_department"),
    GROUP("group"),
    ID("id"),
    STUDENTS("students"),
    STUDENT("student"),
    NAME("name"),
    SURNAME("surname"),
    AGE("age"),
    GPA("gpa"),
    COURSE("course"),
    STUDY_FORM("study_form");
    private final String value;
    DepartmentXmlTags(String value){
        this.value = value;
    }

    public String val() {
        return value;
    }
}
