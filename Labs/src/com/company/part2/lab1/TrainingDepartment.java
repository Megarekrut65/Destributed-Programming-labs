package com.company.part2.lab1;

import java.util.ArrayList;
import java.util.List;

public class TrainingDepartment {
    private List<Group> groups = new ArrayList<>();

    public TrainingDepartment() {
    }

    public TrainingDepartment(List<Group> groups) {
        this.groups = groups;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
    public boolean add(Group group){
        return groups.add(group);
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("TrainingDepartment{groups=").append('\n');
        for(var group:groups){
            builder.append(group).append('\n');
        }
        builder.append('}');
        return builder.toString();
    }
}
