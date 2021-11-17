package com.company.part2.lab1.parser;

import com.company.part2.subjectarea.Group;
import com.company.part2.subjectarea.Student;
import com.company.part2.subjectarea.TrainingDepartment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
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
    public boolean writeDepartmentToXmlFile(TrainingDepartment department, String filePath){
        if(department == null) return false;
        Document document = docBuilder.newDocument();
        Element rootElement = document.createElement(DepartmentXmlTags.TRAINING_DEPARTMENT.val());
        document.appendChild(rootElement);
        var groups = department.getGroups();
        for(var group:groups){
            rootElement.appendChild(createGroupElement(group,document));
        }
        transformFile(filePath, new DOMSource(document));
        try {
            validator.validate(filePath);
        } catch (IOException | SAXException e) {
            e.printStackTrace();
        }
        return gemsHandler.getErrors().size() == 0;
    }
    private Element createGroupElement(Group group, Document document){
        var groupElement = document.createElement(DepartmentXmlTags.GROUP.val());
        //attributes
        groupElement.setAttribute(DepartmentXmlTags.NAME.val(), group.getName());
        groupElement.setAttribute(DepartmentXmlTags.COURSE.val(), Integer.toString(group.getCourse()));
        //study form
        groupElement.appendChild(createSimpleElement(
                DepartmentXmlTags.STUDY_FORM.val(), group.getStudyForm(), document));
        //students
        groupElement.appendChild(createStudentsElement(group.getStudents(), document));

        return groupElement;
    }
    private Element createStudentsElement(List<Student> students, Document document){
        var studentsElement = document.createElement(DepartmentXmlTags.STUDENTS.val());
        for(var student:students){
            studentsElement.appendChild(createStudentElement(student, document));
        }
        return studentsElement;
    }
    private Element createStudentElement(Student student, Document document){
        var studentElement = document.createElement(DepartmentXmlTags.STUDENT.val());
        //attributes
        studentElement.setAttribute(DepartmentXmlTags.ID.val(), student.getId());
        //name
        studentElement.appendChild(createSimpleElement(
                DepartmentXmlTags.NAME.val(), student.getName(), document));
        //surname
        studentElement.appendChild(createSimpleElement(
                DepartmentXmlTags.SURNAME.val(), student.getSurname(), document));
        //age
        studentElement.appendChild(createSimpleElement(
                DepartmentXmlTags.AGE.val(), Integer.toString(student.getAge()), document));
        //gpa
        studentElement.appendChild(createSimpleElement(
                DepartmentXmlTags.GPA.val(), Integer.toString(student.getGpa()), document));
        return studentElement;
    }
    private Element createSimpleElement(String name, String content, Document document){
        var element = document.createElement(name);
        element.appendChild(document.createTextNode(content));
        return element;
    }
    public TrainingDepartment readDepartmentFromXmlFile(String filePath){
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
