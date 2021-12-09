package com.boa.lab6.datebase.subarea;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Group implements Serializable {
    private int id = 0;
    private String name = "";
    private int course = 0;
    private String studyForm = "";

    public Group() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Group(int id, String name, int course, String studyForm) {
        this.id = id;
        this.name = name;
        this.course = course;
        this.studyForm = studyForm;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public String getStudyForm() {
        return studyForm;
    }

    public void setStudyForm(String studyForm) {
        this.studyForm = studyForm;
    }

    @Override
    public String toString() {
        return id + ") " + name + ", course: " + course + ", study form: " + studyForm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return course == group.course && name.equals(group.name) && studyForm.equals(group.studyForm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, course, studyForm);
    }
}
