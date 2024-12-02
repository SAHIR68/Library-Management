package user.dao;


import book.dto.BookDTO;
import user.dto.UserDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private static List<UserDTO> userDTOS = new ArrayList<>();

    public void addUser(UserDTO userDTO) {
        userDTOS.add(userDTO);
    }

    public List<UserDTO> getAllUser() {
        return userDTOS;
    }

    public void addUserToDatabase(UserDTO userDTO){
        String SQL_INSERT = "INSERT INTO tbl_user (c_name, c_dateOfBirth, c_occupation, c_age, c_ssn) VALUES (?,?,?,?,?)";
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/LibManager", "root", "");
             PreparedStatement preparedStatement = conn.prepareStatement(SQL_INSERT)) {

            preparedStatement.setString(1, userDTO.getName());
            preparedStatement.setTimestamp(2, new Timestamp(userDTO.getDateOfBirth().getTime()));
            preparedStatement.setString(3, userDTO.getOccupation());
            preparedStatement.setInt(4, userDTO.getAge());
            preparedStatement.setString(5, userDTO.getSsn());

            int row = preparedStatement.executeUpdate();
            // rows affected
            System.out.println(row); //1

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public int getUserCount() {
        String SQL_COUNT = "SELECT COUNT(*) FROM tbl_user";
        int count = 0;

        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/LibManager", "root", "");
             PreparedStatement preparedStatement = conn.prepareStatement(SQL_COUNT)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt(1); // تعداد رکوردها را از اولین ستون دریافت می‌کنیم
            }

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }

    public UserDTO findUserById(int id) {
        String SQL_SELECT = "SELECT * FROM tbl_user WHERE c_id = ?";
        UserDTO user = new UserDTO();

        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/LibManager", "root", "");
             PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                user.setName(resultSet.getString("c_name"));
                user.setDateOfBirth(resultSet.getTimestamp("c_dateOfBirth"));
                user.setOccupation(resultSet.getString("c_occupation"));
                user.setAge(resultSet.getInt("c_age"));
                user.setSsn(resultSet.getString("c_ssn"));
            }

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}
