package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Util {

    private static final String USER_NAME = "root";
    private static final String PASSWORD = "0n1oh";
    private static final String URL = "jdbc:mysql://localhost:3306/training";

    public static Connection getConnection() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            System.out.println("Подключение к базе успешно");
            return con;
        } catch (ClassNotFoundException e) {
            System.out.println("Драйвер не найден");
            throw new RuntimeException(e);
        } catch (SQLException e) {
            System.out.println("Ошибка подключения к базе данных");
            throw new RuntimeException(e);
        }
    }
}


