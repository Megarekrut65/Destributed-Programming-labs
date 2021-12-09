package com.boa.lab6;

import com.boa.lab6.datebase.DepartmentSqlManager;
import com.boa.lab6.datebase.subarea.DepartmentManager;
import com.boa.lab6.datebase.subarea.Group;
import com.boa.lab6.datebase.subarea.Student;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
public class DepartmentRESTManager implements DepartmentManager {
    private final DepartmentSqlManager database;

    public DepartmentRESTManager() {
        database = new DepartmentSqlManager("localhost", 3306, "department");
    }
    @RequestMapping("/")
    public String index(){
        return "index";
    }
    @GetMapping("/add-student")
    public String studentForm(Model model) {
        model.addAttribute("student", new Student());
        return "add-student";
    }

    @PostMapping("/add-student")
    public String studentSubmit(@ModelAttribute Student student, Model model) {
        model.addAttribute("student", student);
        if(addStudent(student))
         return "student-result";
        return "bad-result";
    }
    @Override
    public boolean addStudent(Student student) {
        return database.addStudent(student);
    }
    @GetMapping("/add-group")
    public String groupForm(Model model) {
        model.addAttribute("group", new Group());
        return "add-group";
    }

    @PostMapping("/add-group")
    public String groupSubmit(@ModelAttribute Group group, Model model) {
        model.addAttribute("group", group);
        if(addGroup(group))
            return "group-result";
        return "bad-result";
    }
    @Override
    public boolean addGroup(Group group) {
        return database.addGroup(group);
    }
    @GetMapping("/delete-student")
    public String studentDeleteForm(Model model) {
        model.addAttribute("student", new Student());
        return "delete-student";
    }

    @PostMapping("/delete-student")
    public String studentDeleteSubmit(@ModelAttribute Student id, Model model) {
        if(deleteStudent(Integer.parseInt(id.getId()), id.getGroupId())){
            return "good-result";
        }

        return "bad-result";
    }
    @Override
    public boolean deleteStudent(int id, int groupId) {
        return database.deleteStudent(id,groupId);
    }
    @GetMapping("/delete-group")
    public String groupDeleteForm(Model model) {
        model.addAttribute("group", new Group());
        return "delete-group";
    }

    @PostMapping("/delete-group")
    public String groupDeleteSubmit(@ModelAttribute Group id, Model model) {
        if(deleteGroup(id.getId())){
            return "good-result";
        }
        return "bad-result";
    }
    @Override
    public boolean deleteGroup(int id) {
        return database.deleteGroup(id);
    }
    @GetMapping("/find-student")
    public String studentFindForm(Model model) {
        model.addAttribute("student", new Student());
        return "find-student";
    }

    @PostMapping("/find-student")
    public String studentFindSubmit(@ModelAttribute Student id, Model model) {
        Student student = findStudent(Integer.parseInt(id.getId()), id.getGroupId());
        if(student != null){
            model.addAttribute("student", student);
            return "student-result";
        }

        return "bad-result";
    }
    @Override
    public Student findStudent(int id, int groupId) {
        return database.findStudent(id,groupId);
    }
    @GetMapping("/find-group")
    public String groupFindForm(Model model) {
        model.addAttribute("group", new Group());
        return "find-group";
    }

    @PostMapping("/find-group")
    public String groupFindSubmit(@ModelAttribute Group id, Model model) {
        Group group = findGroup(id.getId());
        if(group != null){
            model.addAttribute("group", group);
            return "group-result";
        }
        return "bad-result";
    }
    @Override
    public Group findGroup(int id) {
        return database.findGroup(id);
    }
    @GetMapping("/get-students-group")
    public String studentGroupForm(Model model) {
        model.addAttribute("student", new Student());
        return "get-students-group";
    }

    @PostMapping("/get-students-group")
    public String studentGroupSubmit(@ModelAttribute Student id, Model model) {
        List<Student> students = getStudentsInGroup(id.getGroupId());
        if(students != null){
            model.addAttribute("students", students);
            return "get-students";
        }

        return "bad-result";
    }
    @Override
    public List<Student> getStudentsInGroup(int groupId) {
        return database.getStudentsInGroup(groupId);
    }
    @GetMapping("/get-students")
    public String getStudents(Model model){
        List<Student> students = getStudents();
        model.addAttribute("students", students);
        return "get-students";
    }
    @Override
    public List<Student> getStudents() {
        return database.getStudents();
    }
    @GetMapping("/get-groups")
    public String getGroups(Model model){
        List<Group> groups = getGroups();
        model.addAttribute("groups", groups);
        return "get-groups";
    }
    @Override
    public List<Group> getGroups() {
        return database.getGroups();
    }

    @Override
    public void close() {
        database.close();
    }
}
