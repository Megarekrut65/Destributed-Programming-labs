package com.company.part1.lab5.a;

public class Commander {
    public static Command getCommand(){
        int number = (int)(Math.random() * 2);
        if(number == 0) return Command.LEFT;
        return Command.RIGHT;
    }
    public static int getSide(Command command){
        if(command == Command.LEFT) return -1;
        return 1;
    }
}
