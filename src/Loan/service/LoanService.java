package Loan.service;

import Loan.dto.LoanDTO;
import book.dto.BookDTO;
import user.dto.UserDTO;

public interface LoanService {
    void addLoan(LoanDTO loanDTO);
    void borrowBook(LoanDTO loanDTO);
    void returnBook(LoanDTO loanDTO);
    }

