package Loan.dao;

import Loan.dto.LoanDTO;
import book.dao.BookDAO;
import book.dto.BookDTO;
import user.dao.UserDAO;
import user.dto.UserDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoanDAO {
    private static List<LoanDTO> loanDTOS = new ArrayList<>();

    public void addLoaned(LoanDTO loanDTO) {
        loanDTOS.add(loanDTO);
    }

    public List<LoanDTO> getAllLoaned() {
        return loanDTOS;
    }

    public void addLoanToDatabase(LoanDTO loanDTO) {
        String SQL_INSERT = "INSERT INTO tbl_loan (c_userId, c_bookId, c_borrowingDate, c_returningDate) VALUES (?,?,?,?)";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/LibManager", "root", "");
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

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/LibManager", "root", "");
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

    public LoanDTO findLoanById(int loanId) {
        String SQL_SELECT = "SELECT c_userId, c_bookId, c_borrowingDate, c_returningDate FROM tbl_loan WHERE c_id = ?";
        LoanDTO loan = new LoanDTO();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/LibManager", "root", "");
             PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT)) {


            preparedStatement.setInt(1, loanId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {

                    int userId = resultSet.getInt("c_userId");
                    int bookId = resultSet.getInt("c_bookId");
                    java.sql.Date borrowingDate = resultSet.getDate("c_borrowingDate");
                    java.sql.Date returningDate = resultSet.getDate("c_returningDate");

                    UserDTO userDTO = new UserDTO();
                    UserDAO userDAO = new UserDAO();
                    userDTO = userDAO.findUserById(userId);

                    BookDTO bookDTO = new BookDTO();
                    BookDAO bookDAO = new BookDAO();
                    bookDTO = bookDAO.findBookById(bookId);

                    loan = new LoanDTO();
                    loan.setId(loanId);
                    loan.setUserDTO(userDTO);
                    loan.setBookDTO(bookDTO);
                    loan.setBorrowingDate(new java.util.Date(borrowingDate.getTime()));
                    if (returningDate != null) {
                        loan.setReturningDate(new java.util.Date(returningDate.getTime()));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }

        return loan;
    }
    public int findLoanId(int userId, int bookId) {
        String SQL_SELECT = "SELECT c_id FROM tbl_loan WHERE c_userId = ? AND c_bookId = ?";
        int loanId = 0;

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/LibManager", "root", "");
             PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT)) {

            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, bookId);


            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    loanId = resultSet.getInt("id");
                }
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }

        return loanId;
    }

    public void deleteLoanById(int loanId) {

        String SQL_DELETE = "DELETE FROM tbl_loan WHERE c_id = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/LibManager", "root", "");
             PreparedStatement preparedStatement = conn.prepareStatement(SQL_DELETE)) {

            preparedStatement.setInt(1, loanId);
            int rowsDeleted = preparedStatement.executeUpdate();
            System.out.println("Rows deleted: " + rowsDeleted);

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }

}
