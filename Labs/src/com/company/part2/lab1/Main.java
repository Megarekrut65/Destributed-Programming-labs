package com.company.part2.lab1;

import com.company.part2.lab1.parser.DepartmentHandler;
import com.company.part2.lab1.parser.Validator;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Variant 4
 */
public class Main {
    public static void main(String[] args) {
        DepartmentHandler handler = new DepartmentHandler();
        try {
            Validator validator = new Validator("Labs/src/com/company/part2/lab1/data/training_department.xsd",
                    handler);
            validator.validate("Labs/src/com/company/part2/lab1/data/training_department.xml");
            System.err.println(handler.getErrors());
        } catch (SAXException | ParserConfigurationException | IOException e) {
            e.printStackTrace();
        }

    }
}
