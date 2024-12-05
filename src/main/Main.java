package main;

import Loan.dao.LoanDAO;
import Loan.dto.LoanDTO;
import Loan.service.LoanServiceImpl;
import book.dao.BookDAO;
import book.dto.BookDTO;
import book.service.BookService;
import book.service.BookServiceImpl;
import user.dao.UserDAO;
import user.dto.UserDTO;
import user.service.UserService;
import user.service.UserServiceImpl;
import java.util.Date;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Main main = new Main();
        main.start();

    }

    private void start() {

        Long bookID = 0L;
        boolean havingConsule = true;
        while (havingConsule) {
            showMainMenu();
            havingConsule = showUserMenu();
        }
    }

    void showMainMenu() {
        System.out.println("""

                Welcome to the software of management of library
                Please enter number of one of options:
                1) Management of Members
                2) Management of Books
                3) Borrowing and Returning Book
                4) List of Delayed
                5) Search
                6) Exit""");
    }

    boolean showUserMenu() {
        Scanner scanner = new Scanner(System.in);
        Integer mainCommand = scanner.nextInt();
        boolean notExit = true;
        switch (mainCommand) {
            case 1 -> {
                showMemberMenu();
                int memberCommand = scanner.nextInt();
                switch (memberCommand) {
                    case 1 -> {
                        UserDTO userDTO = new UserDTO();
                        setUserProperties(userDTO);
                        UserDAO userDAO = new UserDAO();
                        Integer lastUserId = userDAO.getUserCount();
                        userDTO.setId(lastUserId + 1);
                        System.out.printf("\nYour id number is: %d \n", userDTO.getId());
                        UserService userService = new UserServiceImpl();
                        userService.addUser(userDTO);

                    }
                    case 2 -> {
                        System.out.println("Please enter the ID: ");
                        Integer readID = scanner.nextInt();
                        UserDTO userDTO = new UserDTO();
                        UserService userService = new UserServiceImpl();
                        userService.readUser(readID);
                    }
                    case 3 -> {
                        System.out.println("Please enter the ID: ");
                        Integer updateID = scanner.nextInt();
                        UserDTO userDTO = new UserDTO();
                        UserService userService = new UserServiceImpl();
                        userDTO = userService.updateUser(updateID);
                        setUserProperties(userDTO);
                    }
                    case 4 -> {
                        System.out.println("Please enter the ID: ");
                        Integer deleteID = scanner.nextInt();
                        UserDTO userDTO = new UserDTO();
                        UserService userService = new UserServiceImpl();
                        userService.deleteUser(deleteID);
                    }
                    case 0 -> {
                        break;
                    }
                    default -> System.out.println("Wrong!! Please enter a number between 1 to 4! ");
                }
            }
            case 2 -> {
                showBookMenu();
                Integer bookCommand = scanner.nextInt();
                switch (bookCommand) {
                    case 1 -> {
                        BookDTO bookDTO = new BookDTO();
                        BookDAO bookDAO = new BookDAO();
                        setBookProperties(bookDTO);
                        Integer lastBookId = bookDAO.getBookCount();
                        bookDTO.setId(lastBookId + 1);
                        System.out.printf("\n This book has the id of %d\n", bookDTO.getId());
                        BookService bookService = new BookServiceImpl();
                        bookService.addBook(bookDTO);
                    }
                    case 2 -> {
                        BookDTO bookDTO = new BookDTO();
                        System.out.println("Please enter the book's id: ");
                        Integer readBookId = scanner.nextInt();
                        BookService bookService = new BookServiceImpl();
                        bookService.readBook(readBookId);
                    }
                    case 3 -> {
                        BookDTO bookDTO = new BookDTO();
                        System.out.println("Please enter the book's Id: ");
                        Integer updateBookId = scanner.nextInt();
                        BookService bookService = new BookServiceImpl();
                        bookDTO = bookService.updateBook(updateBookId);
                        setBookProperties(bookDTO);
                    }
                    case 4 -> {
                        BookDTO bookDTO = new BookDTO();
                        System.out.println("Please enter the book's Id: ");
                        Integer deleteBookId = scanner.nextInt();
                        BookService bookService = new BookServiceImpl();
                        bookService.deleteBook(deleteBookId);
                    }
                    case 0 -> {
                        break;
                    }
                    default -> System.out.println("Please enter 0 or 1 or 2 or 3 or 4 !!!");
                }
            }
            case 3 -> {
                showBorrowAndReturnMenu();
                Integer borrowCommand = scanner.nextInt();
                switch (borrowCommand) {
                    case 1 -> {
                        LoanDAO loanDAO = new LoanDAO();
                        LoanDTO loanDTO = new LoanDTO();
                        LoanServiceImpl loanService = new LoanServiceImpl();
                        loanDTO = getLoanedProperties();
                        if ((loanDTO.getBookDTO().getId() != null) && (loanDTO.getUserDTO().getId() != null)) {
                            if (!loanDAO.isBookLoaned(loanDTO.getBookDTO().getId())) {
                                if ((loanService.numberOfBorrowedBooks(loanDTO) < 3)) {
                                    loanService.borrowBook(loanDTO);
                                    loanService.addLoan(loanDTO);
                                } else
                                    System.out.println("You have borrowed 3 books!!");
                            } else
                                System.out.println("This book already have borrowed!!!");
                        } else
                            System.out.println("Not Found!!");
                    }
                    case 2 -> {
                        LoanDAO loanDAO = new LoanDAO();
                        LoanDTO loanDTO = null;
                        LoanServiceImpl loanService = new LoanServiceImpl();
                        loanDTO = getLoanedProperties();
                        for (LoanDTO loanDTO1 : loanDAO.getLoanDTOSFromDatabase()) {
                            if ((loanDTO1.getUserDTO().getId() == loanDTO.getUserDTO().getId()) && (loanDTO1.getBookDTO().getId() == loanDTO.getBookDTO().getId())) {
                                loanService.returnBook(loanDTO1);
                                break;
                            } else
                                System.out.println("You have not borrowed this book!!!");
                        }
                    }
                    case 0 -> {
                        LoanDAO loanDAO = new LoanDAO();
                        System.out.println(loanDAO.getAllLoaned().toString());
                    }
                    default -> System.out.println("Please enter 0 or 1 or 2 !!!");
                }
            }
            case 4 -> {
                LoanDAO loanDAO = new LoanDAO();
                Date today = new Date();
                for (LoanDTO loanDTO: loanDAO.getLoanDTOSFromDatabase()) {
                    if (loanDTO.getReturningDate().getTime() < today.getTime()){
                        System.out.println(loanDTO);
                    }
                    else {
                        System.out.println("We don't have any delayed loan!");
                    }
                }
            }
            case 5 -> {
                showSearchMenu();
                Integer searchCommand = scanner.nextInt();
                switch (searchCommand) {
                    case 1 -> {
                        System.out.println("Please enter his/her name: ");
                        String searchedName = scanner.nextLine().toLowerCase();
                        UserServiceImpl userService = new UserServiceImpl();
                        Integer userId = userService.searchUserName(searchedName);
                        if (userId != 0)
                            userService.readUser(userId);
                        else
                            System.out.println("Not Found!!!");
                    }
                    case 2 -> {
                        System.out.println("Please enter book title: ");
                        String searchedBook = scanner.nextLine().toLowerCase();
                        BookServiceImpl bookService = new BookServiceImpl();
                        Integer bookId = bookService.searchBookTitle(searchedBook);
                    }
                    case 0 -> {
                        break;
                    }
                    default -> System.out.println("Please enter 1 or 2 !!!");
                }
            }
            case 6 -> {
                notExit = false;
            }
            default -> System.out.println("Wrong! Please enter a number from the list that showed!");
        }
        return notExit;
    }

    public Date getBirthday() {
        System.out.println("Please enter your birthday with this format(YYYY/MM/DD): ");
        Scanner scanner = new Scanner(System.in);
        String birthDay = scanner.next();
        Integer yearOfBirth = Integer.parseInt(birthDay.substring(0, 4));
        Integer monthOfBirth = Integer.parseInt(birthDay.substring(5, 7));
        Integer dayOfBirth = Integer.parseInt(birthDay.substring(8, 10));
        Date birthDate = new Date();
        if (yearOfBirth > 1900) {
            birthDate.setYear(1900 -yearOfBirth );
        }
        else
            birthDate.setYear( yearOfBirth - 1900);

        birthDate.setMonth(monthOfBirth-1);
        birthDate.setDate(dayOfBirth);
        return birthDate;
    }

    public void setUserProperties(UserDTO userDTO) {
        System.out.println("Please enter your full name:");
        Scanner scanner = new Scanner(System.in);
        userDTO.setName(scanner.nextLine().toLowerCase());
        userDTO.setDateOfBirth(getBirthday());
        userDTO.setAge(calculateAge(userDTO.getDateOfBirth()));
        System.out.println("Please enter your occupation:");
        userDTO.setOccupation(scanner.nextLine());
        System.out.println("Please enter your Social Security Number(SSN):");
        userDTO.setSsn(scanner.next());
    }

    public void showMemberMenu() {
        System.out.println("""
                Please enter number of one of options:
                1) Add a member
                2) Show a member
                3) Update a member
                4) Delete a member
                0) Back to main menu"""
        );
    }

    public void showBookMenu() {
        System.out.println("""
                Please enter number of one of options:
                1) Add a book
                2) Show a book
                3) Update a book
                4) Delete a book
                0) Back to main menu""");
    }

    public void showBorrowAndReturnMenu() {
        System.out.println("""
                Please enter number of one of options:
                1) Borrowing a book
                2) Returning a book
                0) Back to main menu""");
    }

    public void showSearchMenu() {
        System.out.println("""
                Please enter number of one of options:
                1) Searching for a user
                2) Searching for a book
                0) Back to main menu""");
    }

    public void deleteUserProperties(UserDTO userDTO) {
        userDTO.setName(null);
        userDTO.setAge(null);
        userDTO.setSsn(null);
        userDTO.setOccupation(null);
        userDTO.setId(null);
    }

    public void setBookProperties(BookDTO bookDTO) {
        System.out.println("Please enter this book's title:");
        Scanner scanner = new Scanner(System.in);
        bookDTO.setTitle(scanner.nextLine());
        System.out.println("Please enter this book's writer: ");
        bookDTO.setWriter(scanner.nextLine());
        bookDTO.setReserve(false);
    }

    public Integer calculateAge(Date birthDate) {
        Integer age = 0;
        Date today = new Date();
        if (today.getMonth() > birthDate.getMonth())
            age = birthDate.getYear() - today.getYear();
        else if (today.getMonth() < birthDate.getMonth())
            age = birthDate.getYear() - today.getYear() - 1;
        else if (today.getDate() >= birthDate.getTime())
            age = birthDate.getYear() - today.getYear();
        else
            age = birthDate.getYear() - today.getYear() - 1;
        return age;
    }

    public LoanDTO getLoanedProperties() {
        LoanDTO loanDTO = new LoanDTO();
        UserDTO userDTO = new UserDTO();
        BookDTO bookDTO = new BookDTO();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your id: ");
        Integer returnUserId = scanner.nextInt();
        userDTO.setId(returnUserId);
        System.out.println("Please enter the book id you want to return: ");
        Integer returnBookId = scanner.nextInt();
        bookDTO.setId(returnBookId);
        loanDTO.setUserDTO(userDTO);
        loanDTO.setBookDTO(bookDTO);
        return loanDTO;
    }
}

