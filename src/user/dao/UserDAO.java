package user.dao;


import Loan.dto.LoanDTO;
import book.dto.BookDTO;
import config.StaticString;
import user.dto.UserDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    public void addUserToDatabase(UserDTO userDTO){
        String SQL_INSERT = "INSERT INTO tbl_user (c_name, c_dateOfBirth, c_occupation, c_age, c_ssn) VALUES (?,?,?,?,?)";
        try (Connection conn = DriverManager.getConnection(
                StaticString.DBUrl, StaticString.DBUser, StaticString.DBPass);
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
                StaticString.DBUrl, StaticString.DBUser, StaticString.DBPass);
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
        UserDTO userDTO = new UserDTO();

        try (Connection conn = DriverManager.getConnection(
                StaticString.DBUrl, StaticString.DBUser, StaticString.DBPass);
             PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                userDTO.setName(resultSet.getString("c_name"));
                userDTO.setDateOfBirth(resultSet.getTimestamp("c_dateOfBirth"));
                userDTO.setOccupation(resultSet.getString("c_occupation"));
                userDTO.setAge(resultSet.getInt("c_age"));
                userDTO.setSsn(resultSet.getString("c_ssn"));
            }

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userDTO;
    }

    public List<UserDTO> getUserDTOSFromDatabase() {
        List<UserDTO> allUsers = new ArrayList<>();
        String SQL_SELECT = "SELECT * FROM tbl_user";

        try (Connection conn = DriverManager.getConnection(
                StaticString.DBUrl, StaticString.DBUser, StaticString.DBPass);
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_SELECT)) {

            while (resultSet.next()) {
                UserDTO userDTO = new UserDTO();

                userDTO.setName(resultSet.getString("c_name"));
                userDTO.setDateOfBirth(resultSet.getTimestamp("c_dateOfBirth"));
                userDTO.setOccupation(resultSet.getString("c_occupation"));
                userDTO.setAge(resultSet.getInt("c_age"));
                userDTO.setSsn(resultSet.getString("c_ssn"));
                allUsers.add(userDTO);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allUsers;
    }
}
