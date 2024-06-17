package com.bookingsystem.BookingSystem.service;

import com.bookingsystem.BookingSystem.handler.exception.BookNotAvailableException;
import com.bookingsystem.BookingSystem.repository.BookRepository;
import com.bookingsystem.BookingSystem.repository.LoanRepository;
import com.bookingsystem.BookingSystem.repository.UserRepository;
import com.bookingsystem.BookingSystem.repository.entity.Book;
import com.bookingsystem.BookingSystem.repository.entity.Loan;
import com.bookingsystem.BookingSystem.repository.entity.User;
import com.bookingsystem.BookingSystem.service.impl.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class LoanServiceTest {
    @InjectMocks
    private LoanService loanService;

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookRepository bookRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllLoans_shouldReturnLoanList() {
        // Arrange
        Loan loan1 = new Loan();
        Loan loan2 = new Loan();
        when(loanRepository.findAll()).thenReturn(Arrays.asList(loan1, loan2));

        // Act
        List<Loan> loans = loanService.getAllLoans();

        // Assert
        assertEquals(2, loans.size());
        verify(loanRepository, times(1)).findAll();
    }

    @Test
    public void findById_shouldReturnLoan() {
        // Arrange
        Loan loan = new Loan();
        when(loanRepository.findById(anyString())).thenReturn(Optional.of(loan));

        // Act
        Optional<Loan> foundLoan = loanService.findById("1");

        // Assert
        assertTrue(foundLoan.isPresent());
        verify(loanRepository, times(1)).findById(anyString());
    }

    @Test
    public void createLoan_shouldCreateNewLoan() {
        // Arrange
        User user = new User();
        user.setId("user1");
        Book book = new Book();
        book.setId("book1");
        book.setAvailable(true);
        Loan loanDetails = new Loan();
        loanDetails.setUser(user);
        loanDetails.setBook(book);
        loanDetails.setType("Loan");

        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
        when(bookRepository.findById(anyString())).thenReturn(Optional.of(book));
        when(loanRepository.save(any(Loan.class))).thenReturn(loanDetails);

        // Act
        Loan createdLoan = loanService.createLoan(loanDetails);

        // Assert
        assertNotNull(createdLoan);
        verify(userRepository, times(1)).findById(anyString());
        verify(bookRepository, times(1)).findById(anyString());
        verify(bookRepository, times(1)).save(any(Book.class));
        verify(loanRepository, times(1)).save(any(Loan.class));
    }

    @Test
    public void createLoan_shouldThrowExceptionWhenBookNotAvailable() {
        // Arrange
        User user = new User();
        user.setId("user1");
        Book book = new Book();
        book.setId("book1");
        book.setAvailable(false);
        Loan loanDetails = new Loan();
        loanDetails.setUser(user);
        loanDetails.setBook(book);
        loanDetails.setType("Loan");

        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
        when(bookRepository.findById(anyString())).thenReturn(Optional.of(book));

        // Act & Assert
        assertThrows(BookNotAvailableException.class, () -> loanService.createLoan(loanDetails));
        verify(userRepository, times(1)).findById(anyString());
        verify(bookRepository, times(1)).findById(anyString());
        verify(bookRepository, times(0)).save(any(Book.class));
        verify(loanRepository, times(0)).save(any(Loan.class));
    }

    @Test
    public void reserveBook_shouldReserveBook() {
        // Arrange
        Book book = new Book();
        book.setId("book1");
        book.setAvailable(true);

        when(bookRepository.findById(anyString())).thenReturn(Optional.of(book));

        // Act
        loanService.reserveBook("book1");

        // Assert
        assertEquals("Reserved", book.getStatus());
        assertFalse(book.isAvailable());
        verify(bookRepository, times(1)).findById(anyString());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    public void returnLoan_shouldReturnBook() {
        // Arrange
        Book book = new Book();
        book.setId("book1");
        Loan loan = new Loan();
        loan.setBook(book);
        when(loanRepository.findById(anyString())).thenReturn(Optional.of(loan));

        // Act
        boolean isReturned = loanService.returnLoan("loan1");

        // Assert
        assertTrue(isReturned);
        assertTrue(book.isAvailable());
        verify(loanRepository, times(1)).findById(anyString());
        verify(bookRepository, times(1)).save(any(Book.class));
        verify(loanRepository, times(1)).save(any(Loan.class));
    }

    @Test
    public void returnLoan_shouldReturnFalseWhenLoanNotFound() {
        // Arrange
        when(loanRepository.findById(anyString())).thenReturn(Optional.empty());

        // Act
        boolean isReturned = loanService.returnLoan("loan1");

        // Assert
        assertFalse(isReturned);
        verify(loanRepository, times(1)).findById(anyString());
        verify(bookRepository, times(0)).save(any(Book.class));
        verify(loanRepository, times(0)).save(any(Loan.class));
    }
}
