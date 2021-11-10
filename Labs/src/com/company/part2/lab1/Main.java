package com.company.part2.lab1;

import com.company.part2.lab1.parser.DOMDepartmentParser;

/**
 * Variant 4
 */
public class Main {
    public static void main(String[] args) {
        DOMDepartmentParser parser = new DOMDepartmentParser("Labs/src/com/company/part2/lab1/data/training_department.xsd");
        var dep = parser.readGemFromXmlFile("Labs/src/com/company/part2/lab1/data/training_department.xml");
        System.out.println(dep);
    }
}
