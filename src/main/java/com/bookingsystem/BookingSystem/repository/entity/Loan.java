package com.bookingsystem.BookingSystem.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "loans")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Loan {
    @Id
    private String id;

    @DBRef
    private User user;

    @DBRef
    private Book book;

    private LocalDate loanDate;
    private LocalDate returnDate;

    private String borrowerName;
    private String borrowerLastName;
    private String borrowerDPI;
    private String borrowerAddress;
    private String borrowerEmail;

    private String type;
}
