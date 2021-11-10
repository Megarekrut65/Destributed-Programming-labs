package com.company.part2.lab1.parser;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class DOMDepartmentParser {
    protected Validator validator;
    protected DepartmentHandler gemsHandler;


    public DOMDepartmentParser(String schemaPath) {
        this.gemsHandler = new DepartmentHandler();
        try {
            this.validator = new Validator(schemaPath, gemsHandler);
        } catch (SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
    public List<String> getErrors(){
        return gemsHandler.getErrors();
    }
//    public boolean writeGemToXmlFile(, String filePath){
//
//    }
//
//    public readGemFromXmlFile(String filePath){
//
//    }

    protected void transformFile(String filePath, Source source){
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        try {
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            Result result = new StreamResult(new FileWriter(filePath));
            transformer.transform(source, result);
        } catch (IOException | TransformerException e) {
            e.printStackTrace();
        }
    }
}
