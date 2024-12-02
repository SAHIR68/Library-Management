package Loan.dto;

import book.dto.BookDTO;
import user.dto.UserDTO;

import java.util.Date;

public class LoanDTO {

    private Integer id;
    private UserDTO userDTO;
    private BookDTO bookDTO;
    private Date borrowingDate;
    private Date returningDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public BookDTO getBookDTO() {
        return bookDTO;
    }

    public void setBookDTO(BookDTO bookDTO) {
        this.bookDTO = bookDTO;
    }

    public Date getBorrowingDate() {
        return borrowingDate;
    }

    public void setBorrowingDate(Date borrowingDate) {
        this.borrowingDate = borrowingDate;
    }

    public Date getReturningDate() {
        return returningDate;
    }

    public void setReturningDate(Date returningDate) {
        this.returningDate = returningDate;
    }

    @Override
    public String toString() {
        return "LoanDTO{" +
                "userDTO=" + userDTO +
                ", bookDTO=" + bookDTO +
                ", borrowingDate=" + borrowingDate +
                ", returningDate=" + returningDate +
                '}';
    }
}
