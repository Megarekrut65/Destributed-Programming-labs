package com.boa.lab6.datebase;


import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        DepartmentSqlManager manager = new DepartmentSqlManager("localhost", 3306, "department");

        try {
            manager.stop();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
