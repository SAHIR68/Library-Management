package Loan.service;

import Loan.dao.LoanDAO;
import Loan.dto.LoanDTO;
import book.dao.BookDAO;
import book.dto.BookDTO;
import book.service.BookServiceImpl;
import user.dto.UserDTO;
import user.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LoanServiceImpl implements LoanService {

    @Override
    public void addLoan(LoanDTO loanDTO) {
        LoanDAO loanDAO = new LoanDAO();
        loanDAO.addLoanToDatabase(loanDTO);
    }

    @Override
    public void borrowBook(LoanDTO loanDTO) {
        LoanDAO loanDAO = new LoanDAO();
        loanDTO.getBookDTO().setReserve(true);
        loanDAO.addLoaned(loanDTO);//adding loaned to the loanDTOS
        Date today = new Date();
        Date backDay = new Date();
        backDay.setMonth(today.getMonth() + 1);
        backDay.setYear(today.getYear());
        backDay.setDate(today.getDate());
        loanDTO.setBorrowingDate(today);
        loanDTO.setReturningDate(backDay);
        System.out.println(loanDTO.getBookDTO().isReserved());


    }

    @Override
    public void returnBook(LoanDTO loanDTO) {
        LoanDAO loanDAO = new LoanDAO();
        loanDTO.getBookDTO().setReserve(false);
        BookDAO bookDAO = new BookDAO();
        Date today = new Date();
        if (today.getTime() < loanDTO.getReturningDate().getTime()) {
            bookDAO.updateBookReservationStatus(loanDTO.getBookDTO().getId(), loanDTO.getBookDTO().isReserved());
            loanDAO.deleteLoanById(loanDTO.getId());
        }
            else
            System.out.println("Delayed!!!");

    }

    public Integer dayOfDate(Date date) {
        Integer day = date.getDay();
        return day;
    }

    public Integer monthOfDate(Date date) {
        Integer month = date.getMonth();
        return month;
    }

    public Integer yearOfDate(Date date) {
        Integer year = date.getYear();
        return year;
    }

    public LoanDTO searchLoanDTO(Integer userId, Integer bookId) {
        LoanDTO loanDTO = new LoanDTO();
        UserDTO userDTO = new UserDTO();
        BookDTO bookDTO = new BookDTO();

        UserServiceImpl userService = new UserServiceImpl();
        userDTO = userService.searchUserDTO(userId);
        loanDTO.setUserDTO(userDTO);

        BookServiceImpl bookService = new BookServiceImpl();
        bookDTO = bookService.searchBookDTO(bookId);
        loanDTO.setBookDTO(bookDTO);
        return loanDTO;
    }

    public Integer numberOfBorrowedBooks(LoanDTO loanDTO) {
        Integer numberOfBooks = 0;
        LoanDAO loanDAO = new LoanDAO();
        List<LoanDTO> allLoanDTO = new ArrayList<>();
        allLoanDTO = loanDAO.getAllLoaned();
        for (LoanDTO loanDTO1 : allLoanDTO) {
            if (loanDTO1.getUserDTO().equals(loanDTO.getUserDTO())) {
                if (loanDTO1.getBookDTO().isReserved())
                    numberOfBooks++;
            }
        }
        return numberOfBooks;
    }

    /*public boolean belongBookToUser(LoanDTO loanDTO) {
        boolean belongs = false;
        LoanDAO loanDAO = new LoanDAO();
        List<LoanDTO> allLoanDTO = new ArrayList<>();
        allLoanDTO = loanDAO.getAllLoaned();
        for (LoanDTO loanDTO1 : allLoanDTO) {
            belongs = loanDTO1.getUserDTO().equals(loanDTO.getUserDTO()) && (loanDTO1.getBookDTO().equals(loanDTO.getBookDTO()));
            if (belongs)
                break;
        }
        return belongs;
    }*/
}

