package com.bookingsystem.BookingSystem.repository;

import com.bookingsystem.BookingSystem.repository.entity.Loan;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LoanRepository extends MongoRepository<Loan, String> {
    List<Loan> findByUserId(String userId);
    List<Loan> findByBookId(String bookId);
}
