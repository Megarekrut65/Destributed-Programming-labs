package com.company.part2.lab2;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
public class Main {
    public static void main(String[] args) {
        try {
            String url = "jdbc:mysql://" + "localhost" + ":" + "3306" + "/" +
                    "department";
            var con = DriverManager.getConnection(url, "root", "BF4B9NvZZpZ_rt");
            var stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT ID_ST, name FROM students");
            System.out.println("Students:");
            while (rs.next()) {
                int id = rs.getInt("ID_ST");
                String name = rs.getString("name");
                System.out.println(">>" + id + "-" + name);
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
