package com.company.part2.lab2;

import com.company.part2.subjectarea.DepartmentManager;
import com.company.part2.subjectarea.Group;
import com.company.part2.subjectarea.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentSqlManager implements DepartmentManager {
    private Connection connection;
    private Statement statement;

    public DepartmentSqlManager(String ip, int port, String databaseName) {
        String url = "jdbc:mysql://" + ip + ":" + port + "/" +
                databaseName;
        try {
            connection = DriverManager.getConnection(url, "root", "BF4B9NvZZpZ_rt");
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<Group> getGroups(){
        List<Group> groups = new ArrayList<>();
        String sql = "SELECT ID_GR, name, course, study_form FROM theGroups";
        try {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                groups.add(new Group(
                        rs.getInt("ID_GR"),
                        rs.getString("name"),
                        rs.getInt("course"),
                        rs.getString("study_form")
                ));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groups;
    }
    public void showGroups(){
        var list = getGroups();
        System.out.println("\nGroups:");
        for(var group:list){
            System.out.println(group);
        }
    }
    @Override
    public boolean addGroup(Group group){
        try {
            var st = connection.prepareStatement(
                    "INSERT INTO theGroups"+
                            "(ID_GR, name, course, study_form)"+
                            "values(?,?,?,?)");
            st.setInt(1,group.getId());
            st.setString(2, group.getName());
            st.setInt(3,group.getCourse());
            st.setString(4, group.getStudyForm());
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public boolean deleteGroup(int id) {
        String sql = "DELETE FROM theGroups WHERE ID_GR =" + id;
        try {
            int res = statement.executeUpdate(sql);
            return res > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public Group findGroup(int id){
        String sql = "SELECT ID_GR, name, course, study_form FROM theGroups WHERE ID_GR =" + id;
        try {
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                return new Group(
                        rs.getInt("ID_GR"),
                        rs.getString("name"),
                        rs.getInt("course"),
                        rs.getString("study_form"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public List<Student> getStudents(){
        List<Student> students = new ArrayList<>();
        String sql = "SELECT ID_ST, ID_GR, name, surname, age, gpa FROM students";
        return getStudentsInGroup(students, sql);
    }
    @Override
    public List<Student> getStudentsInGroup(int groupId){
        List<Student> students = new ArrayList<>();
        String sql = "SELECT ID_ST, ID_GR, name, surname, age, gpa FROM students WHERE ID_GR ="+groupId;
        return getStudentsInGroup(students, sql);
    }

    private List<Student> getStudentsInGroup(List<Student> students, String sql) {
        try {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                students.add(new Student(
                        rs.getInt("ID_ST"),
                        rs.getInt("ID_GR"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getInt("age"),
                        rs.getInt("gpa")
                ));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public void showStudents(){
        var list = getStudents();
        System.out.println("\nStudents:");
        for(var student:list){
            System.out.println(student);
        }
    }
    public void showStudents(int groupId){
        var list = getStudentsInGroup(groupId);
        System.out.println("\nStudents:");
        for(var student:list){
            System.out.println(student);
        }
    }
    @Override
    public boolean addStudent(Student student){
        try {
            var st = connection.prepareStatement(
                    "INSERT INTO students"+
                            "(ID_ST, ID_GR, name, surname, age, gpa)"+
                            "values(?,?,?,?,?,?)");
            st.setString(1,student.getId());
            st.setInt(2, student.getGroupId());
            st.setString(3, student.getName());
            st.setString(4, student.getSurname());
            st.setInt(5, student.getAge());
            st.setInt(6, student.getGpa());
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public boolean deleteStudent(int id, int groupId) {
        String sql = "DELETE FROM students WHERE ID_ST =" + id + " AND ID_GR =" + groupId;
        try {
            int res = statement.executeUpdate(sql);
            return res > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public Student findStudent(int id, int groupId){
        String sql = "SELECT ID_ST, ID_GR, name, surname, age, gpa FROM students WHERE ID_ST ="
                + id + " AND ID_GR =" + groupId;
        try {
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                return new Student(
                        rs.getInt("ID_ST"),
                        rs.getInt("ID_GR"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getInt("age"),
                        rs.getInt("gpa"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void stop() throws SQLException {
        statement.close();
        connection.close();
    }
}
