package com.bookingsystem.BookingSystem.service.impl;

import com.bookingsystem.BookingSystem.handler.exception.BookNotAvailableException;
import com.bookingsystem.BookingSystem.handler.exception.ResourceNotFoundException;
import com.bookingsystem.BookingSystem.repository.BookRepository;
import com.bookingsystem.BookingSystem.repository.LoanRepository;
import com.bookingsystem.BookingSystem.repository.UserRepository;
import com.bookingsystem.BookingSystem.repository.entity.Book;
import com.bookingsystem.BookingSystem.repository.entity.Loan;
import com.bookingsystem.BookingSystem.repository.entity.User;
import com.bookingsystem.BookingSystem.service.ILoanService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class LoanService implements ILoanService {
    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public LoanService(LoanRepository loanRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    @Override
    public Optional<Loan> findById(String id) {
        return loanRepository.findById(id);
    }

    @Override
    public List<Loan> findByUserId(String userId) {
        return loanRepository.findByUserId(userId);
    }

    @Override
    public List<Loan> findByBookId(String bookId) {
        return loanRepository.findByBookId(bookId);
    }

    @Override
    public Loan createLoan(Loan loanDetails) {
        Optional<User> userOptional = userRepository.findById(loanDetails.getUser().getId());
        Optional<Book> bookOptional = bookRepository.findById(loanDetails.getBook().getId());

        if (userOptional.isPresent() && bookOptional.isPresent()) {
            User user = userOptional.get();
            Book book = bookOptional.get();

            if (book.isAvailable()) {
                String newStatus;
                if ("Loan".equalsIgnoreCase(loanDetails.getType())) {
                    newStatus = "Borrowed";
                } else if ("Booking".equalsIgnoreCase(loanDetails.getType())) {
                    newStatus = "Reserved";
                } else {
                    throw new IllegalArgumentException("Invalid loan type: " + loanDetails.getType());
                }

                Loan loan = Loan.builder()
                        .user(user)
                        .book(book)
                        .loanDate(LocalDate.now())
                        .borrowerName(loanDetails.getBorrowerName())
                        .borrowerLastName(loanDetails.getBorrowerLastName())
                        .borrowerDPI(loanDetails.getBorrowerDPI())
                        .borrowerAddress(loanDetails.getBorrowerAddress())
                        .borrowerEmail(loanDetails.getBorrowerEmail())
                        .type(loanDetails.getType())
                        .build();

                book.setStatus(newStatus);
                book.setAvailable(false);
                bookRepository.save(book);
                return loanRepository.save(loan);
            } else {
                throw new BookNotAvailableException("Book is not available for loan/booking");
            }
        }
        throw new ResourceNotFoundException("User or Book not found");
    }

    @Override
    public void reserveBook(String bookId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            if (book.isAvailable()) {
                book.setStatus("Reserved");
                book.setAvailable(false);
                bookRepository.save(book);
            } else {
                throw new BookNotAvailableException("Book is not available for reservation");
            }
        } else {
            throw new ResourceNotFoundException("Book not found");
        }
    }

    @Override
    public boolean returnLoan(String loanId) {
        Optional<Loan> loanOptional = loanRepository.findById(loanId);
        if (loanOptional.isPresent()) {
            Loan loan = loanOptional.get();
            Book book = loan.getBook();
            book.setStatus("Disponible");
            book.setAvailable(true);
            bookRepository.save(book);
            loan.setReturnDate(LocalDate.now());
            loanRepository.save(loan);
            return true;
        }
        return false;
    }
}
