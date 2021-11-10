package com.company.part2.lab1;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    public boolean remove(String name){
        return groups.removeIf(group -> Objects.equals(group.getName(), name));
    }
    public Group find(String name){
        for(var group:groups){
            if(group.getName().equals(name)) return group;
        }
        return null;
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
