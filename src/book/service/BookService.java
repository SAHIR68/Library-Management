package book.service;

import book.dto.BookDTO;

import java.util.List;

public interface BookService {

    void addBook(BookDTO bookDTO);

    void readBook(Integer bookID);

    BookDTO updateBook(Integer bookID);

    void deleteBook(Integer bookID);

    void printAllBooks();

    List<BookDTO> getAllBook();
}
