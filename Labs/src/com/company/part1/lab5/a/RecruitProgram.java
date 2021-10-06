package com.company.part1.lab5.a;

public class RecruitProgram {
    public static void main(String[] args) {
        Recruit recruit = new Recruit(200);
        Command command = Commander.getCommand();
        recruit.executeCommand(command, 50);
    }
}
