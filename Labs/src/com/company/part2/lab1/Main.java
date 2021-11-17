package com.company.part2.lab1;

import com.company.part2.lab1.parser.DOMDepartmentParser;
import com.company.part2.subjectarea.Generator;
import com.company.part2.subjectarea.TrainingDepartment;

/**
 * Variant 4
 */
public class Main {
    private static final String file = "src/com/company/part2/lab1/data/training_department.xml";
    private static final String schema="src/com/company/part2/lab1/data/training_department.xsd";
    private static DOMDepartmentParser parser;
    private static TrainingDepartment department = new TrainingDepartment();
    public static void main(String[] args) {
        parser = new DOMDepartmentParser(schema);
        readDepartment();
        addGroups();
        writeDepartment();
    }
    private static void readDepartment(){
        department = parser.readDepartmentFromXmlFile(file);
        if(department != null){
            System.out.println("Department was read successfully!");
            System.out.println(department);
        } else {
            System.err.println("Department was read unsuccessfully!");
            System.err.println(parser.getErrors());
        }
    }
    private static void addGroups(){
        if(department != null){
            TrainingDepartment trainingDepartment = Generator.randDepartment();
            for(var group: trainingDepartment.getGroups()){
                department.add(group);
            }
        }
    }
    private static void writeDepartment(){
        if(department != null){
            if(parser.writeDepartmentToXmlFile(department, file)){
                System.out.println("Department was written successfully!");
            } else {
                System.err.println("Department was written unsuccessfully!");
                System.err.println(parser.getErrors());
            }
        }
    }
}
