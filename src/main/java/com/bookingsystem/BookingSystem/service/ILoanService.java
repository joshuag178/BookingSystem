package com.bookingsystem.BookingSystem.service;

import com.bookingsystem.BookingSystem.repository.entity.Loan;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ILoanService {
    List<Loan> getAllLoans();
    Optional<Loan> findById(String id);
    List<Loan> findByUserId(String userId);
    List<Loan> findByBookId(String bookId);
    Loan createLoan(Loan loanDetails);
    void reserveBook(String bookId);
    boolean returnLoan(String loanId);
}
