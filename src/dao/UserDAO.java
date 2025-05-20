package dao;

import dto.User;
import util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    // 유저 추가
    public void addUser(User user) throws SQLException {
        String insertSql = "insert into user(user_name, email, password, address) values (?, ?, ?, ?) ";
        try(Connection conn = Database.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(insertSql);
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getAddress());
            pstmt.executeUpdate();
        }
    }

    // 전체 유저 조회
    public List<User> getAllUser() throws SQLException {
        List<User> userList = new ArrayList<>();
        String checkSql = "select * from user ";

        try(Connection conn = Database.getConnection();) {
            PreparedStatement pstmt = conn.prepareStatement(checkSql);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("user_name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String address = rs.getString("address");

                userList.add(new User(id, name, email, password, address));
            }
        }
        return userList;
    }

    // 유저 인증
    public void authenticateUser(String name) throws SQLException {
        String checkSql = "select * from user where user_name = ? ";

        try(Connection conn = Database.getConnection();) {
            PreparedStatement pstmt = conn.prepareStatement(checkSql);
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();

            if(!rs.next()) {
                throw new SQLException("해당 유저는 존재하지 않습니다.");
            }
        }
    }

    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();
        List<User> userList = new ArrayList<>();

        try {
            userDAO.addUser(new User(1, "김철수", "a@naver.com", "asd1234", "부산시 부산진구"));
            userList = userDAO.getAllUser();

            for (int i = 0; i < userList.size(); i++) {
                System.out.println(userList.get(i));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
