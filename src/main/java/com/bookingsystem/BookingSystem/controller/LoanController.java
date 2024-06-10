package com.bookingsystem.BookingSystem.controller;

import com.bookingsystem.BookingSystem.handler.exception.BookNotAvailableException;
import com.bookingsystem.BookingSystem.handler.exception.ResourceNotFoundException;
import com.bookingsystem.BookingSystem.repository.entity.Loan;
import com.bookingsystem.BookingSystem.service.impl.LoanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/loans")
public class LoanController {
    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping
    public List<Loan> getAllLoans(){
        return loanService.getAllLoans();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Loan> getLoanById(@PathVariable String id) {
        Optional<Loan> loanOptional = loanService.findById(id);
        return loanOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @GetMapping("/user/{userId}")
    public List<Loan> getLoansByUserId(@PathVariable String userId) {
        return loanService.findByUserId(userId);
    }

    @GetMapping("/book/{bookId}")
    public List<Loan> getLoansByBookId(@PathVariable String bookId) {
        return loanService.findByBookId(bookId);
    }

    @PostMapping("/new")
        public ResponseEntity<Loan> createLoanParams(@RequestParam String userId, @RequestParam String bookId,  @RequestParam String borrowerName,  @RequestParam String borrowerLastName,  @RequestParam String borrowerDPI,  @RequestParam String borrowerAddress,  @RequestParam String borrowerEmail, @RequestParam String type) {
        Loan loan = loanService.createLoanParams(userId, bookId, borrowerName, borrowerLastName, borrowerDPI, borrowerAddress, borrowerEmail, type);
        if (loan != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(loan);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /*@PostMapping
    public ResponseEntity<Loan> createLoan(@RequestBody Loan loan) {
        Loan createdLoan = loanService.createLoan(loan);
        if (createdLoan != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdLoan);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }*/

    @PostMapping
    public ResponseEntity<?> createLoan(@RequestBody Loan loan) {
        try {
            Loan createdLoan = loanService.createLoan(loan);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdLoan);
        } catch (BookNotAvailableException | ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/return/{id}")
    public ResponseEntity<String> returnLoan(@PathVariable String id) {
        boolean isReturned = loanService.returnLoan(id);
        if (isReturned) {
            return ResponseEntity.ok("Loan returned successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Loan not found or already returned.");
        }
    }
}
