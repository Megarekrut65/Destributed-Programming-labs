package com.company.part1.lab5.a;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class RecruitLine{
    private List<Command> container;

    public RecruitLine() {
        this.container = new LinkedList<>();
    }
    void generateList(int size){
        for(int i = 0; i < size; i++){
            container.add(Commander.getCommand());
        }
    }
    private boolean isIndex(int i ){
        return (i >= 0 && i < container.size());
    }
    private void swap(int i, int j){
        Command temp = container.get(i);
        container.set(i, container.get(j));
        container.set(j,temp);
    }
    public boolean checkNear(int i){
        if(isIndex(i)){
            Command command1 = container.get(i);
            int j = i + Commander.getSide(command1);
            if(isIndex(j)){
                Command command2 = container.get(j);
                if(command1 != command2){
                    swap(i,j);
                    return false;
                }
            }
        }
        return true;
    }
    public boolean check(){
        int bad = 0;
        for(int i = 1; i < container.size(); i++){
            if(container.get(i - 1) != container.get(i)) bad++;
        }
        return bad < 2;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (Command command : container) {
            if (command == Command.LEFT) builder.append("<");
            else builder.append(">");
        }
        builder.append("]");
        return builder.toString();
//        return "RecruitLine{" + container +
//                '}';
    }
}
