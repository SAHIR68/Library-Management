package book.dao;

import book.dto.BookDTO;
import config.StaticString;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BookDAO {
  /*  private static List<BookDTO> bookDTOS = new ArrayList<>();

    public void addBook(BookDTO bookDTO) {
        bookDTOS.add(bookDTO);
    }

    public List<BookDTO> getAllBook() {
        return bookDTOS;
    }

    public BookDTO searchBook(Integer ID) {
        BookDTO bookDTO1 = new BookDTO();
        for (BookDTO bookDTO : bookDTOS) {
            if (Objects.equals(bookDTO.getId(), ID))
                bookDTO1 = bookDTO;
        }
        return bookDTO1;
    }*/

    public void addBookToDatabase(BookDTO bookDTO) {
        String SQL_INSERT = "INSERT INTO tbl_book (c_title, c_writer, c_reserve) VALUES (?,?,?)";
        try (Connection conn = DriverManager.getConnection(
                StaticString.DBUrl, StaticString.DBUser, StaticString.DBPass);
             PreparedStatement preparedStatement = conn.prepareStatement(SQL_INSERT)) {

            preparedStatement.setString(1, bookDTO.getTitle());
            preparedStatement.setString(2, bookDTO.getWriter());
            preparedStatement.setBoolean(3, bookDTO.isReserved());

            int row = preparedStatement.executeUpdate();


            System.out.println(row);

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getBookCount() {
        String SQL_COUNT = "SELECT COUNT(*) FROM tbl_book";
        int count = 0;

        try (Connection conn = DriverManager.getConnection(
                StaticString.DBUrl, StaticString.DBUser, StaticString.DBPass);
             PreparedStatement preparedStatement = conn.prepareStatement(SQL_COUNT)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }

    public BookDTO findBookById(int id) {
        String SQL_SELECT = "SELECT * FROM tbl_book WHERE c_id = ?";
        BookDTO book = new BookDTO();

        try (Connection conn = DriverManager.getConnection(
                StaticString.DBUrl, StaticString.DBUser, StaticString.DBPass);
             PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.executeQuery();


            if (resultSet.next()) {

                book.setTitle(resultSet.getString("c_title"));
                book.setWriter(resultSet.getString("c_writer"));
                book.setReserve(resultSet.getBoolean("c_reserve"));

            }

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return book;
    }

    public void updateBookReservationStatus(int bookId, boolean isReserved) {
        String SQL_UPDATE = "UPDATE tbl_book SET c_reserve = ? WHERE c_id = ?";

        try (Connection conn = DriverManager.getConnection(
                StaticString.DBUrl, StaticString.DBUser, StaticString.DBPass);
             PreparedStatement preparedStatement = conn.prepareStatement(SQL_UPDATE)) {
            preparedStatement.setBoolean(1, isReserved);
            preparedStatement.setInt(2, bookId);
            int rowsUpdated = preparedStatement.executeUpdate();
            System.out.println("Rows updated: " + rowsUpdated);


        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }

    public List<BookDTO> readBooksFromDatabase() {
        List<BookDTO> allBooks = new ArrayList<>();
        String SQL_SELECT = "SELECT * FROM tbl_book";

        try (Connection conn = DriverManager.getConnection(
                StaticString.DBUrl, StaticString.DBUser, StaticString.DBPass);
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_SELECT)) {

            while (resultSet.next()) {
                BookDTO bookDTO = new BookDTO();
                bookDTO.setTitle(resultSet.getString("c_title"));
                bookDTO.setWriter(resultSet.getString("c_writer"));
                bookDTO.setReserve(resultSet.getBoolean("c_reserve"));
                allBooks.add(bookDTO);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allBooks;
    }
}
