package com.company.part2.lab1;

import com.company.part2.lab1.parser.DOMDepartmentParser;

import java.util.ArrayList;

/**
 * Variant 4
 */
public class Main {
    public static void main(String[] args) {
        DOMDepartmentParser parser = new DOMDepartmentParser(
                "src/com/company/part2/lab1/data/training_department.xsd");
        var dep = parser.readGemFromXmlFile(
                "src/com/company/part2/lab1/data/training_department.xml");
        System.out.println(dep);
        parser.writeGemToXmlFile(dep, "src/com/company/part2/lab1/data/none.xml");
        System.err.println(parser.getErrors());
    }
}
