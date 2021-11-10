package com.company.part2.lab1.parser;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Use during validation. After that there will be errors.
 */
public class DepartmentHandler extends DefaultHandler {
    private List<String> errors = new ArrayList<>();
    public DepartmentHandler() {
        super();
    }
    private void log(String text){
        errors.add(text);
        //System.err.println(text);
    }

    @Override
    public String toString() {
        return "DepartmentHandler{" +
                "errors=" + errors +
                '}';
    }

    public List<String> getErrors() {
        return errors;
    }

    @Override
    public void warning(SAXParseException e){
        this.log("WARNING "+getErrorAddress(e) + " " + e.getMessage());
    }

    @Override
    public void error(SAXParseException e){
        this.log("ERROR "+getErrorAddress(e) + " " + e.getMessage());
    }

    @Override
    public void fatalError(SAXParseException e){
        this.log("FATAL "+getErrorAddress(e) + " " + e.getMessage());
    }
    private String getErrorAddress(SAXParseException e){
        return e.getLineNumber() + ":"+e.getColumnNumber();
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        errors = new ArrayList<>();
    }
}
