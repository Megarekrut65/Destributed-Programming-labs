package com.company.part2.lab1;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Group {
    private String name = "group0";
    private int course = 1;
    private String studyForm = "full-time";
    private List<Student> students = new ArrayList<>();

    public Group() {
    }

    public Group(String name, int course, String studyForm, List<Student> students) {
        this.name = name;
        this.course = course;
        this.studyForm = studyForm;
        this.students = students;
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

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "Group{" +
                "name='" + name + '\'' +
                ", course=" + course +
                ", studyForm='" + studyForm + '\'' +
                ", students=" + students +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return course == group.course && name.equals(group.name) && studyForm.equals(group.studyForm) && students.equals(group.students);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, course, studyForm, students);
    }
}
