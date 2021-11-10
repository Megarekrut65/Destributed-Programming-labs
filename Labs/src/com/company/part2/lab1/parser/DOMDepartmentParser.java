package com.company.part2.lab1.parser;

import com.company.part2.lab1.Group;
import com.company.part2.lab1.Student;
import com.company.part2.lab1.TrainingDepartment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class DOMDepartmentParser {
    protected Validator validator;
    protected DepartmentHandler gemsHandler;
    private DocumentBuilder docBuilder;
    public DOMDepartmentParser(String schemaPath) {
        this.gemsHandler = new DepartmentHandler();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            this.validator = new Validator(schemaPath, gemsHandler);
            docBuilder = factory.newDocumentBuilder();
        } catch (SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
    public List<String> getErrors(){
        return gemsHandler.getErrors();
    }
    public boolean writeGemToXmlFile(TrainingDepartment department, String filePath){
        return false;
    }

    public TrainingDepartment readGemFromXmlFile(String filePath){
        try {
            validator.validate(filePath);
            if(gemsHandler.getErrors().size() == 0){
                TrainingDepartment department = new TrainingDepartment();
                Document doc = docBuilder.parse(filePath);
                Element root = doc.getDocumentElement();
                NodeList groupList = root.getElementsByTagName(DepartmentXmlTags.GROUP.val());
                for (int i = 0; i < groupList.getLength(); i++) {
                    Element groupElement = (Element) groupList.item(i);
                    Group group = buildGroup(groupElement);
                    department.add(group);
                }
                return department;
            }
        } catch (IOException | SAXException e) {
            e.printStackTrace();
        }
        return null;
    }
    private Group buildGroup(Element groupElement) {
        Group group = new Group();
        //name
        group.setName(groupElement.getAttribute(DepartmentXmlTags.NAME.val()));
        //course
        group.setCourse(Integer.parseInt(groupElement.getAttribute(DepartmentXmlTags.COURSE.val())));
        //study form
        group.setStudyForm(getElementTextContent(groupElement,DepartmentXmlTags.STUDY_FORM.val()));
        //students
        var students = group.getStudents();
        var studentsElement = (Element)groupElement.getElementsByTagName(
               DepartmentXmlTags.STUDENTS.val()).item(0);
        buildStudents(students, studentsElement);
        return group;
    }
    private void buildStudents(List<Student> students, Element studentsElement){
        NodeList studentsList = studentsElement.getElementsByTagName(DepartmentXmlTags.STUDENT.val());
        for(int i = 0; i < studentsList.getLength(); i++){
            Element studentElement = (Element) studentsList.item(i);
            Student student = buildStudent(studentElement);
            students.add(student);
        }
    }
    private Student buildStudent(Element studentElement){
        Student student = new Student();
        //id
        student.setId(studentElement.getAttribute(DepartmentXmlTags.ID.val()));
        //name
        student.setName(getElementTextContent(studentElement, DepartmentXmlTags.NAME.val()));
        //surname
        student.setSurname(getElementTextContent(studentElement, DepartmentXmlTags.SURNAME.val()));
        //age
        student.setAge(Integer.parseInt(getElementTextContent(studentElement, DepartmentXmlTags.AGE.val())));
        //gpa
        student.setGpa(Integer.parseInt(getElementTextContent(studentElement, DepartmentXmlTags.GPA.val())));
        return student;
    }
    private static String getElementTextContent(Element element, String elementName) {
        NodeList nList = element.getElementsByTagName(elementName);
        Node node = nList.item(0);
        return node.getTextContent();
    }
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
