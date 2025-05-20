package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/announce?serverTimezone=Asia/Seoul";
    private static final String USER = "root";
    private static final String PW = "asd1234";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PW);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
