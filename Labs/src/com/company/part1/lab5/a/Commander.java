package com.company.part1.lab5.a;

public class Commander {
    public static Command getCommand(){
        int number = (int)(Math.random() * 2);
        System.out.println(number);
        if(number == 0) return Command.LEFT;
        return Command.RIGHT;
    }
}
