package Loan.dao;

import Loan.dto.LoanDTO;
import book.dao.BookDAO;
import book.dto.BookDTO;
import config.StaticString;
import user.dao.UserDAO;
import user.dto.UserDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoanDAO {
    public void addLoanToDatabase(LoanDTO loanDTO) {
        String SQL_INSERT = "INSERT INTO tbl_loan (c_userId, c_bookId, c_borrowingDate, c_returningDate) VALUES (?,?,?,?)";
        try (Connection conn = DriverManager.getConnection(
                StaticString.DBUrl, StaticString.DBUser, StaticString.DBPass);
             PreparedStatement preparedStatement = conn.prepareStatement(SQL_INSERT)) {
            java.sql.Date sqlDateOfBorrowingDate = new java.sql.Date(loanDTO.getBorrowingDate().getTime());
            java.sql.Date sqlDateOfReturningDate = new java.sql.Date(loanDTO.getReturningDate().getTime());

            preparedStatement.setInt(1, loanDTO.getUserDTO().getId());
            preparedStatement.setInt(2, loanDTO.getBookDTO().getId());
            preparedStatement.setDate(3, sqlDateOfBorrowingDate);
            preparedStatement.setDate(4, sqlDateOfReturningDate);
            preparedStatement.executeUpdate();

            //loanDTO.setId(loanDTO.getId());

            BookDAO bookDAO = new BookDAO();
            bookDAO.updateBookReservationStatus(loanDTO.getBookDTO().getId(), true);

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean isBookLoaned(int bookId) {

        String SQL_CHECK = "SELECT c_reserve FROM tbl_book WHERE c_id = ?";
        boolean isLoaned = false;

        try (Connection conn = DriverManager.getConnection(
                StaticString.DBUrl, StaticString.DBUser, StaticString.DBPass);
             PreparedStatement preparedStatement = conn.prepareStatement(SQL_CHECK)) {
            preparedStatement.setInt(1, bookId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    isLoaned = resultSet.getInt("c_reserve") == 1;
                }
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
        return isLoaned;
    }

    public List<LoanDTO> getLoanDTOSFromDatabase() {
        List<LoanDTO> allLoans = new ArrayList<>();
        String SQL_SELECT = "SELECT * FROM tbl_loan";

        try (Connection conn = DriverManager.getConnection(
                StaticString.DBUrl, StaticString.DBUser, StaticString.DBPass);
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_SELECT)) {

            while (resultSet.next()) {
                LoanDTO loanDTO = new LoanDTO();
                UserDTO userDTO = new UserDTO();
                BookDTO bookDTO = new BookDTO();
                loanDTO.setId(resultSet.getInt("c_id"));
                userDTO.setId(resultSet.getInt("c_userId"));
                bookDTO.setId(resultSet.getInt("c_bookId"));
                loanDTO.setBorrowingDate(resultSet.getDate("c_borrowingDate"));
                loanDTO.setReturningDate(resultSet.getDate("c_returningDate"));
                loanDTO.setUserDTO(userDTO);
                loanDTO.setBookDTO(bookDTO);
                allLoans.add(loanDTO);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allLoans;
    }

    public void deleteLoanById(int loanId) {

        String SQL_DELETE = "DELETE FROM tbl_loan WHERE c_id = ?";

        try (Connection conn = DriverManager.getConnection(
                StaticString.DBUrl, StaticString.DBUser, StaticString.DBPass);
             PreparedStatement preparedStatement = conn.prepareStatement(SQL_DELETE)) {

            preparedStatement.setInt(1, loanId);
            int rowsDeleted = preparedStatement.executeUpdate();
            System.out.println("Rows deleted: " + rowsDeleted);

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }

}
