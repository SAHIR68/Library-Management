package book.service;

import book.dao.BookDAO;
import book.dto.BookDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BookServiceImpl implements BookService {

    @Override
    public void addBook(BookDTO bookDTO) {
        BookDAO bookDAO = new BookDAO();
        bookDAO.addBookToDatabase(bookDTO);
    }

    @Override
    public void readBook(Integer bookId) {
        BookDTO bookDTO = new BookDTO();
        bookDTO = searchBookDTO(bookId);
        if (bookDTO.getId() == null)
            System.out.println("This book not found in this library!");
        else
            System.out.println(bookDTO);
    }


    @Override
    public BookDTO updateBook(Integer bookID) {
        BookDTO bookDTO = new BookDTO();
        bookDTO = searchBookDTO(bookID);
        return bookDTO;
    }

    @Override
    public void deleteBook(Integer bookID) {
        BookDTO bookDTO = new BookDTO();
        bookDTO = searchBookDTO(bookID);
        bookDTO.setTitle(null);
        bookDTO.setId(0);
        bookDTO.setReserve(false);
    }

    @Override
    public List<BookDTO> getAllBook() {
        BookDAO bookDAO = new BookDAO();
        return bookDAO.readBooksFromDatabase();
    }

    @Override
    public void printAllBooks() {
        for (BookDTO bookDTO : getAllBook()) {
            System.out.println(bookDTO.toString());
        }
    }

    public BookDTO searchBookDTO(Integer bookId) {
        BookDTO bookDTO = new BookDTO();
        BookDAO bookDAO = new BookDAO();
        bookDTO = bookDAO.findBookById(bookId);
        bookDTO.setId(bookId);
        return bookDTO;
    }

    public Integer searchBookTitle(String bookTitle) {
        Integer bookId = 0;
        List<BookDTO> allBookDTO = getAllBook();
        for (BookDTO bookDTO : allBookDTO) {
            if (Objects.equals(bookDTO.getTitle(), bookTitle)) {
                bookId = bookDTO.getId();
                break;
            } else
                bookId = 0;
        }
        return bookId;
    }
}